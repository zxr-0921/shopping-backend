package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.SeckillProductMapper;
import seig.qlj.shopping.mapper.SeckillTimeMapper;
import seig.qlj.shopping.pojo.SeckillProduct;
import seig.qlj.shopping.pojo.SeckillTime;
import seig.qlj.shopping.service.SeckillProductService;
import seig.qlj.shopping.util.BeanUtil;
import seig.qlj.shopping.util.RedisKey;
import seig.qlj.shopping.vo.SeckillProductVo;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;


/**
 * @author zxr0921
 * @description 针对表【seckill_product】的数据库操作Service实现
 * @createDate 2023-11-24 23:37:05
 */
@Service
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductMapper, SeckillProduct>
        implements SeckillProductService {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillTimeMapper seckillTimeMapper;
    @Autowired
    private SeckillProductMapper seckillProductMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private HashMap<String, Boolean> localOverMap = new HashMap<>();

    @Override
    @Transactional
    public List<SeckillProductVo> getProduct(String timeId) {
        // 先查看缓存，是否有列表
        List<SeckillProductVo> seckillProductVos = redisTemplate.opsForList().range(RedisKey.SECKILL_PRODUCT_LIST + timeId, 0, -1);
        if (ArrayUtils.isNotEmpty(seckillProductVos.toArray())) {
            return seckillProductVos;
        }
        // 缓存没有，再从数据库中获取，添加到缓存
        seckillProductVos = seckillProductMapper.getSeckillProductVos(timeId, new Date().getTime());
        if (ArrayUtils.isNotEmpty(seckillProductVos.toArray())) {
            redisTemplate.opsForList().leftPushAll(RedisKey.SECKILL_PRODUCT_LIST + timeId, seckillProductVos);
            // 设置过期时间
            long l = seckillProductVos.get(0).getEndTime() - new Date().getTime();
            redisTemplate.expire(RedisKey.SECKILL_PRODUCT_LIST + timeId, l, TimeUnit.MILLISECONDS);
        } else {
            // 秒杀商品过期或不存在
            throw new XmException(ExceptionEnum.GET_SECKILL_NOT_FOUND);
        }
        return seckillProductVos;
    }

    @Override
    public void addSeckillProduct(SeckillProduct seckillProduct) {
        // TODO: 仿添加秒杀商品
        Date time = getDate();
        long startTime = time.getTime() / 1000 * 1000 + 1000 * 60 * 60;
        long endTime = startTime + 1000 * 60 * 60;
        SeckillTime seckillTime = new SeckillTime();
        seckillTime.setStart_time(startTime);
        seckillTime.setEnd_time(endTime);


        QueryWrapper<SeckillTime> wrapper = new QueryWrapper();
        wrapper.eq("start_time", startTime);
        wrapper.eq("end_time", endTime);
        // 先查看是否有该时间段
        SeckillTime one = seckillTimeMapper.selectOne(wrapper);
        if (one == null) {
            seckillTimeMapper.insert(seckillTime);
            seckillProduct.setTime_id(seckillTime.getTime_id());
        } else {
            seckillProduct.setTime_id(one.getTime_id());
        }
        seckillProductMapper.insert(seckillProduct);

    }

    // 获取当前时间
    public Date getDate() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

    @Override
    public List<SeckillTime> getTime() {
        // 获取当前时间及往后7个时间段, 总共8个
        Date time = getDate();
        List<SeckillTime> seckillTimes = seckillTimeMapper.getTime(time.getTime() / 1000 * 1000);
        return seckillTimes;
    }

    @Override
    public SeckillProductVo getSeckill(String seckillId) {
        // 从缓存中查询
        Map map = redisTemplate.opsForHash().entries(RedisKey.SECKILL_PRODUCT + seckillId);
        if (!map.isEmpty()) {
            map.size();
            SeckillProductVo seckillProductVo = null;
            try {
                seckillProductVo = BeanUtil.map2bean(map, SeckillProductVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return seckillProductVo;
        }
        // 从数据库中查询
        SeckillProductVo seckillProductVo = seckillProductMapper.getSeckill(seckillId);
        if (seckillProductVo != null) {
            try {
                redisTemplate.opsForHash().putAll(RedisKey.SECKILL_PRODUCT + seckillId, BeanUtil.bean2map(seckillProductVo));
                redisTemplate.expire(RedisKey.SECKILL_PRODUCT + seckillId, seckillProductVo.getEndTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
                // 将库存单独存入一个key中
                if (stringRedisTemplate.opsForValue().get(RedisKey.SECKILL_PRODUCT_STOCK + seckillId) == null) {
                    stringRedisTemplate.opsForValue().set(RedisKey.SECKILL_PRODUCT_STOCK + seckillId, seckillProductVo.getSeckill_stock() + "", seckillProductVo.getEndTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return seckillProductVo;
        }
        return null;
    }

    /**
     * 秒杀
     *
     * @param seckillId
     */

    @Override
    @Transactional
    public void seckillProduct(String seckillId, Integer userId) {

        if (localOverMap.get(seckillId) != null && localOverMap.get(seckillId)) {
            // 售空
            throw new XmException(ExceptionEnum.GET_SECKILL_IS_OVER);
        }
        // 判断秒杀是否开始, 防止路径暴露被刷
        Map m = redisTemplate.opsForHash().entries(RedisKey.SECKILL_PRODUCT + seckillId);
        if (!m.isEmpty()) {
            SeckillProductVo seckillProductVo = null;
            try {
                seckillProductVo = BeanUtil.map2bean(m, SeckillProductVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 秒杀开始时间
            Long startTime = seckillProductVo.getStartTime();

            if (startTime > new Date().getTime()) {
                throw new XmException(ExceptionEnum.GET_SECKILL_IS_NOT_START);
            }
        }

        // 判断是否已经秒杀到了，避免一个账户秒杀多个商品
        List<String> list = redisTemplate.opsForList().range(RedisKey.SECKILL_PRODUCT_USER_LIST + seckillId, 0, -1);
        if (list.contains(String.valueOf(userId))) {
            throw new XmException(ExceptionEnum.GET_SECKILL_IS_REUSE);
        }

        // 预减库存：从缓存中减去库存
        // 利用redis中的方法，减去库存，返回值为减去1之后的值
        if (stringRedisTemplate.opsForValue().decrement(RedisKey.SECKILL_PRODUCT_STOCK + seckillId) < 0) {
            // 设置内存标记
            localOverMap.put(seckillId, true);
            // 秒杀完成，库存为空
            throw new XmException(ExceptionEnum.GET_SECKILL_IS_OVER);
        }

        // 使用RabbitMQ异步传输
        mqSend(seckillId, userId);
    }

    @Override
    public void mqSend(String seckillId, Integer userId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("userId", userId.toString());
        // 设置ID，保证消息队列幂等性
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(seckillId + ":" + userId);
        try {
            rabbitTemplate.convertAndSend("seckill_order", map, correlationData);
        } catch (AmqpException e) {
            // 发送消息失败
            e.printStackTrace();
            stringRedisTemplate.opsForValue().increment(RedisKey.SECKILL_PRODUCT_STOCK + seckillId);
        }
    }

    @Override
    public Long getEndTime(String seckillId) {
        SeckillProductVo seckill = seckillProductMapper.getSeckill(seckillId);
        return seckillTimeMapper.getEndTime(seckill.getTime_id());
    }
}




