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

import com.example.community.entity.LoginTicketEntity;
import com.example.community.service.LoginTicketService;





/**
 * 
 *
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@RestController
@RequestMapping("community/loginticket")
public class LoginTicketController {
    @Autowired
    private LoginTicketService loginTicketService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:loginticket:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = loginTicketService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("community:loginticket:info")
    public R info(@PathVariable("id") Integer id){
		LoginTicketEntity loginTicket = loginTicketService.getById(id);

        return R.ok().put("loginTicket", loginTicket);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("community:loginticket:save")
    public R save(@RequestBody LoginTicketEntity loginTicket){
		loginTicketService.save(loginTicket);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:loginticket:update")
    public R update(@RequestBody LoginTicketEntity loginTicket){
		loginTicketService.updateById(loginTicket);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:loginticket:delete")
    public R delete(@RequestBody Integer[] ids){
		loginTicketService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
