package com.gome.manager.controler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.ReadingReview;
import com.gome.manager.domain.ReadingSupport;
import com.gome.manager.domain.UserBean;
import com.gome.manager.service.ReadingReviewService;
import com.gome.manager.service.UserService;

@Controller
@RequestMapping(value = "/review")
public class ReadingReviewController extends BaseController{

	@Resource
	private ReadingReviewService readingReviewService;
	@Resource
	private UserService userService;
	/**
	 *
	 * 获取评论列表.
	 *
	 * @param catid期ID
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
	@RequestMapping(value="/queryReviewList", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryReviewList(@Param(value="professorId")String professorId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			ReadingReview review = new ReadingReview();
			review.setYid(Long.parseLong(professorId));
			review.setTypeV("1");
			List<ReadingReview> readingReviewList = readingReviewService.queryReviewList(review);
			if(readingReviewList != null){
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(readingReviewList);
			} else {
				responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}

	/**
	 * 
	 * 新增文献评论.
	 *
	 * @param content
	 * 				文献导读信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addReview", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addReview(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				ReadingReview readingReview = JSON.parseObject(content, ReadingReview.class);
				if(readingReview != null){
					readingReview.setContent(java.net.URLDecoder.decode(readingReview.getContent(),"UTF-8"));
					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String format = df.format(new Date());
					readingReview.setCreateTime(format);
					readingReview.setTypeV("1");
					int record = readingReviewService.addReview(readingReview);
					if(record > 0){
						UserBean userBean = new UserBean();
						userBean.setSourceType("2");
						userBean.setSourceId(readingReview.getYid());
						userBean.setDescribes("评论赠送");
						userBean.setUserId(Long.parseLong(readingReview.getPerId()));
						userService.addUserBean(userBean);
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
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
	 * 新增文献点赞.
	 *
	 * @param content
	 * 				文献导读信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addSupport", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addSupport(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				ReadingSupport readingSupport = JSON.parseObject(content, ReadingSupport.class);
				if(readingSupport != null){
					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					String format = df.format(new Date());
					readingSupport.setCreateTime(format);
					readingSupport.setTypeV("1");
					int record = readingReviewService.addSupport(readingSupport);
					if(record > 0){
						UserBean userBean = new UserBean();
						userBean.setSourceType("1");
						userBean.setSourceId(readingSupport.getYid());
						userBean.setDescribes("点赞赠送");
						userBean.setUserId(Long.parseLong(readingSupport.getPerId()));
						userService.addUserBean(userBean);
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
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
	 * 查询是否点赞.
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
	@RequestMapping(value="/queryIfSupport", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryIfSupport(@Param(value="professorId")String professorId,@Param(value="perId")String perId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(professorId)){
				ReadingSupport readingSupport = new ReadingSupport();
				readingSupport.setPerId(perId);
				readingSupport.setTypeV("1");
				readingSupport.setYid(Long.parseLong(professorId));
				if(readingSupport != null){
					int record = readingReviewService.queryIfSupport(readingSupport);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						responses.setAttach(record);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
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
	 * 查询点赞总数.
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
	@RequestMapping(value="/querySupportTotal", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO querySupportTotal(@Param(value="professorId")String professorId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(professorId)){
				ReadingSupport readingSupport = new ReadingSupport();
				readingSupport.setYid(Long.parseLong(professorId));
				readingSupport.setTypeV("1");
				if(readingSupport != null){
					int record = readingReviewService.querySupportTotal(readingSupport);
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					responses.setAttach(record);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
}
