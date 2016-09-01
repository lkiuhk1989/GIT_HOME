package com.gome.manager.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.stereotype.Service;

import com.gome.manager.dao.MeetingSelectMapper;
import com.gome.manager.domain.Answer;
import com.gome.manager.domain.ObjectBean;
import com.gome.manager.domain.Question;
import com.gome.manager.domain.Replay;
import com.gome.manager.domain.Selecter;
import com.gome.manager.service.MeetingSelectService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingSelectService")
public class MeetingSelectServiceImpl implements MeetingSelectService {

	@Resource
	private MeetingSelectMapper meetingSelectMapper;

	@Override
	public ObjectBean queryObjectBean(String meetId) {
		ObjectBean objectBean = new ObjectBean();
		objectBean.setMeetingId(meetId);
		objectBean = meetingSelectMapper.queryObjectBean(objectBean);
		return objectBean;
	}

	@Override
	public List<Question> queryQuestion(Question question) {
		List<Question> questions = meetingSelectMapper.queryQuestion(question);
		if(questions!=null && questions.size()>0){
			for (int i = 0; i < questions.size(); i++) {
				int seq = questions.get(i).getSeq();
				Selecter selecter = new Selecter();
				selecter.setOid(question.getOid());
				selecter.setQseq(seq);
				List<Selecter> selecterList = meetingSelectMapper.querySelecter(selecter);
				questions.get(i).setSelecterList(selecterList);
			}
		}
		return questions;
	}

	@Override
	public int addQuestionSelection(List<Answer> list) {
		for (int i = 0; i < list.size(); i++) {
			Answer answer = list.get(i);
			answer.setqSeq(Integer.parseInt(answer.getRemark()));
			meetingSelectMapper.addAnswer(answer);
		}
		return 1;
	}

	@Override
	public String queryPerSelectResult(Answer answer) {
		return meetingSelectMapper.queryPerSelectResult(answer);
	}

	@Override
	public void addReplay(Replay replay) {
		meetingSelectMapper.addReplay(replay);
	}

	@Override
	public ObjectBean querySelectResult(String meetId) {
		// TODO Auto-generated method stub
		ObjectBean objectBean = new ObjectBean();
		objectBean.setMeetingId(meetId);
		objectBean = meetingSelectMapper.queryObjectBean(objectBean);
		Question question = new Question();
		question.setOid(objectBean.getOid());
		List<Question> questions = meetingSelectMapper.queryQuestion(question);
		if(questions!=null && questions.size()>0){
			for (int i = 0; i < questions.size(); i++) {
				int seq = questions.get(i).getSeq();
				Selecter selecter = new Selecter();
				selecter.setOid(question.getOid());
				selecter.setQseq(seq);
				List<Selecter> selecterList = meetingSelectMapper.querySelecter(selecter);
				for (int j = 0; j < selecterList.size(); j++) {
					Selecter selecter2 = selecterList.get(j);
					Answer answer =  new Answer();
					answer.setOid(objectBean.getOid());
					answer.setqSeq(selecter2.getQseq());
					answer.setSeSeq(selecter2.getSelseq());
					String allAnswer = meetingSelectMapper.queryAllAnswer(answer);
					selecter2.setTotal(allAnswer);
					String selecterAnswer = meetingSelectMapper.querySelecterAnswer(answer);
					selecter2.setAnswerTotal(selecterAnswer);
					DecimalFormat   df = new DecimalFormat("#0.00");
					float f = (float) ((1.0 * 100* Integer.parseInt(selecterAnswer))/ Integer.parseInt(allAnswer));
					selecter2.setPercent(String.valueOf(df.format(f))+"%");
					if(questions.get(i).getQtype()==3 && questions.get(i).getSeq()==selecter2.getQseq()){
						Question question2 = questions.get(i);
						answer.setOid(objectBean.getOid());
						answer.setqSeq(question2.getSeq());
						List<Answer>  answers = meetingSelectMapper.queryAnswerContent(answer);
						selecter2.setAnswers(answers);
					}
				}
				questions.get(i).setSelecterList(selecterList);
			}
		}
		objectBean.setQuestionList(questions);
		return objectBean;
	}

