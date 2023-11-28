package seig.qlj.shopping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import seig.qlj.shopping.pojo.User;

/**
* @author zxr0921
* @description 针对表【user】的数据库操作Service
* @createDate 2023-11-24 23:37:16
*/
public interface UserService extends IService<User> {
    public User login(User user);
    public void register(User user);
    public void isUserName(String username);

}
