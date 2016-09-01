package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.MeetingVote;
import com.gome.manager.domain.MeetingVoteResult;


/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingVoteMapper {

	List<MeetingVote> queryVote(MeetingVote meetingVoteNew);

	void addVote(MeetingVote meetingVoteNew);

	void deleteVoteById(MeetingVote meetingVote);

	void updateVote(MeetingVote meetingVote);

	List<MeetingVoteResult> queryVoteResult(MeetingVote meetingVote);

	void updateVoteTen(MeetingVote meetingVote);

	String queryVoteStatus(MeetingVote meetingVote);

	void addDataTen(MeetingVote meetingVote);

	String queryPerVoteStatus(MeetingVote meetingVote);

	int clearVote(MeetingVote meetingVote);
	
}
