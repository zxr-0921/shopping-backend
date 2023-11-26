package seig.qlj.shopping.service.impl;

import org.junit.jupiter.api.Test;
import seig.qlj.shopping.pojo.ProductPicture;

import java.util.List;

class ProductPictureServiceImplTest {
    @Test
    void getProductPictureByProductId() {
        String id = "1";

        ProductPictureServiceImpl productPictureService = new ProductPictureServiceImpl();
        List<ProductPicture> list = productPictureService.getProductPictureByProductId(id);
        System.out.println(list);

    }
}