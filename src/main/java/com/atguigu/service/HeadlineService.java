package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author January
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-04-19 17:40:39
*/
public interface HeadlineService extends IService<Headline> {
    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     *
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 发布头条的方法
     * @param headline
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 头条修改实现
     * @param headline
     * @return
     */
    Result updateHeadLine(Headline headline);

}
