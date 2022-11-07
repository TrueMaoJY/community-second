package com.example.community.controller;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.community.annotation.LoginRequired;
import com.example.community.entity.CommentEntity;
import com.example.community.entity.UserEntity;
import com.example.community.service.CommentService;
import com.example.community.service.UserService;
import com.example.community.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.community.entity.DiscussPostEntity;
import com.example.community.service.DiscussPostService;
import org.springframework.web.util.HtmlUtils;


/**
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@Controller
@RequestMapping("/discusspost")
public class DiscussPostController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private CommentService commentService;

    /**
     * Description:帖子详情
     * date: 2022/11/1 19:27
     *
     * @author: MaoJY
     * @since JDK 1.8
     */
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model,Long current) {

        DiscussPostEntity discussPost = discussPostService.getById(id);
        UserEntity user = userService.getById(discussPost.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("post", discussPost);
//        分页
        Page<CommentEntity> page = new Page<>();
        page.setSize(5);
      if(current!=null) page.setCurrent(current);
        PageUtils allComments = commentService.findCommentsByEntity(ENTITY_TYPE_POST, id,
                page.getCurrent(), page.getSize());
        allComments.setPath("/discusspost/detail/"+id);
        model.addAttribute("page",allComments);
        List<?> commentsList = allComments.getList();
        //封装评论
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (commentVoList != null) {
            for (Object o : commentsList) {
                CommentEntity comment=(CommentEntity) o;
                Map<String,Object>  commentVo=new HashMap<>();
                commentVo.put("comment",comment);
                commentVo.put("user",userService.getById(comment.getUserId()));
                //封装回复
                PageUtils replyListPage = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Long.MAX_VALUE);
                List<?> replyList = replyListPage.getList();
                List<Map<String,Object>> replyVoList=new ArrayList<>();
                if (replyList != null) {
                    for (Object o1 : replyList) {
                        CommentEntity reply=(CommentEntity) o1;
                        Map<String,Object> replyVo=new HashMap<>();
                        replyVo.put("reply",reply);
                        replyVo.put("user",userService.getById(reply.getUserId()));
                        UserEntity target = userService.getById(reply.getTargetId());
                        replyVo.put("target",target);
                        replyVoList.add(replyVo);
                    }
                }
                //每个评论的回复数
                int replyCount= commentService.findCommentCount(ENTITY_TYPE_COMMENT,comment.getId());
                commentVo.put("replyCount",replyCount);
                commentVo.put("replys",replyVoList);
                commentVoList.add(commentVo);
            }
            model.addAttribute("comments",commentVoList);
        }
        return "/site/discuss-detail";
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:discusspost:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = discussPostService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @ResponseBody
    //@RequiresPermissions("community:discusspost:info")
    public R info(@PathVariable("id") Integer id) {
        DiscussPostEntity discussPost = discussPostService.getById(id);
        return R.ok().put("discussPost", discussPost);
    }

    /**
     * 发表帖子
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping("/save")
    //@RequiresPermissions("community:discusspost:save")
    public R save(DiscussPostEntity discussPost) {
        UserEntity user = hostHolder.getUser();
        discussPost.setUserId(user.getId().toString());
        discussPost.setType(0);
        discussPost.setCreateTime(new Date());
        discussPost.setStatus(0);
        //对敏感词，标签等进行处理
        discussPost.setTitle(handleText(discussPost.getTitle()));
        discussPost.setContent(handleText(discussPost.getContent()));
        discussPostService.save(discussPost);
        return R.ok();
    }

    private String handleText(String content) {
        if (StringUtils.isBlank(content)) return content;
        return sensitiveFilter.filter(HtmlUtils.htmlEscape(content));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:discusspost:update")
    public R update(DiscussPostEntity discussPost) {
        discussPostService.updateById(discussPost);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:discusspost:delete")
    public R delete(@RequestBody Integer[] ids) {
        discussPostService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
