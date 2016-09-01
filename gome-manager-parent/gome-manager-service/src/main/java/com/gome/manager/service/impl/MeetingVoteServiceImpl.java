package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.dao.MeetingVoteMapper;
import com.gome.manager.domain.MeetingVote;
import com.gome.manager.domain.MeetingVoteResult;
import com.gome.manager.service.MeetingVoteService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingVoteService")
public class MeetingVoteServiceImpl implements MeetingVoteService {

	@Resource
	private MeetingVoteMapper meetingVoteMapper;

	@Override
	public List<MeetingVote> queryVote(String meetingId) {
		MeetingVote meetingVoteNew = new MeetingVote();
		meetingVoteNew.setCode(meetingId);
		return meetingVoteMapper.queryVote(meetingVoteNew);
	}

	@Override
	public int addVote(MeetingVote meetingVote) {
		if(meetingVote.getId()!=null && !"".equals(meetingVote.getId())){
			meetingVoteMapper.updateVote(meetingVote);
		}else{
			meetingVoteMapper.addVote(meetingVote);
		}
		return 1;
	}

	@Override
	public int delVote(MeetingVote meetingVote) {
		meetingVoteMapper.deleteVoteById(meetingVote);
		return 1;
	}

	@Override
	public List<MeetingVoteResult> queryVoteResult(MeetingVote meetingVote) {
		List<MeetingVoteResult> list = meetingVoteMapper.queryVoteResult(meetingVote);
		return list;
	}

	@Override
	public int updateVote(MeetingVote meetingVote) {
		meetingVoteMapper.updateVoteTen(meetingVote);
		return 1;
	}

	@Override
	public String queryVoteStatus(MeetingVote meetingVote) {
		return meetingVoteMapper.queryVoteStatus(meetingVote);
	}

	@Override
	public String queryPerVoteStatus(MeetingVote meetingVote) {
		// TODO Auto-generated method stub
		return meetingVoteMapper.queryPerVoteStatus(meetingVote);
	}

	@Override
	public int clearVote(MeetingVote meetingVote) {
		return meetingVoteMapper.clearVote(meetingVote);
	}
}
