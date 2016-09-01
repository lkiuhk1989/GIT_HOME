package com.gome.manager.dao;

import java.util.List;

import com.gome.manager.domain.Answer;
import com.gome.manager.domain.ObjectBean;
import com.gome.manager.domain.Question;
import com.gome.manager.domain.Replay;
import com.gome.manager.domain.Selecter;


/**
 * 
 * 会议dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
public interface MeetingSelectMapper {

	ObjectBean queryObjectBean(ObjectBean objectBean);

	List<Question> queryQuestion(Question question);

	List<Selecter> querySelecter(Selecter selecter);

	void addAnswer(Answer answer);

	String queryPerSelectResult(Answer answer);

	Object addReplay(Replay replay);

	String queryAllAnswer(Answer answer);

	String querySelecterAnswer(Answer answer);

	List<Answer> queryAnswerContent(Answer answer);
	
}
