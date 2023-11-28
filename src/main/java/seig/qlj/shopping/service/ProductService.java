package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.Product;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【product】的数据库操作Service
* @createDate 2023-11-24 23:37:00
*/
public interface ProductService extends IService<Product> {
    public List<Product> getProductByCategoryId(Integer categoryId);
    public List<Product> getHotProduct();
    public Product getProductById(String productId);
//    public PageInfo<Product> getProductByPage(String currentPage, String pageSize, String categoryId);
    public IPage<Product> getProductByPage(String currentPage, String pageSize, String categoryId);

}
