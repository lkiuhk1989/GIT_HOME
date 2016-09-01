package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.ReadingArticleProfessor;
import com.gome.manager.domain.ReadingArticleTheme;

/**
 * 
 * 导读dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface ReadingMapper {
	
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
	List<Reading> selectReadingListByPage(Page<Reading> page);
	
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
	Integer selectTotalResultByConditions(Reading reading);

	int insertReading(Reading reading);

	Reading selectReadingById(long id);

	int updateReading(Reading reading);

	int deleteReadingById(Long id);
	
	List<Reading> queryReadingList(Reading reading);

	List<ReadingArticle> queryArticleList(Reading reading);

	ReadingArticleTheme queryArticleDetail(String articleId);

	List<Reading> queryYearReadingList();

	ReadingArticle queryArticle(String articleId);

	ReadingArticleTheme queryTheme(ReadingArticleTheme articleTheme);

	void addTheme(ReadingArticleTheme articleTheme);

	void updateTheme(ReadingArticleTheme articleTheme);

	List<ReadingArticleProfessor> queryArticleProfessorList(String articleId);

	ReadingArticleProfessor queryArticleProfessorDetail(String professorId);

	List<Reading> queryCatNameList(String yearValue);
}
