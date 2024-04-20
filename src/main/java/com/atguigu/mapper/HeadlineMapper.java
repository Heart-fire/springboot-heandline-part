package com.atguigu.mapper;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author January
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-04-19 17:40:39
* @Entity com.atguigu.pojo.Headline
*/
@Mapper
public interface HeadlineMapper extends BaseMapper<Headline> {
    //自定义分页查询方法
    IPage<Map> selectMyPage(IPage page, @Param("portalVo") PortalVo portalVo);

    Map selectDetailMap(Integer hid);
}




