package com.example.community.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

import com.example.community.annotation.LoginRequired;
import com.example.community.utils.CommunityUtil;
import com.example.community.utils.HostHolder;
import com.example.community.utils.PageUtils;
import com.example.community.utils.R;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.community.entity.UserEntity;
import com.example.community.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${community.path.upload}")
    private String uploadPath;
    @LoginRequired
    @GetMapping("/setting")
    public String setting() {
        return "site/setting";
    }

    /**
     * Description:上传头像
     * date: 2022/10/27 18:38
     *
     * @author: MaoJY
     * @since JDK 1.8
     */
    @LoginRequired
    @PostMapping("/upload")
    public String upload(MultipartFile headerImg, Model model) {
        if (headerImg == null) {
            model.addAttribute("error","您还未上传图片");
            return "site/setting";
        }
        String filename = headerImg.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error","文件格式不正确");
            return "site/setting";
        }
        //新文件名
        filename = CommunityUtil.generateUUID() + suffix;
        //保存路径
        File file = new File(uploadPath + "/" + filename);
        try {
            //存储文件
            headerImg.transferTo(file);
        } catch (IOException e) {
            logger.error("保存图片失败"+e.getMessage());
            throw new RuntimeException("存储图片失败"+e);
        }
        //修改图片访问路径
        //http://localhost:8080/community/user/head/*.png;
        UserEntity user = hostHolder.getUser();
        filename=domain+contextPath+"/user/head/"+filename;
        System.out.println(filename);
        userService.updateHeader(user.getId(), filename);
        return "redirect:/user/setting";
    }

    @GetMapping("/head/{filename}")
    public void header(@PathVariable("filename") String filename, HttpServletResponse response) {
        //http://localhost:8080/community/user/head/*.png;
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        filename=uploadPath+"/"+filename;
        response.setContentType("image/" + suffix);
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            OutputStream os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(bytes)) != -1) {
                os.write(bytes, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取图像失败"+e.getMessage());
            e.printStackTrace();
        }
    }
    @LoginRequired
    @PostMapping("/updatePassword")
    public String updatePassword(String original,String newPassword,Model model,
            @CookieValue("ticket") String ticket){
        if(StringUtils.isBlank(original)){
            model.addAttribute("originalMsg","原密码不能为空");
            return  "site/setting";
        }
        if(StringUtils.isBlank(newPassword)){
            model.addAttribute("newMsg","新密码不能为空");
            return  "site/setting";
        }
        UserEntity user = hostHolder.getUser();
        String password=CommunityUtil.md5(user.getSalt()+original);
        if(!user.getPassword().equals(password)){
            model.addAttribute("originalMsg","密码不正确！");
            return  "site/setting";
        }
        newPassword=CommunityUtil.md5(user.getSalt()+newPassword);
        userService.updatePassword(user.getId(),newPassword);
        userService.logout(ticket);
        return  "redirect:/login";
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("community:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("community:user:info")
    public R info(@PathVariable("id") Integer id) {
        UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("community:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("community:user:update")
    public R update(@RequestBody UserEntity user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("community:user:delete")
    public R delete(@RequestBody Integer[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
