package com.gome.manager.domain;


/**
 * 
 * 会议资料实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class MeetingData {
	
	
	//ID
	private Long id;
	//会议编号
	private String code;
	
	//名称
	private String name;
	
	//状态：0.未签到，1已签到
	private String pictureUrl;
	
	//签到时间
	private String fileUrl;
	
	//签到时间
	private String detail;
	private String speaker;
	private String hospitol;

	
	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getHospitol() {
		return hospitol;
	}

	public void setHospitol(String hospitol) {
		this.hospitol = hospitol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
