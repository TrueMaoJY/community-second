package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.community.entity.LoginTicketEntity;
import com.example.community.service.LoginTicketService;
import com.example.community.utils.*;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.community.dao.UserDao;
import com.example.community.entity.UserEntity;
import com.example.community.service.UserService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService, CommunityConstant {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private LoginTicketService loginTicketService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity findUserByName(String username) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }

    @Override
    public Map<String, String> register(UserEntity user) {
        Map<String, String> r = new HashMap<>();
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            r.put("usernameMsg", "用户名不能为空");
            return r;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            r.put("passwordMsg", "密码不能为空");
            return r;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            r.put("emailMsg", "邮箱不能为空");
            return r;
        }
        UserEntity exist = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", user.getUsername()));
        if (exist != null) {
            r.put("usernameMsg", "用户名已经存在");
            return r;
        }
        exist = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("email", user.getEmail()));
        if (exist != null) {
            r.put("emailMsg", "邮箱已被注册");
            return r;
        }
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getSalt() + user.getPassword()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        //http://localhost:8080/community/
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        int insert = baseMapper.insert(user);
        System.out.println(insert+"*****");
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain +contextPath+ "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);
        return r;
    }
    /**
    * Description:激活账号
    * date: 2022/10/25 19:22
    * @author: MaoJY
    * @since JDK 1.8
    */
    @Override
    public int activation(int userId, String code) {
        UserEntity userEntity = baseMapper.selectById(userId);
        if (userEntity.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (userEntity.getActivationCode().equals(code)) {
           /* 错误方式 会导致全表更新 int status = baseMapper.update(userEntity, new UpdateWrapper<UserEntity>().set("status", 1));
            System.out.println(status);*/
            /*正确方式1：baseMapper.update(null,new UpdateWrapper<UserEntity>().
                    eq("id",userEntity.getId()));*/
            //正确方式2
            userEntity.setStatus(1);
            baseMapper.updateById(userEntity);

            /*方式三
            *  userEntity.setStatus(1);
            baseMapper.update(userEntity,new
            * UpdateWrapper<UserEntity>().eq("id",userEntity.getId()));
            * */
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }
    /**
    * Description:登录
    * date: 2022/10/25 19:22
    * @author: MaoJY
    * @since JDK 1.8
    */
    @Override
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "用户名为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码为空!");
            return map;
        }
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
        if (user == null) {
            map.put("usernameMsg", "该用户不存在");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameMsg","用户未激活");
            return map;
        }
        password = CommunityUtil.md5( user.getSalt()+password);
        if (!StringUtils.equals(password, user.getPassword())) {
            map.put("passwordMsg", "密码错误！");
            return  map;
        }
        LoginTicketEntity entity=new LoginTicketEntity();
        entity.setUserId(user.getId());
        entity.setStatus(0);
        entity.setTicket(CommunityUtil.generateUUID());
        entity.setExpired(new Date(System.currentTimeMillis()+expiredSeconds*1000));
        int row=loginTicketService.insert(entity);
        map.put("ticket",entity.getTicket());
        return map;
    }
    /**
    * Description:登出
    * date: 2022/10/26 18:56
    * @author: MaoJY
    * @since JDK 1.8
    */
    public void logout(String ticket){
        loginTicketService.update(new UpdateWrapper<LoginTicketEntity>()
                .eq("ticket",ticket).set("status",1));
    }

    @Override
    public int updateHeader(Integer id, String filename) {
       return baseMapper.update(null,new UpdateWrapper<UserEntity>().eq("id",id).set("header_url",filename));
    }

    @Override
    public int updatePassword(Integer id, String newPassword) {
        return baseMapper.update(null,
                new UpdateWrapper<UserEntity>().eq("id",id).set("password",newPassword));
    }
}