package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.Order;
import seig.qlj.shopping.vo.CartVo;
import seig.qlj.shopping.vo.OrderVo;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【order】的数据库操作Service
* @createDate 2023-11-24 23:36:56
*/
public interface OrderService extends IService<Order> {
    public void addOrder(List<CartVo> cartVoList, Integer userId);
    public List<List<OrderVo>> getOrder(Integer userId);
    public void addSeckillOrder(String seckillId, String userId);

}
