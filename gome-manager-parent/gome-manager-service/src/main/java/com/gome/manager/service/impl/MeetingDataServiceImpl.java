package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.dao.MeetingDataMapper;
import com.gome.manager.domain.MeetingData;
import com.gome.manager.service.MeetingDataService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingDataService")
public class MeetingDataServiceImpl implements MeetingDataService {

	@Resource
	private MeetingDataMapper meetingDataMapper;

	@Override
	public List<MeetingData> queryData(String meetingId) {
		MeetingData meetingDataNew = new MeetingData();
		meetingDataNew.setCode(meetingId);
		return meetingDataMapper.queryData(meetingDataNew);
	}

	@Override
	public int addData(MeetingData meetingData) {
		if(meetingData.getId()!=null && !"".equals(meetingData.getId())){
			meetingDataMapper.updateData(meetingData);
		}else{
			meetingDataMapper.addData(meetingData);
		}
		return 1;
	}

	@Override
	public int delData(MeetingData meetingData) {
		meetingDataMapper.deleteDataById(meetingData);
		return 1;
	}

	@Override
	public MeetingData queryDataById(String id) {
		MeetingData meetingDataNew = new MeetingData();
		meetingDataNew.setId(Long.parseLong(id));
		return meetingDataMapper.queryDataById(meetingDataNew);
	}

}
