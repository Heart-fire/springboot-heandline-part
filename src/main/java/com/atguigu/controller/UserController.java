package com.atguigu.controller;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    /**
     * 登录需求
     * 地址: /user/login
     * 方式: post
     * 参数:
     * {
     * "username":"zhangsan", //用户名
     * "userPwd":"123456"     //明文密码
     * }
     * 返回:
     * {
     * "code":"200",         // 成功状态码
     * "message":"success"   // 成功状态描述
     * "data":{
     * "token":"... ..." // 用户id的token
     * }
     * }
     * 大概流程:
     * 1. 账号进行数据库查询 返回用户对象
     * 2. 对比用户密码(md5加密)
     * 3. 成功,根据userId生成token -> map key=token value=token值 - result封装
     * 4. 失败,判断账号还是密码错误,封装对应的枚举错误即可
     */
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;
    /*
     * 用户登录
     * */
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        Result result = userService.login(user);
        System.out.println("result = " + result);
        return result;
    }

    /* 地址: user/getUserInfo
     * 方式: get
     * 请求头: token = token内容
     * 客户端发送请求,提交token请求头,后端根据token请求头获取登录用户的详细信息并响应给客户端进行存储
     * @RequestHeader
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        Result result = userService.getUserInfo(token);
        return result;
    }

    /*
    checkUserName
    用户在注册-输入用户名时,立刻将用户名发送给后端,后端根据用户名-查询用户名-是否可用并做出响应
    * */
    @PostMapping("checkUserName")
    public Result checkUserName(String username) {
        Result result = userService.checkUserName(username);
        return result;
    }

    /*客户端将新用户信息发送给服务端,服务端将新用户存入数据库,存入之前做用户名是否被占用校验,
      校验通过响应成功提示,否则响应失败提示
    * */
    @PostMapping("regist")
    public Result regist(@RequestBody User user) {
        Result result = userService.regist(user);
        return result;
    }

    /*登陆验证和保护*/
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration){
            //已经过期
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
