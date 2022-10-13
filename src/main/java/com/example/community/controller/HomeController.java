package com.example.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.entity.DiscussPostEntity;
import com.example.community.entity.UserEntity;
import com.example.community.service.DiscussPostService;
import com.example.community.service.UserService;
import com.example.community.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MaoJY
 * @create 2022-10-09 19:37
 * @Description:
 */
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
    @RequestMapping({"/index","/"})
    public String queryIndex(Model model, Page page){
        PageUtils pageUtils = discussPostService.queryIndex(0, (int) page.getCurrent(), (int) page.getSize());
        pageUtils.setPath("/index");
        List<Map<String,Object>> discussPosts=new ArrayList<>();
        if(pageUtils.getList()!=null){
            for (Object entity : pageUtils.getList()) {
             DiscussPostEntity   newEntity=(DiscussPostEntity) entity;
                Map<String,Object> map=new HashMap<>();
                map.put("post",entity);
                UserEntity user = userService.getById(newEntity.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        model.addAttribute("page",pageUtils);
        return "index";
    }
    @RequestMapping("/queryAll")
    public  String queryAll(Model model){
        List<DiscussPostEntity> discussPostEntities = discussPostService.queryAll(0,0,0);
        List<Map<String,Object>> discussPosts=new ArrayList<>();
        if(discussPostEntities!=null){
            for (DiscussPostEntity entity : discussPostEntities) {
                Map<String,Object> map=new HashMap<>();
                map.put("post",entity);
                UserEntity user = userService.getById(entity.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "index";
    }

}