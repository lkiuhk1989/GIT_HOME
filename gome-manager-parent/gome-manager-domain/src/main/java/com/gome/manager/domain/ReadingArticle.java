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
public class ReadingArticle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2934175346665574931L;

	//ID
	private Long id;
	
	private Long catid;
	
	//图片
	private String title;
	//发表日期
	private String createTime;
	//发表日期
	private String fileUrl;
	//发表日期
	private String author;
	private String typeName;
	
	ReadingArticleTheme articleTheme = new ReadingArticleTheme();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCatid() {
		return catid;
	}
	public void setCatid(Long catid) {
		this.catid = catid;
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
	public ReadingArticleTheme getArticleTheme() {
		return articleTheme;
	}
	public void setArticleTheme(ReadingArticleTheme articleTheme) {
		this.articleTheme = articleTheme;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
