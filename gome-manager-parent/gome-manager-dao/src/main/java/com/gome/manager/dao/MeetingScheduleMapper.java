package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingSchedule;


/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingScheduleMapper {

	List<MeetingSchedule> querySchedule(MeetingSchedule meetingScheduleNew);

	void addSchedule(MeetingSchedule meetingScheduleNew);

	void deleteScheduleById(MeetingSchedule meetingSchedule);

	List<MeetingSchedule> queryScheduleStage(Meeting meeting);

	MeetingSchedule queryStage(MeetingSchedule meetingSchedule);

	void updateSchedule(MeetingSchedule meetingSchedule);

	List<MeetingSchedule> queryScheduleStageType(MeetingSchedule meetingScheduleNew);

	void addScheduleStage(MeetingSchedule meetingSchedule);

	void updateScheduleStage(MeetingSchedule meetingSchedule);

	int delStage(MeetingSchedule meetingSchedule);
	
}
