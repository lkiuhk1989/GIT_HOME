package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.common.Page;
import com.gome.manager.dao.ReadingProfessorMapper;
import com.gome.manager.domain.ReadingArticleProfessor;
import com.gome.manager.service.ReadingProfessorService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("readingProfessorService")
public class ReadingProfessorServiceImpl implements ReadingProfessorService {

	@Resource
	private ReadingProfessorMapper readingProfessorMapper;
	
	public Page<ReadingArticleProfessor> findReadingProfessorListByPage(Page<ReadingArticleProfessor> page) {
		List<ReadingArticleProfessor> meetingList = readingProfessorMapper.selectReadingProfessorListByPage(page);
		int totalResult = readingProfessorMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<ReadingArticleProfessor>(page.getPageNo(), page.getPageSize(), totalResult, meetingList, page.getConditions());
	}

	@Override
	public int addReadingProfessor(ReadingArticleProfessor professor) {
		return readingProfessorMapper.insertReadingProfessor(professor);
	}

	@Override
	public ReadingArticleProfessor findReadingProfessorById(long id) {
		return readingProfessorMapper.selectReadingProfessorById(id);
	}

	@Override
	public int updateReadingProfessor(ReadingArticleProfessor professor) {
		return readingProfessorMapper.updateReadingProfessor(professor);
	}

	@Override
	public int deleteReadingProfessorById(Long id) {
		return readingProfessorMapper.deleteReadingProfessorById(id);
	}
}
