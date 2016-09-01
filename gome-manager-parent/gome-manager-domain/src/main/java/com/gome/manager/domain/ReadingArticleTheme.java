package com.gome.manager.domain;

import java.io.Serializable;

/**
 * 
 * 会议实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class ReadingArticleTheme  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4292195769658121535L;
	//ID
	private Long id;
	private Long rid;
	//图片
	private String digest;
	//名称
	private String purpose;
	private String discover;
	private String way;
	private String result;
	private String conclusion;
	private String others;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getDiscover() {
		return discover;
	}
	public void setDiscover(String discover) {
		this.discover = discover;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	
}
