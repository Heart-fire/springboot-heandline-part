package com.atguigu.mapper;

import com.atguigu.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author January
* @description 针对表【news_user】的数据库操作Mapper
* @createDate 2024-04-19 17:40:39
* @Entity com.atguigu.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




