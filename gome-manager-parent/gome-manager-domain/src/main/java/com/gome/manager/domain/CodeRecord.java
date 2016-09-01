package com.gome.manager.domain;

import java.io.Serializable;

/**
 * 
 * 校验码.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class CodeRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2665467582893813746L;
	//ID
	private Long id;
	//手机
	private String phone;
	//邮箱
	private String code;
	//创建日期
	private String createTime;
	//标记
	private String flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
