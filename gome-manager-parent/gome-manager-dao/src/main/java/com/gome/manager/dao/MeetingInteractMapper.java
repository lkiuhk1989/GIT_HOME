package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.MeetingInteract;


/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingInteractMapper {

	List<MeetingInteract> queryInteract(MeetingInteract meetingInteractNew);

	void addInteract(MeetingInteract meetingInteractNew);

	void deleteInteractById(MeetingInteract meetingInteract);

	void updateInteract(MeetingInteract meetingInteract);

	List<MeetingInteract> queryOnWall(MeetingInteract meetingInteract);

	String queryMaxInteractId(MeetingInteract meetingInteract);

	String queryFloor(MeetingInteract meetingInteract);

	int deleteWall(MeetingInteract meetingInteract);

}