	@Override
	public HSSFWorkbook exportExcel(ObjectBean objectBean) {
		HSSFWorkbook wb = new HSSFWorkbook(); 
		String[] excelHeader = { "序号", "题目内容", "总数","选择人数","百分比"};  
		if(objectBean!=null){
			List<Question> questionList = objectBean.getQuestionList();
			if(questionList!=null && questionList.size()>0){
				for (int i = 0; i < questionList.size(); i++) {
						 // 设置字体
				        HSSFFont font = wb.createFont();
				        font.setFontHeightInPoints((short) 13); //字体高度
				        font.setColor(HSSFFont.COLOR_RED); //字体颜色
				        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //宽度
				        font.setItalic(false); //是否使用斜体
				        HSSFCellStyle style = wb.createCellStyle();    
				        
				        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
						style.setWrapText(true);    
						style.setFillBackgroundColor((short)2);
						
						HSSFSheet sheet = wb.createSheet("题目"+(i+1));   
						
						 //合並單元格,下標從0開始
					    sheet.addMergedRegion(new Region(0,(short)0,0,(short)4));
					    sheet.setColumnWidth(1, questionList.get(i).getContent().length() * 256);
					    HSSFRow rowT = sheet.createRow(0);
					    rowT.setHeight((short)1000);
					    HSSFCell cellT = rowT.createCell((short)0);
					    cellT.setCellValue(questionList.get(i).getContent());
					    style.setFont(font);
					    cellT.setCellStyle(style);
					    
					    HSSFRow row1 = sheet.createRow((int) 1); 
						if(questionList.get(i).getQtype()!=3){
							for (int j = 0; j < excelHeader.length; j++) {    
								HSSFCell cell = row1.createCell(j);    
								cell.setCellValue(excelHeader[j]);    
								sheet.autoSizeColumn(j);    
							}   
						}
				        List<Selecter> selecterList = questionList.get(i).getSelecterList();
				        
				        for (int m = 0; m < selecterList.size();m++) {
				        	if(questionList.get(i).getQtype()==3 && selecterList.get(m).getQseq()==questionList.get(i).getSeq()){
				        		HSSFRow row = sheet.createRow(m + 1); 
				        		String[] excelHeader1 = { "序号", "姓名", "内容"};  
				        		for (int j = 0; j < excelHeader1.length; j++) {    
									HSSFCell cell = row.createCell(j);    
									cell.setCellValue(excelHeader1[j]);    
									sheet.autoSizeColumn(j);    
								}   
				        		List<Answer> answers = selecterList.get(m).getAnswers();
				        		for(int t=0 ; t<answers.size();t++){
				        			row = sheet.createRow(t + 2); 
				        			row.createCell(0).setCellValue(t+1);    
									row.createCell(1).setCellValue(answers.get(t).getAnswerName());    
									row.createCell(2).setCellValue(answers.get(t).getSeValue());  
									sheet.autoSizeColumn((short)0); //调整第一列宽度
							        sheet.autoSizeColumn((short)1); //调整第二列宽度
							        sheet.autoSizeColumn((short)2); //调整第三列宽度
				        		}
				        	}else{
				        		row1 = sheet.createRow(m + 2);    
								row1.createCell(0).setCellValue(m+1);
								Selecter selecter = selecterList.get(m);    
								row1.createCell(0).setCellValue(m+1);    
								row1.createCell(1).setCellValue(selecter.getContent());    
								row1.createCell(2).setCellValue(selecter.getTotal());    
								row1.createCell(3).setCellValue(selecter.getAnswerTotal());    
								row1.createCell(4).setCellValue(selecter.getPercent());
								sheet.autoSizeColumn((short)0); //调整第一列宽度
						        sheet.autoSizeColumn((short)1); //调整第二列宽度
						        sheet.autoSizeColumn((short)2); //调整第三列宽度
						        sheet.autoSizeColumn((short)3); //调整第四列宽度
						        sheet.autoSizeColumn((short)4); //调整第四列宽度
				        	}
						}   
				}
			}
		}
		return wb;   
	}
}
