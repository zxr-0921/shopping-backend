package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.CollectMapper;
import seig.qlj.shopping.pojo.Collect;
import seig.qlj.shopping.pojo.Product;
import seig.qlj.shopping.service.CollectService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author zxr0921
* @description 针对表【collect】的数据库操作Service实现
* @createDate 2023-11-24 23:36:52
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService{
    @Resource
    private CollectMapper collectMapper;

    @Override
    @Transactional
    public void addCollect(String userId, String productId) {
        Collect collect = new Collect();
        collect.setUser_id(Integer.parseInt(userId));
        collect.setProduct_id(Integer.parseInt(productId));
        // TODO: 2023/11/26  未测试
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", Integer.parseInt(userId));
        wrapper.eq("product_id", Integer.parseInt(productId));
        // 先看看是否数据库中已存在
//        Collect one = collectMapper.selectOne(collect);
        Collect one = collectMapper.selectOne(wrapper);
        if (one != null) {
            throw new XmException(ExceptionEnum.SAVE_COLLECT_REUSE);
        }
        // 不存在，添加收藏
        collect.setCollect_time(new Date().getTime());
        System.out.println("collect:" + collect);
        int count = collectMapper.insert(collect);
        if (count != 1) {
            throw new XmException(ExceptionEnum.SAVE_COLLECT_ERROR);
        }

    }

    @Override
    public List<Product> getCollect(String userId) {
        List<Product> list = null;
        try {
            list = collectMapper.getCollect(userId);
            if (ArrayUtils.isEmpty(list.toArray())) {
                throw new XmException(ExceptionEnum.GET_COLLECT_NOT_FOUND);
            }
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.GET_COLLECT_ERROR);
        }
        return list;
    }

    @Override
    public void deleteCollect(String userId, String productId) {
//        Collect collect = new Collect();
//        collect.setUser_id(Integer.parseInt(userId));
//        collect.setProduct_id(Integer.parseInt(productId));
        // TODO: 2023/11/26   未测试
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", Integer.parseInt(userId));
        wrapper.eq("product_id", Integer.parseInt(productId));
//        QueryWrapper wrapper = new QueryWrapper();
        try {
            int count = collectMapper.delete(wrapper);
            if (count != 1) {
                throw new XmException(ExceptionEnum.DELETE_COLLECT_ERROR);
            }
        } catch (XmException e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.DELETE_COLLECT_ERROR);
        }
    }
}




