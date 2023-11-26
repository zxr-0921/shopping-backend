package seig.qlj.shopping.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seig.qlj.shopping.pojo.Product;
import seig.qlj.shopping.service.ProductService;
import seig.qlj.shopping.util.ResultMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:26
 * @Description:
 */
// TODO: 2023/11/26  测试通过
@RestController
@RequestMapping("/product")
@Api(tags = "商品模块")
public class ProductController {

    @Autowired
    private ResultMessage resultMessage;
    @Autowired
    private ProductService productService;

    @GetMapping("/category/limit/{categoryId}")
    @ApiOperation(value = "根据分类id获取商品信息")
    public ResultMessage getProductByCategoryId(@PathVariable Integer categoryId) {
        List<Product> list = productService.getProductByCategoryId(categoryId);
        resultMessage.success("001", list);
        return resultMessage;

    }

    @GetMapping("/category/hot")
    @ApiOperation(value = "获取热门商品信息")
    public ResultMessage getHotProduct() {
        List<Product> list = productService.getHotProduct();
        resultMessage.success("001", list);
        return resultMessage;

    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "根据商品id获取商品信息")
    public ResultMessage getProduct(@PathVariable String productId) {
        Product product = productService.getProductById(productId);
        resultMessage.success("001", product);
        return resultMessage;
    }

    @GetMapping("/page/{currentPage}/{pageSize}/{categoryId}")
    @ApiOperation(value = "分页获取商品信息")
    public Map<String, Object> getProductByPage(@PathVariable String currentPage, @PathVariable String pageSize, @PathVariable String categoryId) {
//        PageInfo<Product> pageInfo = productService.getProductByPage(currentPage, pageSize, categoryId);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("code", "001");
//        map.put("data", pageInfo.getList());
//        map.put("total", pageInfo.getTotal());
        IPage<Product> page = productService.getProductByPage(currentPage, pageSize, categoryId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "001"); //状态码
        map.put("data", page.getRecords()); //当前页数据
        map.put("total", page.getTotal());  //总记录数
        return map;
    }

}
