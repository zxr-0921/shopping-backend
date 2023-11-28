package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.Collect;
import seig.qlj.shopping.pojo.Product;

import java.util.List;

/**
* @author zxr0921
* @description 针对表【collect】的数据库操作Service
* @createDate 2023-11-24 23:36:52
*/
public interface CollectService extends IService<Collect> {
    public void addCollect(String userId, String productId);
    public List<Product> getCollect(String userId);
    public void deleteCollect(String userId, String productId);

}
