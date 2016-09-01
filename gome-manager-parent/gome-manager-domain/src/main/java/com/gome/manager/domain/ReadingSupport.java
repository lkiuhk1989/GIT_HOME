package com.gome.manager.domain;

import java.io.Serializable;

/**
 * 
 * 评论实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class ReadingSupport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3576016565013678112L;
	//ID
	private Long id;
	private Long yid;
	
	//名称
	private String perId;
	
	//发表日期
	private String createTime;
	private String typeV;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getYid() {
		return yid;
	}

	public void setYid(Long yid) {
		this.yid = yid;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getTypeV() {
		return typeV;
	}

	public void setTypeV(String typeV) {
		this.typeV = typeV;
	}

}
