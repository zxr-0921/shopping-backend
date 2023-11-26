package seig.qlj.shopping.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import seig.qlj.shopping.mapper.ProductMapper;
import seig.qlj.shopping.mapper.SeckillProductMapper;
import seig.qlj.shopping.mapper.SeckillTimeMapper;
import seig.qlj.shopping.pojo.SeckillProduct;
import seig.qlj.shopping.pojo.SeckillTime;
import seig.qlj.shopping.service.ProductService;
import seig.qlj.shopping.service.SeckillProductService;

import java.util.*;

/**
 * @description: 定时任务，每天15点添加秒杀商品
 * @createDate: 2023/11/26 15:00
 * @version: 1.0
 */
@Component
public class SeckillTask {

    @Autowired
    private SeckillTimeMapper seckillTimeMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SeckillProductMapper seckillProductMapper;
    @Autowired
    private SeckillProductService seckillProductService;

    @Scheduled(cron = "0 0 15 * * ?")
    public void execute() {
        // 获取商品设为秒杀商品
        List<Integer> productIds = productMapper.selectIds();
        Date time = getDate();
        seckillTimeMapper.deleteAll();
        seckillProductMapper.deleteAll();
        for (int i = 1; i < 24; i = i + 2) {

            // 插入时间
            long startTime = time.getTime()/1000*1000 + 1000 * 60 * 60 * i;
            long endTime = startTime + 1000 * 60 * 60;
            SeckillTime seckillTime = new SeckillTime();
            seckillTime.setStart_time(startTime);
            seckillTime.setEnd_time(endTime);
            seckillTimeMapper.insert(seckillTime);


            // 随机选15个商品id
            HashSet<Integer> set = new HashSet<>();
            while (set.size() < 15) {
                Random random = new Random();
                int nextInt = random.nextInt(productIds.size());
                set.add(productIds.get(nextInt));
            }
            ArrayList<Integer> integers = new ArrayList<>(set);

            // System.out.println(Arrays.toString(integers.toArray()));

            // 添加秒杀商品
            ArrayList<SeckillProduct> seckillProducts = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                SeckillProduct seckillProduct = new SeckillProduct();
                seckillProduct.setSeckill_price(1000.0);
                seckillProduct.setSeckill_stock(100);
                seckillProduct.setProduct_id(integers.get(j));
                seckillProduct.setTime_id(seckillTime.getTime_id());
                seckillProducts.add(seckillProduct);
            }

//            seckillProductMapper.insertList(seckillProducts);
//            seckillProductMapper.insert(seckillProducts);
            seckillProductService.saveBatch(seckillProducts);
            // System.out.println(Arrays.toString(seckillProduc
            // ts.toArray()));

            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("完成---------------------");

        }

        System.out.println("一次添加ok-------------------------------------------");
        // try {
        //     Thread.sleep(100_000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

    }

    private Date getDate() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }

}
