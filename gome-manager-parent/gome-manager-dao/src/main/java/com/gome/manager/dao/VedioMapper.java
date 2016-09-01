package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.ReadingArticleTheme;
import com.gome.manager.domain.Vedio;

/**
 * 
 * 导读dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface VedioMapper {

	List<Vedio> selectVedioListByPage(Page<Vedio> page);

	int selectTotalResultByConditions(Vedio conditions);

	int insertVedio(Vedio vedio);

	Vedio selectVedioById(long id);

	int updateVedio(Vedio vedio);

	int deleteVedioById(Long id);

	List<Vedio> queryVedioList(Vedio vedio);

	List<ReadingArticle> queryArticleList(Reading reading);

	ReadingArticle queryArticle(String articleId);

	ReadingArticleTheme queryTheme(ReadingArticleTheme articleTheme);

	Vedio queryVedioDetail(long vedioId);
}
