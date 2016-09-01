package com.gome.manager.controler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.manager.common.Constant;
import com.gome.manager.common.Page;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.ReadingReview;
import com.gome.manager.domain.ReadingSupport;
import com.gome.manager.domain.UserBean;
import com.gome.manager.domain.Vedio;
import com.gome.manager.service.ReadingReviewService;
import com.gome.manager.service.UserService;
import com.gome.manager.service.VedioService;

@Controller
@RequestMapping(value = "/vedio")
public class VedioController extends BaseController{
	@Resource
	private VedioService vedioService;

	@Resource
	private ReadingReviewService readingReviewService;
	@Resource
	private UserService userService;
	/**
	 * 
	 * 跳转到学习资料列表页.
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
	@RequestMapping(value="/queryVedioView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView queryVedioView(@Param(value="title")String title, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(page)){
			model.setViewName("/learning/vedioList");
		} else {
			model.setViewName("/learning/vedioTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}

		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Vedio> p = new Page<Vedio>(pageNo, pageSize);

		Vedio vedio = new Vedio();
		if(StringUtils.isNotEmpty(title)){
			vedio.setTitle(title);
		}
		p.setConditions(vedio);
		Page<Vedio> pageVedio = vedioService.findVedioListByPage(p);
		model.addObject("page", pageVedio);
		model.addObject("title", title);
		return model;
	}

	/**
	 * 跳转到新增学习资料页.
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
	@RequestMapping(value="/toAddVedioView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddVedioView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/learning/addVedio");
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		return model;
	}

	/**
	 * 
	 * 新增学习资料.
	 *
	 * @param content
	 * 				学习资料信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/addVedio", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addVedio(@RequestParam("fileUrl")MultipartFile fileUrl,@Param(value="title")String title,@Param(value="vType")String vType,@Param(value="picPath")String picPath,@Param(value="belong")String belong,@Param(value="createTime")String createTime, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(title)){
				Vedio vedio = new Vedio();
				vedio.setTitle(title);
				vedio.setCreateTime(createTime);
				vedio.setBelong(belong);
				vedio.setPicPath(picPath);
				vedio.setvType(vType);
				String filepath = "";
				if(vedio != null){
					//图片路径
					try {
						if(fileUrl!=null){
							long size = fileUrl.getSize();
							if(size>0){
								double f = size/1024/1024;
								BigDecimal bd = new BigDecimal(f);
								bd = bd.setScale(3,BigDecimal.ROUND_HALF_UP);
								vedio.setVedioSize(String.valueOf(bd));
							}
							filepath=upLoadFile(fileUrl, request);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(fileUrl.getSize()>0){
						vedio.setFileUrl(filepath);
					}
					int record = vedioService.addVedio(vedio);
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
		return File.separator+"gmshopFile"+File.separator+"vedio"+File.separator +fileName;  
	}
	/**
	 * 
	 * 跳转到编辑学习资料页.
	 *
	 * @param id
	 * 			学习资料ID
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
	@RequestMapping(value="/toEditVedioView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditVedioView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/learning/editVedio");
		if(StringUtils.isNotEmpty(id)){
			Vedio vedio = vedioService.findVedioById(Long.parseLong(id));
			if(vedio != null){
				model.addObject("vedio", vedio);
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
			}
		}
		return model;
	}

	/**
	 * 
	 * 编辑学习资料.
	 *
	 * @param content
	 * 			学习资料信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/editVedio", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editVedio(@RequestParam("fileUrl")MultipartFile fileUrl,@Param(value="id")String id,@Param(value="title")String title,@Param(value="vType")String vType,@Param(value="picPath")String picPath,@Param(value="belong")String belong,@Param(value="createTime")String createTime, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(title != null){
				Vedio vedio = new Vedio();
				vedio.setId(Long.parseLong(id));
				vedio.setTitle(title);
				vedio.setBelong(belong);
				vedio.setCreateTime(createTime);
				vedio.setPicPath(picPath);
				vedio.setvType(vType);
				String filepath = "";
				//图片路径
				try {
					if(fileUrl!=null){
						long size = fileUrl.getSize();
						if(size>0){
							double f = size/1024;
							double f1 = f/1024;
							BigDecimal bd = new BigDecimal(f1);
							bd = bd.setScale(3,BigDecimal.ROUND_HALF_UP);
							vedio.setVedioSize(String.valueOf(bd));
						}
						filepath=upLoadFile(fileUrl, request);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				vedio.setFileUrl(filepath);
				int record = vedioService.updateVedio(vedio);
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
	 * 删除学习资料.
	 *
	 * @param id
	 * 			学习资料ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    liuhaikun    新建
	 * </pre>
	 */
	@RequestMapping(value="/deleteVedio", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteVedio(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long vedioId = Long.parseLong(id);
				int record = vedioService.deleteVedioById(vedioId);
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
	 * 获取视频列表.
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
	@RequestMapping(value="/querVedioList", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO querVedioList(HttpServletRequest request, HttpServletResponse response,@Param(value="vType")String vType){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Vedio vedio = new Vedio();
			vedio.setvType(vType);
			List<Vedio> vedioList = vedioService.querVedioList(vedio);
			if(vedioList != null){
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(vedioList);
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
	 * 获取视屏详情.
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
	@RequestMapping(value="/queryVedioDetail", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryVedioDetail(@Param(value="vedioId")String vedioId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Vedio vedio = vedioService.queryVedioDetail(Long.parseLong(vedioId));
			if(vedio != null){
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(vedio);
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
			review.setTypeV("2");
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
					readingReview.setTypeV("2");
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
					readingSupport.setTypeV("2");
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
				readingSupport.setTypeV("2");
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
				readingSupport.setTypeV("2");
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
