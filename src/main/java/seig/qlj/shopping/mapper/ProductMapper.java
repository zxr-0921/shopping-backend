package seig.qlj.shopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import seig.qlj.shopping.pojo.Product;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【product】的数据库操作Mapper
* @createDate 2023-11-24 23:37:00
* @Entity seig.qlj.shopping.pojo.Product
*/
public interface ProductMapper extends BaseMapper<Product> {
    @Select("select product_id from product")
    List<Integer> selectIds();

}




