package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.ProductPicture;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【product_picture】的数据库操作Service
* @createDate 2023-11-24 23:37:03
*/
public interface ProductPictureService extends IService<ProductPicture> {
    public List<ProductPicture> getProductPictureByProductId(String productId);

}
