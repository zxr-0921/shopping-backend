package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import seig.qlj.shopping.pojo.SeckillProduct;
import seig.qlj.shopping.pojo.SeckillTime;
import seig.qlj.shopping.vo.SeckillProductVo;

import java.util.Date;
import java.util.List;

/**
* @author zxr0921
* @description 针对表【seckill_product】的数据库操作Service
* @createDate 2023-11-24 23:37:05
*/
public interface SeckillProductService extends IService<SeckillProduct> {
    @Transactional
    public List<SeckillProductVo> getProduct(String timeId);
    public void addSeckillProduct(SeckillProduct seckillProduct);
    public Date getDate();
    public List<SeckillTime> getTime();
    public SeckillProductVo getSeckill(String seckillId);
    public void seckillProduct(String seckillId, Integer userId);
    public void mqSend(String seckillId, Integer userId);
    public Long getEndTime(String seckillId);

}
