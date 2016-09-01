package com.gome.manager.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 会议投票实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class MeetingVote {
	
	
	//ID
	private Long id;
	//会议编号
	private String code;
	
	//投票人
	private String perId;
	
	//题目编号
	private String questionCode;
	
	//题目编号
	private String status;
	
	//选择答案
	private String auswer;
	//选择答案
	private String wide;
	//选择答案
	private String height;
	
	public String getWide() {
		return wide;
	}

	public void setWide(String wide) {
		this.wide = wide;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	private List<MeetingVoteResult> voteResultList = new ArrayList<MeetingVoteResult>();
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MeetingVoteResult> getVoteResultList() {
		return voteResultList;
	}

	public void setVoteResultList(List<MeetingVoteResult> voteResultList) {
		this.voteResultList = voteResultList;
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

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getAuswer() {
		return auswer;
	}

	public void setAuswer(String auswer) {
		this.auswer = auswer;
	}
	
}
