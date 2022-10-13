package com.example.community.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.community.entity.CommentEntity;
import com.example.community.service.CommentService;
import com.example.community.utils.*;



/**
 * 
 *
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@Controller
@RequestMapping("community/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
    * Description:自定义分页查询
    * date: 2022/10/9 20:45
    * @author: MaoJY
    * @since JDK 1.8
    */
    @ResponseBody
    @RequestMapping("/queryPage")
    public R queryAll(@RequestParam(name = "page",defaultValue = "1") Object curPage,
                      @RequestParam(name = "limit",defaultValue = "10") Object limit){
        Map<String,Object> params=new HashMap<>();
        params.put("page",curPage);
        params.put("limit",limit);
        PageUtils page = commentService.queryPage(params);
        System.out.println(page.getCurrent());
        System.out.println(page.getPageSize());
        return  R.ok().put("page",page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:comment:list")
    public R list(@RequestParam Map<String, Object> params){

        /*
        pageUtils 封装了分页信息和页面的数据
         */
        PageUtils page = commentService.queryPage(params);
        Page page1 = (Page) params.get("page");

//        List<?> list = page.getList();
//        for (Object o : list) {
//            System.out.println(((CommentEntity) o).getContent());
//        }
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("community:comment:info")
    public R info(@PathVariable("id") Integer id){
		CommentEntity comment = commentService.getById(id);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("community:comment:save")
    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:comment:update")
    public R update(@RequestBody CommentEntity comment){
		commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:comment:delete")
    public R delete(@RequestBody Integer[] ids){
		commentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
