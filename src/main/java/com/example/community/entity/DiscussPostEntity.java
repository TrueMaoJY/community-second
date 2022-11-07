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
@TableName("discuss_post")
public class DiscussPostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String content;
	/**
	 * 0-普通; 1-置顶;
	 */
	private Integer type;
	/**
	 * 0-正常; 1-精华; 2-拉黑;
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer commentCount;
	/**
	 * 
	 */
	private Double score;

}
