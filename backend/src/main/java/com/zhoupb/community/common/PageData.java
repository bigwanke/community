package com.zhoupb.community.common;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页数据
 * 
 * @author zhoupb
 *
 */
public class PageData<T> {

	private List<T> data = null;

	private long total = 0;

	private long size = 0;

	private long current = 0;

	private long pages = 0;

	private Boolean hasPrevious = null;

	private Boolean hasNext = null;
	
	public PageData() {
	}

	public PageData(Page<T> page) {
		this.data = page.getRecords();
		this.total = page.getTotal();
		this.size = page.getSize();
		this.current = page.getCurrent();
		this.pages = page.getPages();
		this.hasPrevious = page.hasPrevious();
		this.hasNext = page.hasNext();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getCurrent() {
		return current;
	}

	public void setCurrent(long current) {
		this.current = current;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public Boolean getHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(Boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public static <T1> PageData<T1> transformType(List<T1> list, PageData<?> page) {
		PageData<T1> res = new PageData<T1>();
		res.data = list;
		res.total = page.getTotal();
		res.size = page.getSize();
		res.current = page.getCurrent();
		res.pages = page.getPages();
		res.hasPrevious = page.getHasPrevious();
		res.hasNext = page.getHasNext();
		return res;
	}
	
}
