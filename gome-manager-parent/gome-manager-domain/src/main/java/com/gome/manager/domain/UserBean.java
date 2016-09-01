package com.gome.manager.domain;

import java.io.Serializable;

/**
 * 
 * 用户乐豆.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -676688296900906980L;
	//ID
	private Long id;
	//用户id
	private Long userId;
	//建议
	private String userName;
	//来源id
	private Long sourceId;
	//数量
	private int amount;
	//来源1：点赞、2：评论、3：完善资料、4：签到、5：新注册、6：微互动、7：下载文献、8：下载指南、9：重复下载、10：观看视频
	private String sourceType;
	//获取时间
	private String getTime;
	//签到时间
	private String signTime;
	//获取说明
	private String describes;
	//建议
	private String advice;
	/**
	 * 乐豆类型
	 * 0、获取1、消费
	 */
	private String typeB;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getTypeB() {
		return typeB;
	}
	public void setTypeB(String typeB) {
		this.typeB = typeB;
	}
	
}
