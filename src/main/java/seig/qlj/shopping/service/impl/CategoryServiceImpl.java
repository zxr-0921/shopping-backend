package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.CategoryMapper;
import seig.qlj.shopping.pojo.Category;
import seig.qlj.shopping.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zxr0921
* @description 针对表【category】的数据库操作Service实现
* @createDate 2023-11-24 23:36:46
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        List<Category> categories = null;
        try {
            categories = categoryMapper.selectList(null);
            if (categories == null) {
                throw new XmException(ExceptionEnum.GET_CATEGORY_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_CATEGORY_ERROR);
        }
        return categories;
    }
}




