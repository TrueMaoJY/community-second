package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.community.entity.LoginTicketEntity;
import com.example.community.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
public interface LoginTicketService extends IService<LoginTicketEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int insert(LoginTicketEntity entity);

    LoginTicketEntity findUserByTicket(String ticket);
}

