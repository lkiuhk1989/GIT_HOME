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
public class Vedio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 295637410548020812L;

	//ID
	private Long id;
	//主题
	private String title;
	//图片
	private String picPath;
	private String belong;
	private String fileUrl;
	//发表日期
	private String createTime;
	
	private String vedioSize;
	
	private String totalSee;
	
	private String vType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
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

	public String getVedioSize() {
		return vedioSize;
	}

	public void setVedioSize(String vedioSize) {
		this.vedioSize = vedioSize;
	}

	public String getTotalSee() {
		return totalSee;
	}

	public void setTotalSee(String totalSee) {
		this.totalSee = totalSee;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

}
