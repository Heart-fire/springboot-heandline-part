package com.atguigu.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
/**
 * 
 * @TableName news_headline
 */
@Data
public class Headline implements Serializable {
    /**
     * 头条id
     */
    @TableId(type = IdType.AUTO)
    private Integer hid;

    /**
     * 头条标题
     */
    private String title;

    /**
     * 头条新闻内容
     */
    private String article;

    /**
     * 头条类型id
     */
    private Integer type;

    /**
     * 头条发布用户id
     */
    private Integer publisher;

    /**
     * 头条浏览量
     */
    private Integer pageViews;

    /**
     * 头条发布时间
     */
    private Date createTime;

    /**
     * 头条最后的修改时间
     */
    private Date updateTime;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 头条是否被删除 1 删除  0 未删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}