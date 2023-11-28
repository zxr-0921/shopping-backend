package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.ProductMapper;
import seig.qlj.shopping.pojo.Product;
import seig.qlj.shopping.service.ProductService;

import java.util.List;

/**
 * @author zxr0921
 * @description 针对表【product】的数据库操作Service实现
 * @createDate 2023-11-24 23:37:00
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductByCategoryId(Integer categoryId) {
        List<Product> list = null;
        // TODO: 2023/11/26   未测试
//        分页查询
//        Example example = new Example(Product.class);
//        example.orderBy("productSales").desc();
//        example.createCriteria().andEqualTo("categoryId", categoryId);

//        PageHelper.startPage(0, 8);
        IPage<Product> page = new Page<>(1, 8);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        wrapper.orderByDesc("product_sales");
        IPage<Product> productIPage = productMapper.selectPage(page, wrapper);
        try {

//            list = productMapper.selectByExample(example);
            list = productIPage.getRecords();
            if (ArrayUtils.isEmpty(list.toArray())) {

                throw new XmException(ExceptionEnum.GET_PRODUCT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_PRODUCT_ERROR);
        }
        return list;
    }

    @Override
    public List<Product> getHotProduct() {
        IPage<Product> page = new Page<>(1, 8);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("product_sales");
        productMapper.selectPage(page, wrapper);
        List<Product> list = null;
        try {
            list = page.getRecords();
//            System.out.println("返回的商品" + list);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_PRODUCT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_PRODUCT_ERROR);
        }
        return list;
    }

    @Override
    public Product getProductById(String productId) {
        Product product = null;
        try {
            product = productMapper.selectById(productId);
            if (product == null) {
                throw new XmException(ExceptionEnum.GET_PRODUCT_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_PRODUCT_ERROR);
        }
        return product;
    }

    @Override
    public IPage<Product> getProductByPage(String currentPage, String pageSize, String categoryId) {
        List<Product> list = null;
        IPage<Product> page = new Page<>(Integer.parseInt(currentPage) - 1, Integer.parseInt(pageSize), true);
//        PageHelper.startPage(Integer.parseInt(currentPage) - 1, Integer.parseInt(pageSize), true);
        // TODO: 2023/11/26   未测试
        if (categoryId.equals("0")) { // 为0，代表分页查询所有商品
            list = page.getRecords();
            ;
        } else {
            // 分类分页查询商品
//            Product product = new Product();
//            product.setCategory_id(Integer.parseInt(categoryId));
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("category_id", Integer.parseInt(categoryId));
            page = productMapper.selectPage(page, wrapper);
            list = page.getRecords();
//            list = productMapper.select(product);
        }
        return page;
    }
}




