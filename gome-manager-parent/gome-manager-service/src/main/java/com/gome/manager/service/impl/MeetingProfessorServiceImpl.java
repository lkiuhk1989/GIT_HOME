package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.common.Page;
import com.gome.manager.dao.MeetingProfessorMapper;
import com.gome.manager.domain.Professor;
import com.gome.manager.service.MeetingProfessorService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingProfessorService")
public class MeetingProfessorServiceImpl implements MeetingProfessorService {

	@Resource
	private MeetingProfessorMapper meetingProfessorMapper;
	
	public Page<Professor> findMeetingProfessorListByPage(Page<Professor> page) {
		List<Professor> meetingList = meetingProfessorMapper.selectMeetingProfessorListByPage(page);
		int totalResult = meetingProfessorMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Professor>(page.getPageNo(), page.getPageSize(), totalResult, meetingList, page.getConditions());
	}

	@Override
	public int addMeetingProfessor(Professor professor) {
		return meetingProfessorMapper.insertMeetingProfessor(professor);
	}

	@Override
	public Professor findMeetingProfessorById(long id) {
		return meetingProfessorMapper.selectMeetingProfessorById(id);
	}

	@Override
	public int updateMeetingProfessor(Professor professor) {
		return meetingProfessorMapper.updateMeetingProfessor(professor);
	}

	@Override
	public int deleteMeetingProfessorById(Long id) {
		return meetingProfessorMapper.deleteMeetingProfessorById(id);
	}

	@Override
	public List<Professor> findMeetingProfessorResume(long professorId) {
		return meetingProfessorMapper.findMeetingProfessorResume(professorId);
	}

	@Override
	public int addMeetingProfessorRecode(Professor professor) {
		if(professor.getId()!=null){
			meetingProfessorMapper.updateRecode(professor);
		}else
			meetingProfessorMapper.addMeetingProfessorRecode(professor);
		return 1;
	}

	@Override
	public int delResume(Long meetingProfessorId) {
		meetingProfessorMapper.delResume(meetingProfessorId);
		return 1;
	}

	@Override
	public List<Professor> findMeetingProfessorByMeetId(long id) {
		return meetingProfessorMapper.findMeetingProfessorByMeetId(id);
	}
}
