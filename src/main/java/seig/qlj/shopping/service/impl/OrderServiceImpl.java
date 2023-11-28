package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.OrderMapper;
import seig.qlj.shopping.mapper.ProductMapper;
import seig.qlj.shopping.mapper.SeckillProductMapper;
import seig.qlj.shopping.mapper.ShoppingCartMapper;
import seig.qlj.shopping.pojo.Order;
import seig.qlj.shopping.pojo.Product;
import seig.qlj.shopping.pojo.SeckillProduct;
import seig.qlj.shopping.pojo.ShoppingCart;
import seig.qlj.shopping.service.OrderService;
import seig.qlj.shopping.util.IdWorker;
import seig.qlj.shopping.vo.CartVo;
import seig.qlj.shopping.vo.OrderVo;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author zxr0921
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-11-24 23:36:56
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService {
    @Resource
    private IdWorker idWorker;
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShoppingCartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SeckillProductMapper seckillProductMapper;

    private final static String SECKILL_PRODUCT_USER_LIST = "seckill:product:user:list";


    @Override
    @Transactional
    public void addOrder(List<CartVo> cartVoList, Integer userId) {
        // 先添加订单
        String orderId = idWorker.nextId() + ""; // 订单id
        long time = new Date().getTime(); // 订单生成时间
        for (CartVo cartVo : cartVoList) {
            Order order = new Order();
            order.setOrder_id(orderId);
            order.setOrder_time(time);
            order.setProduct_num(cartVo.getNum());
            order.setProduct_id(cartVo.getProductId());
            order.setProduct_price(cartVo.getPrice());
            order.setUser_id(userId);
            try {
                orderMapper.insert(order);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
            }
            // 减去商品库存,记录卖出商品数量
            // TODO : 此处会产生多线程问题，即不同用户同时对这个商品操作，此时会导致数量不一致问题
            // TODO: 2023/11/26 未测试
//            Product product = productMapper.selectByPrimaryKey(cartVo.getProductId());
            Product product = productMapper.selectById(cartVo.getProductId());
            product.setProduct_num(product.getProduct_num() - cartVo.getNum());
            product.setProduct_sales(product.getProduct_sales() + cartVo.getNum());
//            productMapper.updateByPrimaryKey(product);
            productMapper.selectById(product);
        }
        // 删除购物车
//        ShoppingCart cart = new ShoppingCart();
//        cart.setUser_id(userId);
        // TODO: 2023/11/26   未测试
        QueryWrapper<ShoppingCart> wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        try {
            int count = cartMapper.delete(wrapper);
            if (count == 0) {
                throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
        }

    }

    @Override
    public List<List<OrderVo>> getOrder(Integer userId) {
        List<OrderVo> list = null;
        ArrayList<List<OrderVo>> ret = new ArrayList<>();
        try {
            list = orderMapper.getOrderVoByUserId(userId);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_ORDER_NOT_FOUND);
            }
            // 将同一个订单放在一组
            Map<String, List<OrderVo>> collect = list.stream().collect(Collectors.groupingBy(Order::getOrder_id));
            Collection<List<OrderVo>> values = collect.values();
            ret.addAll(values);
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_ORDER_ERROR);
        }
        return ret;
    }

    @Override
    @Transactional
    public void addSeckillOrder(String seckillId, String userId) {
        // 订单id
        String orderId = idWorker.nextId() + "";
        // 商品id
//        SeckillProduct seckillProduct = new SeckillProduct();
//        seckillProduct.setSeckill_id(Integer.parseInt(seckillId));
        // TODO: 2023/11/26   未测试
        QueryWrapper<SeckillProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("seckill_id", Integer.parseInt(seckillId));
        SeckillProduct one = seckillProductMapper.selectOne(wrapper);
        Integer productId = one.getProduct_id();
        // 秒杀价格
        Double price = one.getSeckill_price();

        // 订单封装
        Order order = new Order();
        order.setOrder_id(orderId);
        order.setProduct_id(productId);
        order.setProduct_num(1);
        order.setUser_id(Integer.parseInt(userId));
        order.setOrder_time(new Date().getTime());
        order.setProduct_price(price);

        try {
            orderMapper.insert(order);
            // 减库存
            seckillProductMapper.decrStock(one.getSeckill_id());
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.ADD_ORDER_ERROR);
        }

        // 订单创建成功, 将用户写入redis, 防止多次抢购
        redisTemplate.opsForList().leftPush(SECKILL_PRODUCT_USER_LIST + seckillId, userId);

    }

    }





