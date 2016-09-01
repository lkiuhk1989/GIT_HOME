package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.dao.MeetingScheduleMapper;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingSchedule;
import com.gome.manager.service.MeetingScheduleService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingScheduleService")
public class MeetingScheduleServiceImpl implements MeetingScheduleService {

	@Resource
	private MeetingScheduleMapper meetingScheduleMapper;

	@Override
	public List<MeetingSchedule> querySchedule(String meetingId) {
		MeetingSchedule meetingScheduleNew = new MeetingSchedule();
		meetingScheduleNew.setCode(meetingId);
		return meetingScheduleMapper.querySchedule(meetingScheduleNew);
	}

	@Override
	public int addSchedule(MeetingSchedule meetingSchedule) {
		MeetingSchedule meetingSchedule1 = meetingScheduleMapper.queryStage(meetingSchedule);
		meetingSchedule.setStage(meetingSchedule1.getStage());
		if(meetingSchedule.getId()!=null && !"".equals(meetingSchedule.getId())){
			meetingScheduleMapper.updateSchedule(meetingSchedule);
		}else{
			meetingScheduleMapper.addSchedule(meetingSchedule);
		}
		return 1;
	}

	@Override
	public int delSchedule(MeetingSchedule meetingSchedule) {
		meetingScheduleMapper.deleteScheduleById(meetingSchedule);
		return 1;
	}

	@Override
	public List<MeetingSchedule> queryScheduleStage(Meeting meeting) {
		List<MeetingSchedule> list = meetingScheduleMapper.queryScheduleStage(meeting);
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setNum(String.valueOf(i+1));
			}
		}
		return list;
	}

	@Override
	public List<MeetingSchedule> queryScheduleById(String meetingId) {
		MeetingSchedule meetingScheduleNew = new MeetingSchedule();
		meetingScheduleNew.setCode(meetingId);
		List<MeetingSchedule> listType = meetingScheduleMapper.queryScheduleStageType(meetingScheduleNew);
		if(listType!=null && listType.size()>0){
			for (int i = 0; i < listType.size(); i++) {
				meetingScheduleNew.setStageId(listType.get(i).getStageId());
				List<MeetingSchedule> list = meetingScheduleMapper.querySchedule(meetingScheduleNew);
				if(list!=null && list.size()>1){
					list.remove(0);
				}
				listType.get(i).setScheduleList(list);
			}
		}
		return listType;
	}

	@Override
	public MeetingSchedule queryScheduleByMeetId(String meetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addScheduleStage(MeetingSchedule meetingSchedule) {
		if(meetingSchedule.getStageId()!=null){
			meetingScheduleMapper.updateScheduleStage(meetingSchedule);
		}else{
			meetingScheduleMapper.addScheduleStage(meetingSchedule);
		}
		return 1;
	}

	@Override
	public int delStage(MeetingSchedule meetingSchedule) {
		return meetingScheduleMapper.delStage(meetingSchedule);
	}
	
}
