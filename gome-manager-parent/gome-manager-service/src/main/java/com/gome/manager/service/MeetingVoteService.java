package com.gome.manager.service;

import java.util.List;

import com.gome.manager.domain.MeetingVote;
import com.gome.manager.domain.MeetingVoteResult;


/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingVoteService {

	List<MeetingVote> queryVote(String id);

	int addVote(MeetingVote meetingVote);

	int delVote(MeetingVote meetingVote);

	List<MeetingVoteResult> queryVoteResult(MeetingVote meetingVote);

	int updateVote(MeetingVote meetingVote);

	String queryVoteStatus(MeetingVote meetingVote);

	String queryPerVoteStatus(MeetingVote meetingVote);

	int clearVote(MeetingVote meetingVote);

}
