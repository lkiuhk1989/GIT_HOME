package com.gome.manager.domain;

import java.io.Serializable;

/**
 * 
 * 用户类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391839018287557659L;
	//ID
	private Long id;
	//名称
	private String userName;
	//昵称
	private String nickname;
	//密码
	private String password;
	//医院
	private String hospital;
	//科室
	private String office;
	//手机
	private String phone;
	//邮箱
	private String email;
	//图片
	private String fileUrl;
	//创建日期
	private String createTime;
	//更新日期
	private String updateTime;
	//省份
	private String province;
	//城市
	private String city;
	//积分乐豆
	private String beans;

	public String getBeans() {
		return beans;
	}

	public void setBeans(String beans) {
		this.beans = beans;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

}
