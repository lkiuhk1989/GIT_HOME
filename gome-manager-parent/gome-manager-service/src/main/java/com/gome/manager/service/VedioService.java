package com.gome.manager.service;

import java.util.List;

import com.gome.manager.common.Page;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.Vedio;

/**
 * 文献导读service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface VedioService {

	
	/**
	 * 
	 * 分页查询列表.
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
	Page<Vedio> findVedioListByPage(Page<Vedio> p);

	int addVedio(Vedio Vedio);

	Vedio findVedioById(long parseLong);

	int updateVedio(Vedio Vedio);

	int deleteVedioById(Long VedioId);

	ReadingArticle queryArticleDetail(String articleId);

	List<ReadingArticle> queryArticleList(Reading reading);

	List<Vedio> querVedioList(Vedio vedio);

	Vedio queryVedioDetail(long vedioId);

}
