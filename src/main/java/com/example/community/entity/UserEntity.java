package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private String salt;
	/**
	 * 
	 */
	private String email;
	/**
	 * 0-普通用户; 1-超级管理员; 2-版主;
	 */
	private Integer type;
	/**
	 * 0-未激活; 1-已激活;
	 */
	private Integer status;
	/**
	 * 
	 */
	private String activationCode;
	/**
	 * 
	 */
	private String headerUrl;
	/**
	 * 
	 */
	private Date createTime;

}
