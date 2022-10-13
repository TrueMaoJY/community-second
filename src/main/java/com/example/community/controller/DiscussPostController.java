package com.example.community.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.community.utils.PageUtils;
import com.example.community.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.entity.DiscussPostEntity;
import com.example.community.service.DiscussPostService;




/**
 * 
 *
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@RestController
@RequestMapping("community/discusspost")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:discusspost:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = discussPostService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("community:discusspost:info")
    public R info(@PathVariable("id") Integer id){
		DiscussPostEntity discussPost = discussPostService.getById(id);
        return R.ok().put("discussPost", discussPost);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("community:discusspost:save")
    public R save(@RequestBody DiscussPostEntity discussPost){
		discussPostService.save(discussPost);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:discusspost:update")
    public R update(@RequestBody DiscussPostEntity discussPost){
		discussPostService.updateById(discussPost);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:discusspost:delete")
    public R delete(@RequestBody Integer[] ids){
		discussPostService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
