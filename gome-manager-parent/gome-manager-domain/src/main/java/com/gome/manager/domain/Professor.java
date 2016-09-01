package com.gome.manager.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 会议实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class Professor {
	
	
	//会议ID
	private Long id;
	
	//会议编号
	private String code;
	
	//会议名称
	private String name;
	
	//参考价格
	private String unit;
	
	//图片地址
	private String office;
	
	//会议描述
	private String jobs;
	//排序
	private String sortNum;
	
	
	//状态(0:关闭   1:开启)
	private String recode;
	private String picUrl;
	
	private Long professorId;

	private List<Professor> professorResumeList = new ArrayList<Professor>();
	
	
	public String getSortNum() {
		return sortNum;
	}


	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}


	public List<Professor> getProfessorResumeList() {
		return professorResumeList;
	}


	public void setProfessorResumeList(List<Professor> professorResumeList) {
		this.professorResumeList = professorResumeList;
	}


	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}


	public Long getProfessorId() {
		return professorId;
	}


	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
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


	public String getRecode() {
		return recode;
	}


	public void setRecode(String recode) {
		this.recode = recode;
	}
	
}
