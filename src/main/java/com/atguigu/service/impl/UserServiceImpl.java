package com.atguigu.service.impl;

import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author January
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2024-04-19 17:40:39
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /*登录业务实现
     * 1.根据账号->查询用户对象
     * 2.如果账号为空-->查询失败-->账号错误!-->返回501
     * 3.输入不为空-->查询数据库账号对比密码-->如果失败返回503错误
     * 4.密码正确-->根据用户ID生成Token,token-->返回resut
     * */
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        //根据账号查询数据库
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);//返回一个
        //判断账户
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);//返回501异常，账号为空
        }
        /*-对比密码--正确则登录成功--否则登录失败*/
        if (!StringUtils.isNullOrEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            //1.密码正确--登录成功
            //2.根据用户ID生成token---拿什么生成呢(用Jwt工具)
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            //将token封装到result返回
            HashMap data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        }
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);//返回503，密码错误
    }

    /*
     * 根据token获取用户信息
     * 1.token是否在有效期内
     * 2.根据token解析用户ID
     * 3.根据ID查询数据
     * 4.去掉密码，封装result结果返回即可
     * */
    @Override
    public Result getUserInfo(String token) {
        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        //2.获取token对应的用户
        int userId = jwtHelper.getUserId(token).intValue();
        //3.查询数据
        User user = userMapper.selectById(userId);

        if (user != null) {
            user.setUserPwd(null);
            Map data = new HashMap();
            data.put("loginUser", user);
            return Result.ok(data);
        }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    /*
     * 检查账号是否可用
     * 1.根据账号进行查询count
     * 2.count==0 可用
     * 3.count>0 不能用
     * */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(queryWrapper);

        if (count == 0) {
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }
    /*
      注册业务
    * 1.依然检查 账户是否已被注册
    * 2.密码加密处理
    * 3.账户数据保存
    * 4.返回结果
    * */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
        //用户已被使用，返回错误
        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        //密码加密
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);
        return Result.ok(null);
    }

}




