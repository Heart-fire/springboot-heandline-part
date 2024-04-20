package com.atguigu.controller;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.PortalVo;
import com.atguigu.pojo.Type;
import com.atguigu.service.HeadlineService;
import com.atguigu.service.TypeService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;

    /*----进入新闻首页,查询所有分类并动态展示新闻类别栏位---*/
    @Autowired
    private HeadlineService headlineService;

    /**
     * 查询全部类别信息
     *
     * @return
     */
    @GetMapping("findAllTypes")
    public Result findAllTypes() {
        //直接调用业务层,查询全部数据
        Result result = typeService.findAllTypes();
        return result;
    }

    /*客户端向服务端发送查询关键字,新闻类别,页码数,页大小
     * VO是前端传后端数据的载体，DTO类是后端传给前端数据传输对象载体 */
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo) {
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    /**
     * 根据ID查询该新闻全部信息
     * @param hid
     * @return
     */
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return  result;
    }
}