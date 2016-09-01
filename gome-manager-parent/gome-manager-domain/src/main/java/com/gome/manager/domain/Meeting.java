package com.gome.manager.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * 会议实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class Meeting {
	
	DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	//会议ID
	private Long id;
	
	//会议编号
	private String code;
	
	//会议名称
	private String name;
	
	//参考价格
	private String theme;
	
	//图片地址
	private String picPath;
	
	//会议描述
	private String description;
	
	
	//状态(0:关闭   1:开启)
	private String status;
	
	//修改时间
	private String beginTime;
	
	//修改时间
	private String endTime;
		
	//创建时间
	private String createTime;
	
	//创建人
	private String createPer;
	
	//操作账号
	private String operateUser;
		
	//欢迎信
	private String letterContent;
	
	//欢迎信
	private String letterPic;
	
	//欢迎信
	private String meetAddr;
	
	
	public String getMeetAddr() {
		return meetAddr;
	}

	public void setMeetAddr(String meetAddr) {
		this.meetAddr = meetAddr;
	}

	public String getLetterPic() {
		return letterPic;
	}

	public void setLetterPic(String letterPic) {
		this.letterPic = letterPic;
	}

	public String getLetterContent() {
		return letterContent;
	}

	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreatePer() {
		return createPer;
	}

	public void setCreatePer(String createPer) {
		this.createPer = createPer;
	}
	
	
}
