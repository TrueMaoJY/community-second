package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("message")
public class MessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private Integer fromId;
	/**
	 * 
	 */
	private Integer toId;
	/**
	 * 
	 */
	private String conversationId;
	/**
	 * 
	 */
	private String content;
	/**
	 * 0-未读;1-已读;2-删除;
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;

}
