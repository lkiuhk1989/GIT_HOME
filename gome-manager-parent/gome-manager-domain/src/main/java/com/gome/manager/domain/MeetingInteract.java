package com.gome.manager.domain;



/**
 * 
 * 会议投票实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class MeetingInteract {
	
	
	//ID
	private Long id;
	
	private Long maxInteractId;
	//会议编号
	private String code;
	
	//投票人
	private String perId;
	
	private String perName;
	
	private String unitName;
	
	//题目编号
	private String content;
	
	//选择答案
	private String times;
	
	//选择答案
	private String iswallrun;
	
	private String floor;
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Long getMaxInteractId() {
		return maxInteractId;
	}

	public void setMaxInteractId(Long maxInteractId) {
		this.maxInteractId = maxInteractId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
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

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getIswallrun() {
		return iswallrun;
	}

	public void setIswallrun(String iswallrun) {
		this.iswallrun = iswallrun;
	}
	
}
