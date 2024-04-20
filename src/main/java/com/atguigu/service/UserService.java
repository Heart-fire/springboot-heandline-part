package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author January
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-04-19 17:40:39
*/
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取登录用户数据
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 查询用户名是否可用
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册业务
     * @param user
     * @return
     */
    Result regist(User user);
}
