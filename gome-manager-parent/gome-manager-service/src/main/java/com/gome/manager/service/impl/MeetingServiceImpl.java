package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.common.Page;
import com.gome.manager.dao.MeetingMapper;
import com.gome.manager.dao.MeetingVoteMapper;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingBack;
import com.gome.manager.domain.MeetingVote;
import com.gome.manager.service.MeetingService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

	@Resource
	private MeetingMapper meetingMapper;
	@Resource
	private MeetingVoteMapper meetingVoteMapper;
	
	public Page<Meeting> findMeetingListByPage(Page<Meeting> page) {
		List<Meeting> meetingList = meetingMapper.selectMeetingListByPage(page);
		int totalResult = meetingMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Meeting>(page.getPageNo(), page.getPageSize(), totalResult, meetingList, page.getConditions());
	}

	@Override
	public int addMeeting(Meeting meeting) {
		this.meetingMapper.updateMeetingAll();
		int id = meetingMapper.insertMeeting(meeting);
		MeetingVote meetingVote = new MeetingVote();
		meetingVote.setCode(String.valueOf(meeting.getId()));
		meetingVote.setStatus("0");
		meetingVote.setQuestionCode("1");
		meetingVoteMapper.addDataTen(meetingVote);
		return id;
	}

	@Override
	public Meeting findMeetingById(long id) {
		return meetingMapper.selectMeetingById(id);
	}

	@Override
	public int updateMeeting(Meeting meeting) {
		return meetingMapper.updateMeeting(meeting);
	}

	@Override
	public int deleteMeetingById(Long id) {
		return meetingMapper.deleteMeetingById(id);
	}

	@Override
	public String queryMeetCode() {
		String code = meetingMapper.queryMeetCode();
		return code;
	}

	@Override
	public void updateMeetCode(String code) {
		int codeN = Integer.parseInt(code)+1;
		meetingMapper.updateMeetCode(String.valueOf(codeN));
	}

	@Override
	public Meeting validateMeetCode(String code) {
		return meetingMapper.validateMeetCode(code);
	}

	@Override
	public MeetingBack findMeetingBackById(String id) {
		MeetingBack meetingBack = new MeetingBack();
		meetingBack.setId(Long.parseLong(id));
		return meetingMapper.findMeetingBackById(meetingBack);
	}

	@Override
	public int updateMeetingNew(Meeting meeting) {
		return meetingMapper.updateMeetingNew(meeting);
	}


}
