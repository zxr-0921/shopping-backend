package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.Category;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【category】的数据库操作Service
* @createDate 2023-11-24 23:36:46
*/
public interface CategoryService extends IService<Category> {
    public List<Category> getAll();

}
