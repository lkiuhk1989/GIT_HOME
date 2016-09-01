package com.gome.manager.service;

import com.gome.manager.common.Page;
import com.gome.manager.domain.ReadingArticleProfessor;

/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface ReadingProfessorService {


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
	Page<ReadingArticleProfessor> findReadingProfessorListByPage(Page<ReadingArticleProfessor> p);

	int addReadingProfessor(ReadingArticleProfessor professor);

	ReadingArticleProfessor findReadingProfessorById(long parseLong);

	int updateReadingProfessor(ReadingArticleProfessor professor);

	int deleteReadingProfessorById(Long meetingId);
}
