package com.atguigu.service.impl;

import com.atguigu.pojo.PortalVo;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import io.jsonwebtoken.Jwt;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author January
 * @description 针对表【news_headline】的数据库操作Service实现
 * @createDate 2024-04-19 17:40:39
 */
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline> implements HeadlineService {
    @Autowired
    private HeadlineMapper headlineMapper;
    @Autowired
    private JwtHelper jwtHelper;
    /*
     * 首页数据查询
     * 1.进行分页查询
     * 2.分页数据 拼接到result即可
     * 注意：查询需自定义语句，自定义mapper方法，携带分页
     * */
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        //当前页数---->页容量
        IPage<Map> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());
        headlineMapper.selectMyPage(page, portalVo);

        List<Map> records = page.getRecords();
        Map data = new HashMap();
        data.put("pageData", records);
        data.put("pageNum", page.getCurrent());
        data.put("pageSize", page.getSize());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo", data);

        return Result.ok(pageInfo);
    }

    /*
    * 根据ID查询详情
    * 用户点击"查看全文"时,向服务端发送新闻id
    - 后端根据新闻id查询完整新闻文章信息并返回
    - 后端要同时让新闻的浏览量+1
    *
    * */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        //1.实现根据id的查询(多表
        Map headLineDetail = headlineMapper.selectDetailMap(hid);

        //修改阅读量+1 //2.拼接头条对象(阅读量和version)进行数据更新
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setPageViews((Integer) headLineDetail.get("pageViews") + 1); //阅读量+1
        headline.setVersion((Integer) headLineDetail.get("version")); //设置版本
        headlineMapper.updateById(headline);

        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("headline", headLineDetail);
        return Result.ok(pageInfoMap);
    }

    /*
     * 补全数据
     * */
    @Override
    public Result publish(Headline headline, String token) {
        //token查询用户ID
        int userId = jwtHelper.getUserId(token).intValue();
        //数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    @Override
    public Result updateHeadLine(Headline headline) {
        //读取版本
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }


}




