package com.gome.manager.controler;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gome.manager.common.util.DateUtil;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.common.util.SendMail;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Meeting;
import com.gome.manager.domain.MeetingData;
import com.gome.manager.service.MeetingDataService;

@Controller
@RequestMapping(value = "/meetingData")
public class MeetingDataController extends BaseController{

	@Resource
	private MeetingDataService meetingDataService;


	/**
	 * 跳转到资料页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/toDataView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toDataView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/meeting/data");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		List<MeetingData> dataList = meetingDataService.queryData(id);
		Meeting meeting = new Meeting();
		meeting.setId(Long.parseLong(id));
		model.addObject("meeting", meeting);
		model.addObject("dataList", dataList);
		return model;
	}

	/**
	 * 
	 * 新增资料.
	 *
	 * @param content
	 * 				会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addData", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addData(@RequestParam("fileUrl")MultipartFile fileUrl,@Param(value="meetId")String meetId,@Param(value="picPath")String picPath,@Param(value="detail")String detail,@Param(value="speaker")String speaker,@Param(value="hospitol")String hospitol,HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		String filepath=null;
		try{
				MeetingData meetingData = new MeetingData();
				meetingData.setCode(meetId);
				meetingData.setPictureUrl(picPath);
				meetingData.setName(fileUrl.getOriginalFilename());
				meetingData.setDetail(detail);
				meetingData.setSpeaker(speaker);
				meetingData.setHospitol(hospitol);
				if(meetingData != null){
					//图片路径
					try {
						if(fileUrl!=null){
							filepath=upLoadFile(fileUrl, request);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					meetingData.setFileUrl(filepath);
					int record = meetingDataService.addData(meetingData);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
				}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 删除资料.
	 *
	 * @param content
	 * 				会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/delData", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO delData(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long DataId = Long.parseLong(id);
				MeetingData meetingData = new MeetingData();
				meetingData.setId(DataId);
				int record = meetingDataService.delData(meetingData);
				if(record > 0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	/**
	 * 
	 * 删除资料.
	 *
	 * @param content
	 * 				会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/queryData", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryData(@Param(value="meetId")String meetId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(meetId)){
				List<MeetingData> dataList = meetingDataService.queryData(meetId);
				if(dataList !=null && dataList.size()>0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(dataList);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	
	
	public static String upLoadFile(MultipartFile file,HttpServletRequest request) throws Exception{
        String fileName = new Date().getTime()+file.getOriginalFilename(); 
        String path  = request.getSession().getServletContext().getRealPath("/") ;
		if(path!=null && !"".equals(path)){
			path = path.substring(0, path.indexOf("gome-manager-web")-1);
		}
		path = path+ File.separator + "gmshopFile";
        System.out.println(path);
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return File.separator+"gmshopFile"+File.separator +fileName;  
	}
	
	
	/**
	 * 
	 * 查询投票题目状态.
	 *
	 * @param content
	 * 				会议信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/downloadFile", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO downloadFile(@Param(value="id")String id,@Param(value="emailAddress")String emailAddress, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				System.out.println(id);
				System.out.println(emailAddress);
				boolean email = checkEmail(emailAddress);
				if(!email){
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					return responses;
				}
				MeetingData data = meetingDataService.queryDataById(id);
				if(data !=null && email &&emailAddress!=null){
					String[] str_to = {emailAddress};
					String str_title = "嘉音——会议资料";
					String str_content = "尊敬的嘉音用户，您好：\r\n"
							+"\r\n"+DateUtil.DateToStr("yyyy-MM-dd HH:mm:ss")+"您在使用使用<嘉音>的过程中有下载了《"+data.getName()+"》全文，我们以邮件的形式发送给您，便于您安排时间阅读"
							+"\r\n详细内容请见附件"
							+"\r\n非常感谢您对嘉音的支持与厚爱，我们会继续努力为您提供更优质的服务！";
					String path  = request.getSession().getServletContext().getRealPath("/") ;
					if(path!=null && !"".equals(path)){
						path = path.substring(0, path.indexOf("gome-manager-web")-1);
					}
					String filePath = path+data.getFileUrl();
					String fileName = data.getName();
					SendMail.sendEmailAttachment(str_to, str_title, str_content, filePath, fileName);
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
}
