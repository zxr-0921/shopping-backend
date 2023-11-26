package seig.qlj.shopping.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seig.qlj.shopping.pojo.ProductPicture;
import seig.qlj.shopping.service.ProductPictureService;
import seig.qlj.shopping.util.ResultMessage;

import java.util.List;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:27
 * @Description:
 */
// TODO: 2023/11/26  测试通过
@RestController
@RequestMapping("/productPicture")
@Api(tags = "商品图片模块")
public class ProductPictureController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ProductPictureService productPictureService;

    @GetMapping("/product/{productId}")
    @ApiOperation(value = "根据商品id获取商品图片信息")
    public ResultMessage productPicture(@PathVariable String productId) {
        List<ProductPicture> products = productPictureService.getProductPictureByProductId(productId);
        resultMessage.success("001", products);
        return resultMessage;
    }

}
