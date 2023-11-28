package seig.qlj.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import seig.qlj.shopping.pojo.Collect;
import seig.qlj.shopping.pojo.Product;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【collect】的数据库操作Mapper
* @createDate 2023-11-24 23:36:52
* @Entity seig.qlj.shopping.pojo.Collect
*/
public interface CollectMapper extends BaseMapper<Collect> {
    @Select("select product.* from product, collect where collect.user_id = #{userId} and collect.product_id = product.product_id")
    List<Product> getCollect(String userId);

}




