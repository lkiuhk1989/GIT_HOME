package com.gome.manager.domain;


/**
 * 
 * 会议投票结果实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class MeetingVoteResult {
	//ID
	private Long id;
	//名称
	private String num;
	
	//签到时间
	private String auswer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAuswer() {
		return auswer;
	}

	public void setAuswer(String auswer) {
		this.auswer = auswer;
	}

}
