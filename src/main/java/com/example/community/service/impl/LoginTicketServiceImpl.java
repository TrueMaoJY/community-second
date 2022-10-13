package com.example.community.service.impl;

import com.example.community.utils.PageUtils;
import com.example.community.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.community.dao.LoginTicketDao;
import com.example.community.entity.LoginTicketEntity;
import com.example.community.service.LoginTicketService;


@Service("loginTicketService")
public class LoginTicketServiceImpl extends ServiceImpl<LoginTicketDao, LoginTicketEntity> implements LoginTicketService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LoginTicketEntity> page = this.page(
                new Query<LoginTicketEntity>().getPage(params),
                new QueryWrapper<LoginTicketEntity>()
        );

        return new PageUtils(page);
    }

}