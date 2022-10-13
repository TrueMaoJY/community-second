package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.utils.PageUtils;
import com.example.community.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.community.dao.DiscussPostDao;
import com.example.community.entity.DiscussPostEntity;
import com.example.community.service.DiscussPostService;


@Service("discussPostService")
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostDao, DiscussPostEntity> implements DiscussPostService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DiscussPostEntity> page = this.page(
                new Query<DiscussPostEntity>().getPage(params),
                new QueryWrapper<DiscussPostEntity>()
        );

        return new PageUtils(page);
    }
    /**
    * Description:查询所有帖子
    * date: 2022/10/9 21:07
    * @author: MaoJY
    * @since JDK 1.8
    */
    @Override
    public List<DiscussPostEntity> queryAll(int userId,int offset,int limit) {
        QueryWrapper<DiscussPostEntity> w = new QueryWrapper<>();
        if(userId!=0){
            w.eq("user_id",userId);
        }
        Page<DiscussPostEntity> page=new Page<>(offset,limit);
        IPage<DiscussPostEntity> iPage = this.page(page, w.orderByDesc("create_time"));
        return iPage.getRecords();
    }
    /**
     * Description:查询首页
     * date: 2022/10/9 21:07
     * @author: MaoJY
     * @since JDK 1.8
     */
    @Override
    public PageUtils queryIndex(int userId,int current,int pageSize) {
        QueryWrapper<DiscussPostEntity> w = new QueryWrapper<>();
        if(userId!=0){
            w.eq("user_id",userId);
        }
        if(current==0)current=1;
        if(pageSize==0)pageSize=10;
        Page<DiscussPostEntity> page=new Page<>(current,pageSize);
        IPage<DiscussPostEntity> iPage = this.page(page, w.orderByDesc("create_time"));
        return new PageUtils(iPage);
    }

}