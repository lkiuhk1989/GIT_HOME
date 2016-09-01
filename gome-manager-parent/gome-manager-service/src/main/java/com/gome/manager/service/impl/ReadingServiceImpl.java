package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.common.Page;
import com.gome.manager.dao.ReadingMapper;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.ReadingArticleProfessor;
import com.gome.manager.domain.ReadingArticleTheme;
import com.gome.manager.service.ReadingService;

/**
 * 
 * 导读service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("readingService")
public class ReadingServiceImpl implements ReadingService {
	@Resource
	ReadingMapper readingMapper;
	
	public Page<Reading> findReadingListByPage(Page<Reading> page) {
		List<Reading> readingList = readingMapper.selectReadingListByPage(page);
		int totalResult = readingMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Reading>(page.getPageNo(), page.getPageSize(), totalResult, readingList, page.getConditions());
	}

	@Override
	public int addReading(Reading reading) {
		int id = readingMapper.insertReading(reading);
		return id;
	}

	@Override
	public Reading findReadingById(long id) {
		return readingMapper.selectReadingById(id);
	}

	@Override
	public int updateReading(Reading reading) {
		return readingMapper.updateReading(reading);
	}

	@Override
	public int deleteReadingById(Long id) {
		return readingMapper.deleteReadingById(id);
	}
	
	@Override
	public List<Reading> queryReadingList(Reading reading) {
		// TODO Auto-generated method stub
		List<Reading> list = readingMapper.queryReadingList(reading);
		return list;
	}
	@Override
	public List<ReadingArticle> queryArticleList(Reading reading) {
		// TODO Auto-generated method stub
		List<ReadingArticle> list = readingMapper.queryArticleList(reading);
		return list;
	}
	@Override
	public ReadingArticle queryArticleDetail(String articleId) {
		// TODO Auto-generated method stub
		ReadingArticle readingArticle = readingMapper.queryArticle(articleId);
		ReadingArticleTheme articleTheme = new ReadingArticleTheme();
		articleTheme.setRid(Long.parseLong(articleId));
		articleTheme = readingMapper.queryTheme(articleTheme);
		readingArticle.setArticleTheme(articleTheme);
		return readingArticle;
	}
	@Override
	public List<Reading> queryYearReadingList() {
		List<Reading> list = readingMapper.queryYearReadingList();
		return list;
	}

	@Override
	public int addTheme(ReadingArticleTheme articleTheme) {
		ReadingArticleTheme articleTheme1 = readingMapper.queryTheme(articleTheme);
		if(articleTheme1!=null){
			readingMapper.updateTheme(articleTheme);
		}else{
			readingMapper.addTheme(articleTheme);
		}
		return 1;
	}

	@Override
	public ReadingArticleTheme queryTheme(ReadingArticleTheme articleTheme) {
		// TODO Auto-generated method stub
		return readingMapper.queryTheme(articleTheme);
	}

	@Override
	public List<ReadingArticleProfessor> queryArticleProfessorList(String articleId) {
		// TODO Auto-generated method stub
		return readingMapper.queryArticleProfessorList(articleId);
	}

	@Override
	public ReadingArticleProfessor queryArticleProfessorDetail(String professorId) {
		// TODO Auto-generated method stub
		ReadingArticleProfessor queryArticleProfessorDetail = readingMapper.queryArticleProfessorDetail(professorId);
		String content = queryArticleProfessorDetail.getContent();
		StringBuffer sb = new StringBuffer();
		if(content!=null && content!= ""){
			String[] split = content.split("@@@");
			for (int i = 0; i < split.length; i++) {
				sb.append("<p>"+split[i]+"</p>");
			}
		}
		queryArticleProfessorDetail.setContent(sb.toString());
		return queryArticleProfessorDetail;
	}

	@Override
	public List<Reading> queryCatNameList(String yearValue) {
		// TODO Auto-generated method stub
		return readingMapper.queryCatNameList(yearValue);
	}

}
