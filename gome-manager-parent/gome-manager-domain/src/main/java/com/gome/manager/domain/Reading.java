package com.gome.manager.domain;

import java.io.Serializable;

/**
 * 
 * 会议实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class Reading implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 295637410548020812L;

	//ID
	private Long id;
	
	//名称
	private String catname;
	
	//期数
	private String yearValue;
	//主题
	private String title;
	//图片
	private String fileUrl;
	//发表日期
	private String createTime;
	private String author;
	private String atype;
	private String typeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getYearValue() {
		return yearValue;
	}

	public void setYearValue(String yearValue) {
		this.yearValue = yearValue;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
