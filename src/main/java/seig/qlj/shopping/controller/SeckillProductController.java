package seig.qlj.shopping.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import seig.qlj.shopping.pojo.SeckillProduct;
import seig.qlj.shopping.pojo.SeckillTime;
import seig.qlj.shopping.service.SeckillProductService;
import seig.qlj.shopping.util.ResultMessage;
import seig.qlj.shopping.vo.SeckillProductVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-28 19:58
 * @Description:
 */
@RestController
@RequestMapping("/seckill/product")
@Api(tags = "秒杀商品模块")
public class SeckillProductController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private SeckillProductService seckillProductService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 根据时间id获取对应时间的秒杀商品列表
     * @param timeId
     * @return
     */
    @GetMapping("/time/{timeId}")
    @ApiOperation(value = "根据时间id获取对应时间的秒杀商品列表")
    public ResultMessage getProduct(@PathVariable String timeId) {
        List<SeckillProductVo> seckillProductVos = seckillProductService.getProduct(timeId);
        resultMessage.success("001", seckillProductVos);
        return resultMessage;
    }

    /**
     * 获取秒杀商品
     * @param seckillId
     * @return
     */
    @GetMapping("/{seckillId}")
    @ApiOperation(value = "获取秒杀商品")
    public ResultMessage getSeckill(@PathVariable String seckillId) {
        SeckillProductVo seckillProductVo = seckillProductService.getSeckill(seckillId);
        resultMessage.success("001", seckillProductVo);
        return resultMessage;
    }

    /**
     * 获取时间段
     * @return
     */
    @GetMapping("/time")
    @ApiOperation(value = "获取时间段")
    public ResultMessage getTime() {
        List<SeckillTime> seckillTimes = seckillProductService.getTime();
        resultMessage.success("001", seckillTimes);
        return resultMessage;
    }

    /**
     * 添加秒杀商品
     * @param seckillProduct
     * @return
     */
    @PostMapping("")
    @ApiOperation(value = "添加秒杀商品")
    public ResultMessage addSeckillProduct(@RequestBody SeckillProduct seckillProduct) {
        seckillProductService.addSeckillProduct(seckillProduct);
        resultMessage.success("001", "添加成功");
        return resultMessage;
    }

    /**
     * 开始秒杀
     * @param seckillId
     * @return
     */
    @PostMapping("/seckill/{seckillId}")
    @ApiOperation(value = "开始秒杀")
    public ResultMessage seckillProduct(@PathVariable String seckillId, @CookieValue("XM_TOKEN") String cookie) {
        // 先判断cookie是否存在，和redis校验
        Integer userId = (Integer) redisTemplate.opsForHash().get(cookie, "userId");
        seckillProductService.seckillProduct(seckillId, userId);
        resultMessage.success("001", "排队中");
        return resultMessage;
    }


}
