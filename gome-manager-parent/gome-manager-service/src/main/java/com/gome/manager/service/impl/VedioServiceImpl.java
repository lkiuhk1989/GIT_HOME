package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.common.Page;
import com.gome.manager.dao.VedioMapper;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.ReadingArticleTheme;
import com.gome.manager.domain.Vedio;
import com.gome.manager.service.VedioService;

/**
 * 
 * 导读service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("vedioService")
public class VedioServiceImpl implements VedioService {
	@Resource
	VedioMapper vedioMapper;
	
	public Page<Vedio> findVedioListByPage(Page<Vedio> page) {
		List<Vedio> VedioList = vedioMapper.selectVedioListByPage(page);
		int totalResult = vedioMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Vedio>(page.getPageNo(), page.getPageSize(), totalResult, VedioList, page.getConditions());
	}

	@Override
	public int addVedio(Vedio Vedio) {
		int id = vedioMapper.insertVedio(Vedio);
		return id;
	}

	@Override
	public Vedio findVedioById(long id) {
		return vedioMapper.selectVedioById(id);
	}

	@Override
	public int updateVedio(Vedio Vedio) {
		return vedioMapper.updateVedio(Vedio);
	}

	@Override
	public int deleteVedioById(Long id) {
		return vedioMapper.deleteVedioById(id);
	}
	
	@Override
	public List<ReadingArticle> queryArticleList(Reading reading) {
		// TODO Auto-generated method stub
		List<ReadingArticle> list = vedioMapper.queryArticleList(reading);
		return list;
	}
	@Override
	public ReadingArticle queryArticleDetail(String articleId) {
		// TODO Auto-generated method stub
		ReadingArticle readingArticle = vedioMapper.queryArticle(articleId);
		ReadingArticleTheme articleTheme = new ReadingArticleTheme();
		articleTheme.setRid(Long.parseLong(articleId));
		articleTheme = vedioMapper.queryTheme(articleTheme);
		readingArticle.setArticleTheme(articleTheme);
		return readingArticle;
	}

	@Override
	public List<Vedio> querVedioList(Vedio vedio) {
		// TODO Auto-generated method stub
		return vedioMapper.queryVedioList(vedio);
	}

	@Override
	public Vedio queryVedioDetail(long vedioId) {
		// TODO Auto-generated method stub
		return vedioMapper.queryVedioDetail(vedioId);
	}

}
