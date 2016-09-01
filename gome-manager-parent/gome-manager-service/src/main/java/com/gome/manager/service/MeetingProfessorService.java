package com.gome.manager.service;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Professor;

/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingProfessorService {


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
	Page<Professor> findMeetingProfessorListByPage(Page<Professor> p);

	int addMeetingProfessor(Professor professor);

	Professor findMeetingProfessorById(long parseLong);

	int updateMeetingProfessor(Professor professor);

	int deleteMeetingProfessorById(Long meetingId);

	List<Professor> findMeetingProfessorResume(long parseLong);

	int addMeetingProfessorRecode(Professor professor);

	int delResume(Long meetingProfessorId);

	List<Professor> findMeetingProfessorByMeetId(long parseLong);

}
