package com.gome.manager.service;

import java.util.List;

import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingSchedule;


/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingScheduleService {

	List<MeetingSchedule> querySchedule(String id);

	int addSchedule(MeetingSchedule meetingSchedule);

	int delSchedule(MeetingSchedule meetingSchedule);

	List<MeetingSchedule> queryScheduleStage(Meeting meeting);

	MeetingSchedule queryScheduleByMeetId(String meetId);

	List<MeetingSchedule> queryScheduleById(String meetId);

	int addScheduleStage(MeetingSchedule meetingSchedule);

	int delStage(MeetingSchedule meetingSchedule);

}
