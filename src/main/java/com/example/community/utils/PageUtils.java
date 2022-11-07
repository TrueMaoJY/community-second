/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.community.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private int total;
	/**
	 * 每页记录数
	 */
	private int pageSize=10;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int current=1;
	/**
	 * 列表数据
	 */
	/**
	* Description:起始页码
	* date: 2022/10/11 20:32
	* @author: MaoJY
	* @since JDK 1.8
	*/
	private int from;
	/**
	* Description:结束页码
	* date: 2022/10/11 20:32
	* @author: MaoJY
	* @since JDK 1.8
	*/
	private int to;
	/**
	* Description:复用链接
	* date: 2022/10/11 20:38
	* @author: MaoJY
	* @since JDK 1.8
	*/

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getFrom() {
		return Math.max(current-2,1);
	}

	public int getTo() {
		return Math.min(current+2,totalPage);
	}

	private List<?> list;
	/**
	* Description:偏移量 当前是第几条记录
	*/
	private long offset;
	public long getOffset(){
		return  offset;
	}
	/**
	 * 分页
	 * @param list        列表数据
	 * @param total  总记录数
	 * @param pageSize    每页记录数
	 * @param current    当前页数
	 */
	public PageUtils(List<?> list, int total, int pageSize, int current) {
		this.list = list;
		this.total = total;
		this.pageSize = pageSize;
		this.current = current;
		this.totalPage = (int)Math.ceil((double)total/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(IPage<?> page) {
		this.list = page.getRecords();
		this.total = (int)page.getTotal();
		this.pageSize = (int)page.getSize();
		this.current = (int)page.getCurrent();
		this.totalPage = (int)page.getPages();
		this.from=getFrom();
		this.to=getTo();
		this.offset=page.offset();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}
