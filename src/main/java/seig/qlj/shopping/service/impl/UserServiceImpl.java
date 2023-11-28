package seig.qlj.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import seig.qlj.shopping.exception.ExceptionEnum;
import seig.qlj.shopping.exception.XmException;
import seig.qlj.shopping.mapper.UserMapper;
import seig.qlj.shopping.pojo.User;
import seig.qlj.shopping.service.UserService;
import seig.qlj.shopping.util.MD5Util;

import javax.annotation.Resource;

/**
* @author zxr0921
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-11-24 23:37:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private  UserMapper userMapper;

    /**
     * 用户登录
     * @param user 用户
     * @return 用户
     */
    @Override
    public User login(User user) {
        user.setPassword(MD5Util.MD5Encode(user.getPassword() + "", "UTF-8"));
        QueryWrapper wrapper = new QueryWrapper(user);
        User one = userMapper.selectOne(wrapper);
        if (one == null) {
            throw new XmException(ExceptionEnum.GET_USER_NOT_FOUND);
        }
        return one;
    }

    /**
     * 用户注册
     * @param user 用户
     */
    @Override
    public void register(User user) {
        User one = new User();
        one.setUsername(user.getUsername());
        QueryWrapper wrapper = new QueryWrapper(one);
        // 先去看看用户名是否重复
        if (userMapper.selectCount(wrapper) == 1) {
            // 用户名已存在
            throw new XmException(ExceptionEnum.SAVE_USER_REUSE);
        }
        // 使用md5对密码进行加密
        user.setPassword(MD5Util.MD5Encode(user.getPassword() + "", "UTF-8"));
        // 存入数据库
        try {
            this.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new XmException(ExceptionEnum.SAVE_USER_ERROR);
        }
    }

    /**
     * 检查用户名是否重复
     * @param username 用户名
     */
    @Override
    public void isUserName(String username) {
        User one = new User();
        one.setUsername(username);
        QueryWrapper wrapper = new QueryWrapper(one);

        // 先去看看用户名是否重复
        if (userMapper.selectCount(wrapper) == 1) {
            // 用户名已存在
            throw new XmException(ExceptionEnum.SAVE_USER_REUSE);
        }
    }
}




