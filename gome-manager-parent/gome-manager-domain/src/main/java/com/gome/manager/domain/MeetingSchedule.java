package com.gome.manager.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 会议会议日程实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class MeetingSchedule {
	
	
	//ID
	private Long id;
	//ID
	private Long stageId;
	
	//会议编号
	private String code;
	
	//名称
	private String stage;
	
	//状态：0.未签到，1已签到
	private String status;
	
	//签到时间
	private String times;
	
	//签到时间
	private String beginTimes;
	
	//签到时间
	private String endTimes;
	//签到时间
	private String dateStr;
	//签到时间
	private String timeStr;
	//签到时间
	private String content;
	private String num;
	
	
	List<MeetingSchedule> scheduleList = new ArrayList<MeetingSchedule>();
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public List<MeetingSchedule> getScheduleList() {
		return scheduleList;
	}
	public void setScheduleList(List<MeetingSchedule> scheduleList) {
		this.scheduleList = scheduleList;
	}
	public String getBeginTimes() {
		return beginTimes;
	}
	public void setBeginTimes(String beginTimes) {
		this.beginTimes = beginTimes;
	}
	public String getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getStageId() {
		return stageId;
	}
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
