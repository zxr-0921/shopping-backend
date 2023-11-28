package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.ProductPictureMapper;
import seig.qlj.shopping.pojo.ProductPicture;
import seig.qlj.shopping.service.ProductPictureService;

import javax.management.Query;
import java.util.List;

/**
* @author zxr0921
* @description 针对表【product_picture】的数据库操作Service实现
* @createDate 2023-11-24 23:37:03
*/
@Service
public class ProductPictureServiceImpl extends ServiceImpl<ProductPictureMapper, ProductPicture>
    implements ProductPictureService {
    @Autowired
    private ProductPictureMapper productPictureMapper;

    /**
     * 根据商品id获取商品图片
     * @param productId 商品id
     * @return 商品图片列表
     */
    @Override
    public List<ProductPicture> getProductPictureByProductId(String productId) {
        // TODO: 2023/11/26  未测试
        if (productId == null) {
            throw new XmException(ExceptionEnum.GET_PRODUCT_PICTURE_ERROR);
        }
        QueryWrapper<ProductPicture> pp = new QueryWrapper<>();
        pp.eq("product_id", Integer.parseInt(productId));
//        ProductPicture picture = new ProductPicture();
//        picture.setProduct_id(Integer.parseInt(productId));
        List<ProductPicture> list = null;
        try {
            list = productPictureMapper.selectList(pp);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_PRODUCT_PICTURE_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_PRODUCT_PICTURE_ERROR);
        }
        return list;
    }
}




