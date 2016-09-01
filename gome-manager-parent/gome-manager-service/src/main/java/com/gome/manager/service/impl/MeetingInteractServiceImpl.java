package com.gome.manager.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.gome.manager.dao.MeetingInteractMapper;
import com.gome.manager.domain.MeetingInteract;
import com.gome.manager.service.MeetingInteractService;

/**
 * 
 * 商品service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("meetingInteractService")
public class MeetingInteractServiceImpl implements MeetingInteractService {

	@Resource
	private MeetingInteractMapper meetingInteractMapper;

	@Override
	public List<MeetingInteract> queryInteract(String meetingId) {
		MeetingInteract meetingInteractNew = new MeetingInteract();
		meetingInteractNew.setCode(meetingId);
		return meetingInteractMapper.queryInteract(meetingInteractNew);
	}

	@Override
	public int addInteract(MeetingInteract meetingInteract) {
		if(meetingInteract.getId()!=null && !"".equals(meetingInteract.getId())){
			meetingInteractMapper.updateInteract(meetingInteract);
		}else{
			String floor = meetingInteractMapper.queryFloor(meetingInteract);
			meetingInteract.setFloor(floor);
			meetingInteractMapper.addInteract(meetingInteract);
		}
		return 1;
	}

	@Override
	public int updateInteract(MeetingInteract meetingInteract) {
		String floor = meetingInteractMapper.queryFloor(meetingInteract);
		meetingInteract.setFloor(floor);
		meetingInteractMapper.updateInteract(meetingInteract);
		return 1;
	}

	@Override
	public List<MeetingInteract> queryOnWall(MeetingInteract meetingInteract) {
		List<MeetingInteract> list= meetingInteractMapper.queryOnWall(meetingInteract);
		//获取当前最大maxInteractId
		String maxInterId = meetingInteractMapper.queryMaxInteractId(meetingInteract);
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setMaxInteractId(Long.parseLong(maxInterId));
				String times = list.get(i).getTimes();
				SimpleDateFormat formatter0 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
				try {
					String h = "";
					String s = "";
					Date parse = formatter0.parse(times);
					int hours = parse.getHours();
					int seconds = parse.getMinutes();
					if(hours<10){
						h = "0"+hours;
					}else{
						h = hours+"";
					}
					if(seconds<10){
						s = "0"+seconds;
					}else{
						s = seconds+"";
					}
					list.get(i).setTimes(h+":"+s);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<MeetingInteract> queryOwnHistory(MeetingInteract meetingInteract) {
		return meetingInteractMapper.queryInteract(meetingInteract);
	}

	@Override
	public int deleteWall(MeetingInteract meetingInteract) {
		return meetingInteractMapper.deleteWall(meetingInteract);
	}

	@Override
	public HSSFWorkbook exportExcel(List<MeetingInteract> interactList) {
		String[] excelHeader = { "序号", "姓名", "医院","时间","提问内容"};    
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
		for (int i = 0; i < interactList.size(); i++) {    
			row = sheet.createRow(i + 1);    
			MeetingInteract interact = interactList.get(i);    
			row.createCell(0).setCellValue(i+1);    
			row.createCell(1).setCellValue(interact.getPerName());    
			row.createCell(2).setCellValue(interact.getUnitName());    
			row.createCell(3).setCellValue(interact.getTimes());    
			row.createCell(4).setCellValue(interact.getContent());    
			sheet.autoSizeColumn((short)0); //调整第一列宽度
	        sheet.autoSizeColumn((short)1); //调整第二列宽度
	        sheet.autoSizeColumn((short)2); //调整第三列宽度
	        sheet.autoSizeColumn((short)3); //调整第四列宽度
	        sheet.autoSizeColumn((short)4); //调整第四列宽度
		}    
		return wb;   
	}
}
