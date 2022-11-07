package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.utils.PageUtils;
import com.example.community.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.community.dao.CommentDao;
import com.example.community.entity.CommentEntity;
import com.example.community.service.CommentService;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                new QueryWrapper<CommentEntity>()
        );
        return new PageUtils(page);
    }
/**
* Description:根据entityType 1 评论+ 帖子id； 2 回复+评论id 查询列表
* date: 2022/11/3 19:45
* @author: MaoJY
* @since JDK 1.8
*/
    @Override
    public PageUtils findCommentsByEntity(int entityTypePost, Integer id, long current, long size) {
        Page<CommentEntity> page = new Page<>(current, size);
        QueryWrapper<CommentEntity> wrapper = new QueryWrapper<CommentEntity>().
                eq("entity_type", entityTypePost).eq("entity_id", id);
        IPage<CommentEntity> iPage= this.page(page, wrapper.orderByDesc("create_time"));
        return new PageUtils(iPage);
    }
/**
* Description:每个评论的回复数
* date: 2022/11/3 19:45
* @author: MaoJY
* @since JDK 1.8
*/
    @Override
    public int findCommentCount(int entityTypeComment, Integer id) {
        Long count = baseMapper.selectCount(new QueryWrapper<CommentEntity>().
                eq("entity_type", entityTypeComment).
                eq("entity_id", id));
        return Integer.parseInt(count.toString());
    }

}