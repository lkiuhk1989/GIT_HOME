package com.gome.manager.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gome.manager.domain.MeetingInteract;


/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingInteractService {

	List<MeetingInteract> queryInteract(String id);

	int addInteract(MeetingInteract meetingInteract);

	int updateInteract(MeetingInteract meetingInteract);

	List<MeetingInteract> queryOnWall(MeetingInteract meetingInteract);

	List<MeetingInteract> queryOwnHistory(MeetingInteract meetingInteract);

	int deleteWall(MeetingInteract meetingInteract);

	HSSFWorkbook exportExcel(List<MeetingInteract> interactList);

}
