package com.example.community.dao;

import com.example.community.entity.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author maoJY
 * @email 804259917@qq.com
 * @date 2022-10-08 20:20:44
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
	
}
