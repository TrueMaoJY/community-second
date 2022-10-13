package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.community.entity.DiscussPostEntity;
import com.example.community.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
public interface DiscussPostService extends IService<DiscussPostEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<DiscussPostEntity> queryAll(int userId,int current,int pageSize);
    PageUtils queryIndex(int userId,int current,int pageSize);
}

