//package seig.qlj.shopping.mq;
//
//import com.qcloud.cos.internal.eventstreaming.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import seig.qlj.shopping.service.OrderService;
//import seig.qlj.shopping.service.SeckillProductService;
//import seig.qlj.shopping.util.RedisKey;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.nio.channels.Channel;
//import java.util.Map;
//
///**
// * @Auther: wdd
// * @Date: 2020-04-24 9:30
// * @Description:
// */
//@Component
//public class SeckillOrderQueue {
//
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private SeckillProductService seckillProductService;
//    @Resource
//    StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 监听秒杀订单队列
//     * @param map
//     * @param channel
//     * @param message
//     */
//    @RabbitListener(queues = "seckill_order")
//    public void insertOrder(Map map, Channel channel, Message message){
//
//        // 查看id，保证幂等性
//        String correlationId = message.getMessageProperties().getCorrelationId();
//        if (!stringRedisTemplate.hasKey(RedisKey.SECKILL_RABBITMQ_ID + correlationId)) {
//            // redis中存在，表明此条消息已消费，请勿重复消费
//            return;
//        }
//        String seckillId = (String) map.get("seckillId");
//        String userId = (String) map.get("userId");
//        // 存入redis，因为只需要判断是否存在，因此value为多少无所谓
//        stringRedisTemplate.opsForValue().set(RedisKey.SECKILL_RABBITMQ_ID + correlationId, "1");
//        Long seckillEndTime = seckillProductService.getEndTime(seckillId);
//        stringRedisTemplate.expire(RedisKey.SECKILL_RABBITMQ_ID + correlationId, seckillEndTime - new Date().getTime(), TimeUnit.SECONDS); // 设置过期时间
//
//        try {
//            orderService.addSeckillOrder(seckillId, userId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                stringRedisTemplate.delete(RedisKey.SECKILL_RABBITMQ_ID + correlationId);
//                // 将该消息放入队列尾部，尝试再次消费
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//    }
//}
