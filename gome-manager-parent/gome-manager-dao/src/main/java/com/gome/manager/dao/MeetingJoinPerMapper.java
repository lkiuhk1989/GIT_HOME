package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.MeetingJoinPer;


/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingJoinPerMapper {

	List<MeetingJoinPer> queryJoinPer(MeetingJoinPer meetingJoinPerNew);

	void addJoinPer(MeetingJoinPer meetingJoinPerNew);

	void deleteJoinPerById(MeetingJoinPer meetingJoinPer);

	MeetingJoinPer signMeetJionPerson(MeetingJoinPer joinPerNew);

	void updateSignMeetJionPerson(MeetingJoinPer joinPer);

	void updateMeetUnOpenFlag(MeetingJoinPer joinPerNew2);

}
