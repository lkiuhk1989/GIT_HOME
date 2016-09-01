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
public class ReadingReview implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7673485698680494711L;
	/**
	 * 
	 */

	//ID
	private Long id;
	private Long yid;
	
	//名称
	private String perId;
	//用户头像
	private String perName;
	//用户头像
	private String perPic;
	
	//期数
	private String content;
	//主题
	private String numfloor;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNumfloor() {
		return numfloor;
	}

	public void setNumfloor(String numfloor) {
		this.numfloor = numfloor;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerPic() {
		return perPic;
	}

	public void setPerPic(String perPic) {
		this.perPic = perPic;
	}

	public String getTypeV() {
		return typeV;
	}

	public void setTypeV(String typeV) {
		this.typeV = typeV;
	}
	
}
