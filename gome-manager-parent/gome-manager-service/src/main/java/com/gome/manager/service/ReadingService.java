package com.gome.manager.service;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.ReadingArticleProfessor;
import com.gome.manager.domain.ReadingArticleTheme;

/**
 * 文献导读service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface ReadingService {

	
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
	Page<Reading> findReadingListByPage(Page<Reading> p);

	int addReading(Reading reading);

	Reading findReadingById(long parseLong);

	int updateReading(Reading reading);

	int deleteReadingById(Long readingId);
	/**
	 * 
	 * 导读列表.
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 */
	List<Reading> queryReadingList(Reading reading);

	List<ReadingArticle> queryArticleList(Reading reading);

	ReadingArticle queryArticleDetail(String articleId);

	List<Reading> queryYearReadingList();

	int addTheme(ReadingArticleTheme articleTheme);

	ReadingArticleTheme queryTheme(ReadingArticleTheme articleTheme);

	List<ReadingArticleProfessor> queryArticleProfessorList(String articleId);

	ReadingArticleProfessor queryArticleProfessorDetail(String professorId);

	List<Reading> queryCatNameList(String yearValue);
}
