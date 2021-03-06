package com.gome.manager.controler;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
import com.gome.manager.common.util.DateUtil;
import com.gome.manager.common.util.ResponsesDTO;
import com.gome.manager.common.util.SendMail;
import com.gome.manager.constants.ReturnCode;
import com.gome.manager.controler.base.BaseController;
import com.gome.manager.domain.Reading;
import com.gome.manager.domain.ReadingArticle;
import com.gome.manager.domain.ReadingArticleTheme;
import com.gome.manager.domain.User;
import com.gome.manager.domain.UserBean;
import com.gome.manager.service.ReadingService;
import com.gome.manager.service.UserService;
import com.gome.manager.service.VedioService;

@Controller
@RequestMapping(value = "/learning")
public class LearningController extends BaseController{

	@Resource
	private ReadingService readingService;
	@Resource
	private UserService userService;
	@Resource
	private VedioService vedioService;

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
	@RequestMapping(value="/queryReadingView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView queryReadingView(@Param(value="title")String title, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(page)){
			model.setViewName("/learning/readingList");
		} else {
			model.setViewName("/learning/readingTable");
		}
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}

		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Reading> p = new Page<Reading>(pageNo, pageSize);

		Reading reading = new Reading();
		if(StringUtils.isNotEmpty(title)){
			reading.setTitle(title);
		}
		reading.setAtype("2");
		p.setConditions(reading);
		Page<Reading> pageReading = readingService.findReadingListByPage(p);
		model.addObject("page", pageReading);
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
	@RequestMapping(value="/toAddReadingView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddReadingView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/learning/addReading");
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
	@RequestMapping(value="/addReading", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addReading(@RequestParam("fileUrl")MultipartFile fileUrl,@Param(value="title")String title,@Param(value="typeName")String typeName,@Param(value="catname")String catname,@Param(value="createTime")String createTime,@Param(value="author")String author, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(title)){
				Reading reading = new Reading();
				reading.setTitle(title);
				reading.setTypeName(typeName);
				reading.setCreateTime(createTime);
				reading.setAuthor(author);
				reading.setAtype("2");
				String filepath = "";
				if(reading != null){
					//图片路径
					try {
						if(fileUrl!=null){
							filepath=upLoadFile(fileUrl, request);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
//					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//					String format = df.format(new Date());
//					reading.setCreateTime(format);
					if(fileUrl.getSize()>0){
						reading.setFileUrl(filepath);
					}
					int record = readingService.addReading(reading);
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
		return File.separator+"gmshopFile"+File.separator +fileName;  
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
	@RequestMapping(value="/toEditReadingView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditReadingView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/learning/editReading");
		if(StringUtils.isNotEmpty(id)){
			Reading reading = readingService.findReadingById(Long.parseLong(id));
			if(reading != null){
				model.addObject("reading", reading);
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
	@RequestMapping(value="/editReading", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editReading(@RequestParam("fileUrl")MultipartFile fileUrl,@Param(value="id")String id,@Param(value="title")String title,@Param(value="typeName")String typeName,@Param(value="catname")String catname,@Param(value="createTime")String createTime,@Param(value="author")String author, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(title != null){
				Reading reading = new Reading();
				reading.setId(Long.parseLong(id));
				reading.setTitle(title);
				reading.setTypeName(typeName);
				reading.setCatname(catname);
				reading.setCreateTime(createTime);
				reading.setAuthor(author);
				reading.setCreateTime(createTime);
				reading.setAuthor(author);
				String filepath = "";
				if(reading != null){
					//图片路径
					try {
						if(fileUrl!=null){
							filepath=upLoadFile(fileUrl, request);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
//					DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//					String format = df.format(new Date());
//					reading.setCreateTime(format);
					if(fileUrl.getSize()>0){
						reading.setFileUrl(filepath);
					}
					int record = readingService.updateReading(reading);
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
	@RequestMapping(value="/deleteReading", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteReading(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long ReadingId = Long.parseLong(id);
				int record = readingService.deleteReadingById(ReadingId);
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
	 * 获取文章列表.
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
	@RequestMapping(value="/queryArticleList", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryArticleList(@Param(value="title")String title,HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Reading reading = new Reading();
			reading.setAtype("2");
			if(title!=null && title!=""){
				reading.setTitle(java.net.URLDecoder.decode(title,"UTF-8"));
			}
			List<ReadingArticle> readingArticleList = vedioService.queryArticleList(reading);
			if(readingArticleList != null){
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(readingArticleList);
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
	 * 获取文章详情.
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
	@RequestMapping(value="/queryArticleDetail", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO queryArticleDetail(@Param(value="articleId")String articleId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			ReadingArticle readingArticleDetail = vedioService.queryArticleDetail(articleId);
			if(readingArticleDetail != null){
				responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				responses.setAttach(readingArticleDetail);
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
	 * 跳转到文献主题.
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
	@RequestMapping(value="/toThemeView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toThemeView(@Param(value="id")String id,HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/learning/theme");
		String userName = getUserInfo(request, "userName");
		ReadingArticleTheme articleTheme = new ReadingArticleTheme();
		articleTheme.setRid(Long.parseLong(id));
		articleTheme = readingService.queryTheme(articleTheme);
		model.addObject("operateUser", userName);
		model.addObject("articleTheme", articleTheme);
		Reading reading = new Reading();
		reading.setId(Long.parseLong(id));
		model.addObject("id", id);
		model.addObject("reading", reading);
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
	@RequestMapping(value="/addTheme", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addTheme(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				ReadingArticleTheme articleTheme = JSON.parseObject(content, ReadingArticleTheme.class);
				if(articleTheme != null){
					int record = readingService.addTheme(articleTheme);
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
	 * 下载学习资料.
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
	@RequestMapping(value="/downloadLearning", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO downloadLearning(@Param(value="articleId")String articleId,@Param(value="perId")String perId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(perId!=null){
				//判断用户乐豆是否足够
				/**
				 * 查看当前用户是否已经下载过此文献
				 */
				UserBean userBean = new UserBean();
				userBean.setSourceId(Long.valueOf(articleId));
				userBean.setUserId(Long.parseLong(perId));
				UserBean userBean1 = userService.queryUserIfDownload(userBean);
				int totalBean=0;
				if(userBean1!=null){
					totalBean = 21;
				}else{
					totalBean = userService.getTotalBean(Long.parseLong(perId));
				}
				if(totalBean>=20){
					User user = userService.queryUserDetail(Long.parseLong(perId));
					if(user!=null){
						ReadingArticle readingArticleDetail = readingService.queryArticleDetail(articleId);
						if(readingArticleDetail!=null){
							if(readingArticleDetail.getFileUrl()==null || "".equals(readingArticleDetail.getFileUrl().trim())){
								responses.setReturnCode(ReturnCode.ERROR_RESOURCES);
								return responses;
							}
							String[] str_to = {user.getEmail()};
							String str_title = readingArticleDetail.getTitle();
							String str_content = DateUtil.DateToStr("yyyy-MM-dd HH:mm:ss") + "您下载了标题为《"+readingArticleDetail.getTitle()+"》的指南共识，敬请查阅！";
							 String path  = request.getSession().getServletContext().getRealPath("/") ;
								if(path!=null && !"".equals(path)){
									path = path.substring(0, path.indexOf("gome-manager-web")-1);
								}
							String filePath = path+readingArticleDetail.getFileUrl();
							String fileName = "指南共识";
							try {
								SendMail.sendEmailAttachment(str_to, str_title, str_content, filePath, fileName);
							} catch (Exception e) {
								responses.setReturnCode(ReturnCode.ERROR_RESOURCES);
								return responses;
							}
							
							//下载成功后
							userBean.setAmount(-20);
							userBean.setDescribes("下载指南共识");
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							String getTime = df.format(new Date());
							userBean.setGetTime(getTime);
							userBean.setSourceType("8");
							if(userBean1!=null){
								userBean.setDescribes("重复下载指南共识");
								userBean.setSourceType("9");
							}
							userService.addUserBean(userBean);
							responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
						} else {
							responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
						}
					}
				}else{
					responses.setCode(1024);
					responses.setAttach("您的乐豆不足");
				}
			}else{
				responses.setReturnCode(ReturnCode.ERROR_NO_LOGIN);
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
}
