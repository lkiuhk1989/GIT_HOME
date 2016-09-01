package com.gome.manager.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private int oid;
	private int seq;
	private int qtype;
	private String content;
	private String remark;
	
	List<Selecter> selecterList = new ArrayList<Selecter>();
	
	public List<Selecter> getSelecterList() {
		return selecterList;
	}
	public void setSelecterList(List<Selecter> selecterList) {
		this.selecterList = selecterList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getQtype() {
		return qtype;
	}
	public void setQtype(int qtype) {
		this.qtype = qtype;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
}
