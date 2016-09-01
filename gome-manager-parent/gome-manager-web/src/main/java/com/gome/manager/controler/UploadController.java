package com.gome.manager.controler;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
 * 图片上传控制器.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月3日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value="/imageUpload")
public class UploadController {

	/**
	 * 上传图片
	 * @param file
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/uploadimg",method={RequestMethod.POST})
	@ResponseBody
	public ModelAndView uploadImg(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		//图片路径
		String filepath=null;
		model.setViewName("/common/uploadpic");
		try {
			if(file!=null){
				filepath=upLoadFile(file, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("msg", filepath);
		return model;
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
	
	/**上传图片页面,采用iframe
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/touploadimg")
	public ModelAndView getToUploadImg(ModelAndView model){
		model.setViewName("/common/uploadpic");
		return  model;
	}
}
