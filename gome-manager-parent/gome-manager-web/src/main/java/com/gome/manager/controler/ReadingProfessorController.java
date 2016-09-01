package com.gome.manager.controler;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.manager.common.Constant;
import com.gome.manager.common.Page;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticleProfessor;
import com.gome.manager.service.ReadingProfessorService;
import com.gome.manager.service.ReadingService;

@Controller
@RequestMapping(value = "/readProfessor")
public class ReadingProfessorController extends BaseController{

		@Resource
		private ReadingService readingService;
		@Resource
		private ReadingProfessorService readingProfessorService;
		
		/**
		 * 
		 * 跳转到会议专家列表页.
		 *
		 * @param content
		 * 				搜索条件
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 * @throws UnsupportedEncodingException 
		 */
		@RequestMapping(value="/queryProfessorListView", method={RequestMethod.GET,RequestMethod.POST})
		public ModelAndView queryProfessorListView(@Param(value="id")String id,@Param(value="pName")String pName, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
			if(StringUtils.isEmpty(page)){
				model.setViewName("/reading/professorList");
			} else {
				model.setViewName("/reading/professorTable");
			}
			int pageNo = 1;
			if(StringUtils.isNotEmpty(page)){
				pageNo = Integer.parseInt(page);
			}
			
			int pageSize = Constant.PAGE_SIZE;
			if(StringUtils.isNotEmpty(size)){
				pageSize = Integer.parseInt(size);
			}
			Page<ReadingArticleProfessor> p = new Page<ReadingArticleProfessor>(pageNo, pageSize);
			
			ReadingArticleProfessor professor = new ReadingArticleProfessor();
			if(StringUtils.isNotEmpty(pName)){
				professor.setName(pName);
			}
			professor.setRid(Long.parseLong(id));
			p.setConditions(professor);
			Page<ReadingArticleProfessor> pageReadingProfessor = readingProfessorService.findReadingProfessorListByPage(p);
			Reading reading = new Reading();
			reading.setId(Long.parseLong(id));
			model.addObject("reading", reading);
			model.addObject("pName", pName);
			model.addObject("page", pageReadingProfessor);
			return model;
		}
		
		/**
		 * 跳转到新增会议专家页.
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
		@RequestMapping(value="/toAddReadingProfessorView", method={RequestMethod.GET, RequestMethod.POST})
		public ModelAndView toAddReadingProfessorView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
			model.setViewName("/reading/addProfessor");
			String userName = getUserInfo(request, "userName");
			Reading reading = readingService.findReadingById(Long.parseLong(id));
			reading.setId(Long.parseLong(id));
			model.addObject("reading", reading);
			model.addObject("operateUser", userName);
			return model;
		}
		/**
		 * 
		 * 新增会议专家.
		 *
		 * @param content
		 * 				会议专家信息
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/addReadingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO addReadingProfessor(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(content)){
					ReadingArticleProfessor professor = JSON.parseObject(content, ReadingArticleProfessor.class);
					if(professor != null){
						int record = readingProfessorService.addReadingProfessor(professor);
						if(record > 0){
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
		 * 跳转到编辑会议专家页.
		 *
		 * @param id
		 * 			会议专家ID
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/toEditReadingProfessorView", method={RequestMethod.GET,RequestMethod.POST})
		public ModelAndView toEditReadingProfessorView(@Param(value="id")String id,@Param(value="rid")String rid, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
			model.setViewName("/reading/editReadingProfessor");
			if(StringUtils.isNotEmpty(id)){
				ReadingArticleProfessor professor = readingProfessorService.findReadingProfessorById(Long.parseLong(id));
				if(professor != null){
					model.addObject("professor", professor);
					String userName = getUserInfo(request, "userName");
					model.addObject("operateUser", userName);
				}
			}
			Reading reading = new Reading();
			reading.setId(Long.parseLong(rid));
			model.addObject("reading", reading);
			return model;
		}
		
		/**
		 * 
		 * 编辑会议专家.
		 *
		 * @param content
		 * 			会议专家信息
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/editReadingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO editReadingProfessor(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				ReadingArticleProfessor professor = JSON.parseObject(content, ReadingArticleProfessor.class);
				if(professor != null){
					int record = readingProfessorService.updateReadingProfessor(professor);
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
		 * 删除会议专家.
		 *
		 * @param id
		 * 			会议专家ID
		 * @param request
		 * @param response
		 * @return
		 *
		 * <pre>
		 * 修改日期        修改人    修改原因
		 * 2015年11月09日    liuhaikun    新建
		 * </pre>
		 */
		@RequestMapping(value="/deleteReadingProfessor", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
		@ResponseBody
		public ResponsesDTO deleteReadingProfessor(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
			ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
			try{
				if(StringUtils.isNotEmpty(id)){
					Long ReadingProfessorId = Long.parseLong(id);
					int record = readingProfessorService.deleteReadingProfessorById(ReadingProfessorId);
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
		
}
