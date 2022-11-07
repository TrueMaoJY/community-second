package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.community.entity.UserEntity;
import com.example.community.utils.PageUtils;
import com.example.community.utils.R;

import java.util.Map;

/**
 * 
 *
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
    UserEntity findUserByName(String username);

    Map<String,String> register(UserEntity user);
    int activation(int userId,String code);

    Map<String,Object> login(String username, String password, int expiredSeconds);
    void logout(String ticket);

    int updateHeader(Integer id, String filename);

    int updatePassword(Integer id, String newPassword);
}

