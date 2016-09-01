package com.gome.manager.service;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingBack;

/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingService {


	/**
	 * 
	 * 分页查询会议列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			分页数据
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Page<Meeting> findMeetingListByPage(Page<Meeting> p);

	int addMeeting(Meeting meeting);

	Meeting findMeetingById(long parseLong);

	int updateMeeting(Meeting meeting);

	int deleteMeetingById(Long meetingId);

	String queryMeetCode();

	void updateMeetCode(String code);

	Meeting validateMeetCode(String code);

	MeetingBack findMeetingBackById(String code);

	int updateMeetingNew(Meeting meeting);
	
}
