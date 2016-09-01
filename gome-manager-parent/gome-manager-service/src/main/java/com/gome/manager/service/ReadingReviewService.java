package com.gome.manager.service;

import java.util.List;

import com.gome.manager.domain.ReadingReview;
import com.gome.manager.domain.ReadingSupport;

/**
 * 文献导读service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface ReadingReviewService {

	List<ReadingReview> queryReviewList(ReadingReview review);

	int addReview(ReadingReview readingReview);

	int addSupport(ReadingSupport readingSupport);

	int queryIfSupport(ReadingSupport readingSupport);

	int querySupportTotal(ReadingSupport readingSupport);
}
