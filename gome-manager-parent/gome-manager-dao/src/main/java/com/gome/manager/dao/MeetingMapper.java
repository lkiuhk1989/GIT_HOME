package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingBack;

/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingMapper {

	/**
	 *
	 * 分页查询会议列表.
	 *
	 * @param page
	 * 			分页信息（封装了查询条件）
	 * @return
	 * 			会议列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	List<Meeting> selectMeetingListByPage(Page<Meeting> page);
	
	/**
	 *
	 * 根据搜索条件查询总记录数.
	 * @param goods
	 * 			搜索条件
	 * @return
	 * 			总记录数
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	Integer selectTotalResultByConditions(Meeting meeting);

	int insertMeeting(Meeting meeting);

	Meeting selectMeetingById(long id);

	int updateMeeting(Meeting meeting);

	int deleteMeetingById(Long id);

	void updateMeetCode(String code);

	String queryMeetCode();

	Meeting validateMeetCode(String code);

	MeetingBack findMeetingBackById(MeetingBack meetingBack);

	int updateMeetingNew(Meeting meeting);

	void updateMeetingAll();

}
