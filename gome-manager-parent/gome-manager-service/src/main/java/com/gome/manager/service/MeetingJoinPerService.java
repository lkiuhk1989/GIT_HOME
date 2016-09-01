package com.gome.manager.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gome.manager.domain.MeetingJoinPer;


/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingJoinPerService {

	List<MeetingJoinPer> queryJoinPer(String meetingId);

	int addJoinPer(MeetingJoinPer meetingJoinPer);

	int delJoinPer(MeetingJoinPer meetingJoinPer);

	MeetingJoinPer signMeetJionPerson(MeetingJoinPer joinPerNew);

	void updateSignMeetJionPerson(MeetingJoinPer joinPer);

	void updateMeetUnOpenFlag(MeetingJoinPer joinPerNew2);

	List<MeetingJoinPer> queryJoinPer(MeetingJoinPer joinPer);

	HSSFWorkbook exportExcel(List<MeetingJoinPer> meetingJoinPerList);

}
