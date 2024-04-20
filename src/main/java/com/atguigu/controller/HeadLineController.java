package com.atguigu.controller;

import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadLineController {
    @Autowired
    private HeadlineService headlineService;

    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline, @RequestHeader String token) {
        Result result = headlineService.publish(headline, token);
        return result;
    }

    /*    前端先调用登录校验接口,校验登录是否过期
          登录校验通过后 ,则根据新闻id查询新闻的完整信息并响应给前端   */
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineService.getById(hid);
        Map data = new HashMap();
        data.put("headline", headline);
        return Result.ok(data);
    }

    /*
     * 头条修改实现
     * */
    @PostMapping("update")
    public Result update(@RequestBody Headline headline) {
        Result result = headlineService.updateHeadLine(headline);
        return result;
    }


    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){
       headlineService.removeById(hid);
       return Result.ok(null);
    }
}
