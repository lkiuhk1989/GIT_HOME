package com.gome.manager.service;

import java.util.List;

import com.gome.manager.domain.MeetingData;


/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingDataService {

	List<MeetingData> queryData(String id);

	int addData(MeetingData meetingData);

	int delData(MeetingData meetingData);

	MeetingData queryDataById(String id);

}
