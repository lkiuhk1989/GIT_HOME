package com.gome.manager.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * 会议参会人员实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class MeetingJoinPer {
	
	DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	//ID
	private Long id;
	
	//会议编号
	private String code;
	
	//名称
	private String name;
	
	//状态：0.未签到，1已签到
	private String status;
	
	//签到时间
	private String openFlag;
	//签到时间
	private String signTime;
	//openID
	private String openId;
	//单位
	private String unit;
	//科室
	private String office;
	//职称
	private String jobs;
	//职务
	private String position;
	//openFlag
	private String phone;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getJobs() {
		return jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	
}
