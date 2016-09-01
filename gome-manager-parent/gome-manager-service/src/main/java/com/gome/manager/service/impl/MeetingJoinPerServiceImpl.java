package com.gome.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.gome.manager.dao.MeetingJoinPerMapper;
import com.gome.manager.domain.MeetingJoinPer;
import com.gome.manager.service.MeetingJoinPerService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingManagerService")
public class MeetingJoinPerServiceImpl implements MeetingJoinPerService {

	@Resource
	private MeetingJoinPerMapper meetingJoinPerMapper;

	@Override
	public List<MeetingJoinPer> queryJoinPer(String meetingId) {
		MeetingJoinPer meetingJoinPerNew = new MeetingJoinPer();
		meetingJoinPerNew.setCode(meetingId);
		return meetingJoinPerMapper.queryJoinPer(meetingJoinPerNew);
	}

	@Override
	public int addJoinPer(MeetingJoinPer meetingJoinPer) {
		String name = meetingJoinPer.getName();
		if(name!=null){
			String[] split = name.split("/");
			for (int i = 0; i < split.length; i++) {
				String perName = split[i];
				MeetingJoinPer meetingJoinPerNew = new MeetingJoinPer();
				meetingJoinPerNew.setCode(meetingJoinPer.getCode());
				meetingJoinPerNew.setName(perName);
				meetingJoinPerNew.setUnit(meetingJoinPer.getUnit());
				meetingJoinPerNew.setOffice(meetingJoinPer.getOffice());
				meetingJoinPerNew.setJobs(meetingJoinPer.getJobs());
				meetingJoinPerNew.setPhone(meetingJoinPer.getPhone());
				meetingJoinPerNew.setPosition(meetingJoinPer.getPosition());
				meetingJoinPerMapper.addJoinPer(meetingJoinPerNew);
			}
		}
		return 1;
	}

	@Override
	public int delJoinPer(MeetingJoinPer meetingJoinPer) {
		meetingJoinPerMapper.deleteJoinPerById(meetingJoinPer);
		return 1;
	}

	@Override
	public MeetingJoinPer signMeetJionPerson(MeetingJoinPer joinPerNew) {
		return meetingJoinPerMapper.signMeetJionPerson(joinPerNew);
	}

	@Override
	public void updateSignMeetJionPerson(MeetingJoinPer joinPer) {
		meetingJoinPerMapper.updateSignMeetJionPerson(joinPer);
	}

	@Override
	public void updateMeetUnOpenFlag(MeetingJoinPer joinPerNew2) {
		meetingJoinPerMapper.updateMeetUnOpenFlag(joinPerNew2);
	}

	@Override
	public List<MeetingJoinPer> queryJoinPer(MeetingJoinPer joinPer) {
		return meetingJoinPerMapper.queryJoinPer(joinPer);
	}

	@Override
	public HSSFWorkbook exportExcel(List<MeetingJoinPer> meetingJoinPerList) {
		String[] excelHeader = { "序号", "参会人姓名","医院", "科室","职称","职务","电话","签到时间"};    
		HSSFWorkbook wb = new HSSFWorkbook();    
		HSSFSheet sheet = wb.createSheet("Campaign");    
		HSSFRow row = sheet.createRow((int) 0);    
		HSSFCellStyle style = wb.createCellStyle();    
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
		style.setWrapText(true);    
		style.setFillBackgroundColor((short)2);

		 // 设置字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 20); //字体高度
        font.setColor(HSSFFont.COLOR_RED); //字体颜色
        font.setFontName("黑体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); //宽度
        font.setItalic(false); //是否使用斜体
       
		for (int i = 0; i < excelHeader.length; i++) {    
			HSSFCell cell = row.createCell(i);    
			cell.setCellValue(excelHeader[i]);    
			style.setFont(font);
			cell.setCellStyle(style); 
			sheet.autoSizeColumn(i);    
		}    
		for (int i = 0; i < meetingJoinPerList.size(); i++) {    
			row = sheet.createRow(i + 1);    
			MeetingJoinPer meetingJoinPer = meetingJoinPerList.get(i);    
			row.createCell(0).setCellValue(i+1);    
			row.createCell(1).setCellValue(meetingJoinPer.getName());    
			row.createCell(2).setCellValue(meetingJoinPer.getUnit());    
			row.createCell(3).setCellValue(meetingJoinPer.getOffice());    
			row.createCell(4).setCellValue(meetingJoinPer.getJobs());    
			row.createCell(5).setCellValue(meetingJoinPer.getPosition());    
			row.createCell(6).setCellValue(meetingJoinPer.getPhone());    
			row.createCell(7).setCellValue(meetingJoinPer.getSignTime());    
			sheet.autoSizeColumn((short)0); //调整第一列宽度
	        sheet.autoSizeColumn((short)1); //调整第二列宽度
	        sheet.autoSizeColumn((short)2); //调整第三列宽度
	        sheet.autoSizeColumn((short)3); //调整第四列宽度
	        sheet.autoSizeColumn((short)4); //调整第四列宽度
	        sheet.autoSizeColumn((short)5); //调整第四列宽度
	        sheet.autoSizeColumn((short)6); //调整第四列宽度
	        sheet.autoSizeColumn((short)7); //调整第四列宽度
		}    
		return wb;   
	}
}
