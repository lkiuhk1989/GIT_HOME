package com.gome.manager.domain;

import java.util.ArrayList;
import java.util.List;

public class Selecter {
	private int qseq;
	private int oid;
	private int selseq;
	private String content;
	private String remark;
	private String total;
	private String answerTotal;
	private String percent;
	private List<Answer>  answers  = new ArrayList<Answer>();
	
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public String getAnswerTotal() {
		return answerTotal;
	}
	public void setAnswerTotal(String answerTotal) {
		this.answerTotal = answerTotal;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
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
	
	public int getQseq() {
		return qseq;
	}
	public void setQseq(int qseq) {
		this.qseq = qseq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSelseq() {
		return selseq;
	}
	public void setSelseq(int selseq) {
		this.selseq = selseq;
	}
	

}
