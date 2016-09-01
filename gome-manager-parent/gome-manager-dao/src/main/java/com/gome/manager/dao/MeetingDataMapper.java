package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.MeetingData;


/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingDataMapper {

	List<MeetingData> queryData(MeetingData meetingDataNew);

	void addData(MeetingData meetingDataNew);

	void deleteDataById(MeetingData meetingData);

	void updateData(MeetingData meetingData);

	MeetingData queryDataById(MeetingData meetingDataNew);
	
}
