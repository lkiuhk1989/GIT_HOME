package com.gome.manager.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ObjectBean {
    private int oid;
    private String title;
    private String discribe;
    private Timestamp createTime;
    private String remark;
    private int state;
    private String anonymousFlag;
    private String meetingId;
    
    List<Question> questionList = new ArrayList<Question>();
    
    public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public ObjectBean() {
    }

    public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public void setTitle(String title) {
        this.title = title;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscribe() {
        return discribe;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAnonymousFlag() {
		return anonymousFlag;
	}

	public void setAnonymousFlag(String anonymousFlag) {
		this.anonymousFlag = anonymousFlag;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

   
}
