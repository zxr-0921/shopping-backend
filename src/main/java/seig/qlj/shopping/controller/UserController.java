package seig.qlj.shopping.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.mapping.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import seig.qlj.shopping.pojo.User;
import seig.qlj.shopping.service.UserService;
import seig.qlj.shopping.util.BeanUtil;
import seig.qlj.shopping.util.CookieUtil;
import seig.qlj.shopping.util.MD5Util;
import seig.qlj.shopping.util.ResultMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wdd
 * @Date: 2020-03-19 13:27
 * @Description:
 */
// TODO: 2023/11/26 测试通过
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {


    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Resource
    private ResultMessage resultMessage;


    /**
     * 用户登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ResultMessage login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        user = userService.login(user);
        // 添加cookie，设置唯一认证
        String encode = MD5Util.MD5Encode(user.getUsername() + user.getPassword(), "UTF-8");
        // 进行加盐
        encode += "|" + user.getUser_id() + "|" + user.getUsername() + "|";
        CookieUtil.setCookie(request, response, "XM_TOKEN", encode, 1800);
        System.out.println("encode:" + encode);
        // 将encode放入redis中，用于认证
        try {
            redisTemplate.opsForHash().putAll(encode, BeanUtil.bean2map(user));
            redisTemplate.expire(encode, 30 * 60, TimeUnit.SECONDS); // 设置过期时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将密码设为null,返回给前端 脱密
        user.setPassword(null);
        resultMessage.success("001", "登录成功", user);
        return resultMessage;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public ResultMessage register(@RequestBody User user) {
        userService.register(user);
        resultMessage.success("001", "注册成功");
        return resultMessage;
    }

    /**
     * 判断用户名是否已存在
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    @ApiOperation(value = "判断用户名是否已存在")
    public ResultMessage username(@PathVariable String username) {
        userService.isUserName(username);
        resultMessage.success("001", "可注册");
        return resultMessage;
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    // TODO: 2023/11/26 根据token获取用户信息报错
    @GetMapping("/token")
    @ApiOperation(value = "根据token获取用户信息")
    public ResultMessage token(@CookieValue("XM_TOKEN") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = redisTemplate.opsForHash().entries(token);
        // 可能map为空 ， 即redis中时间已过期，但是cookie还存在。
        // 这个时候应该删除cookie，让用户重新登录
        if (map.isEmpty()) {
            CookieUtil.delCookie(request, token);
            resultMessage.fail("002", "账号过期,请重新登录");
            return resultMessage;
        }

        redisTemplate.expire(token, 30 * 60, TimeUnit.SECONDS); // 设置过期时间
        User user = BeanUtil.map2bean(map, User.class);
        user.setPassword(null);
        resultMessage.success("001", user);
        return resultMessage;
    }

}
