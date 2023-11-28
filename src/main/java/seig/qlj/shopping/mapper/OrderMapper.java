package seig.qlj.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import seig.qlj.shopping.pojo.Order;
import seig.qlj.shopping.vo.OrderVo;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【order】的数据库操作Mapper
* @createDate 2023-11-24 23:36:56
* @Entity seig.qlj.shopping.pojo.Order
*/
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select `order`.*, product.product_name as productName, product.product_picture as productPicture " +
            "from `order`, product where `order`.product_id = product.product_id and `order`.user_id = #{userId}")
    List<OrderVo> getOrderVoByUserId(Integer userId);

}




