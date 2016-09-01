package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.ReadingArticleProfessor;

/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface ReadingProfessorMapper {

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
	List<ReadingArticleProfessor> selectReadingProfessorListByPage(Page<ReadingArticleProfessor> page);
	
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
	Integer selectTotalResultByConditions(ReadingArticleProfessor professor);

	int insertReadingProfessor(ReadingArticleProfessor professor);

	ReadingArticleProfessor selectReadingProfessorById(long id);

	int updateReadingProfessor(ReadingArticleProfessor professor);

	int deleteReadingProfessorById(Long id);

	List<ReadingArticleProfessor> findReadingProfessorResume(Long professorId);

	void addReadingProfessorRecode(ReadingArticleProfessor professor);

	void delResume(Long id);

	void updateRecode(ReadingArticleProfessor professor);

	List<ReadingArticleProfessor> findReadingProfessorByMeetId(long id);
}
