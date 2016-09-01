package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.ReadingReview;
import com.gome.manager.domain.ReadingSupport;

/**
 * 
 * 导读dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface ReadingReviewMapper {
	
	List<ReadingReview> queryReviewList(ReadingReview review);

	void addReview(ReadingReview readingReview);

	void addSupport(ReadingSupport readingSupport);

	int queryIfSupport(ReadingSupport readingSupport);

	int querySupportTotal(ReadingSupport readingSupport);

	String queryFloor(ReadingReview readingReview);
}
