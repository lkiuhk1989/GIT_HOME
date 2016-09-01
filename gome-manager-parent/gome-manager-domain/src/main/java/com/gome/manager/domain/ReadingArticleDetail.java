package com.gome.manager.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 会议实体类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月6日    caowei    新建
 * </pre>
 */
public class ReadingArticleDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2451401149955726201L;
	//ID
	private Long id;
	//图片
	private String title;
	//发表日期
	private String createTime;
	//名称
	private String content;
	
	//图片
	private String filepath;
	
	private String filedownnum;
	
	List<ReadingArticleTheme> themeList  = new ArrayList<ReadingArticleTheme>();
	
	List<ReadingArticleProfessor> professorLst  = new ArrayList<ReadingArticleProfessor>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFiledownnum() {
		return filedownnum;
	}
	public void setFiledownnum(String filedownnum) {
		this.filedownnum = filedownnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<ReadingArticleTheme> getThemeList() {
		return themeList;
	}
	public void setThemeList(List<ReadingArticleTheme> themeList) {
		this.themeList = themeList;
	}
	public List<ReadingArticleProfessor> getProfessorLst() {
		return professorLst;
	}
	public void setProfessorLst(List<ReadingArticleProfessor> professorLst) {
		this.professorLst = professorLst;
	}
	
}
