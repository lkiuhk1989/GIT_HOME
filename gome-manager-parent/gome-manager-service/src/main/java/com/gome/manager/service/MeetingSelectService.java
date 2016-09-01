package com.gome.manager.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.gome.manager.domain.Answer;
import com.gome.manager.domain.ObjectBean;
import com.gome.manager.domain.Question;
import com.gome.manager.domain.Replay;


/**
 * 会议service接口.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingSelectService {

	ObjectBean queryObjectBean(String meetId);

	List<Question> queryQuestion(Question question);

	int addQuestionSelection(List<Answer> list);

	String queryPerSelectResult(Answer answer);

	void addReplay(Replay replay);

	ObjectBean querySelectResult(String meetId);

	HSSFWorkbook exportExcel(ObjectBean objectBean);

}
