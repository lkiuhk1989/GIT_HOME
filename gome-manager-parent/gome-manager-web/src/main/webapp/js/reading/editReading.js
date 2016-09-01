/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//保存
	var yearValue = $("#yearValue").val();
	var catname = $("#catname").val();
	 $("#yearValue").find("option[value='"+yearValue+"']").attr("selected",true);
	 $("#host").find("option[value='"+host+"']").attr("selected",true);
	 
	$("#save_btn").click(function(){
		var content={};
		content.title=$("#title").val();
    	content.yearValue= $("#yearValue").children('option:selected').val();
    	content.catname=$("#catname").children('option:selected').val();
    	content.fileUrl=$("#fileUrl").val();
    	$.ajax({
    		url:'../reading/editMeeting',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			content:JSON.stringify(content)
    		},
    		success:function(data){
    			if(data.code==1){
    				$("#tishi").text("修改成功");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		$("#altbtn").unbind("click");
			  		$("#altbtn").bind("click",function(){
						 $("#mod-dialog").hide();
						 $("#mod-dialog-bg").hide();
						 window.location.href="../reading/queryReadingView";
					});
    			}else{
    				$("#tishi").text("修改失败");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		$("#altbtn").unbind("click");
			  		$("#altbtn").bind("click",function(){
						 $("#mod-dialog").hide();
						 $("#mod-dialog-bg").hide();
					});
    			}
    		},
    		error:function(){
    			$("#tishi").text("系统异常");
				$("#mod-dialog").show();
		  		$("#mod-dialog-bg").show();
		  		$("#altbtn").unbind("click");
		  		$("#altbtn").bind("click",function(){
					 $("#mod-dialog").hide();
					 $("#mod-dialog-bg").hide();
				});
    		}
    		
    	});
	});
	
	function checkSubmit(){
    	if(!name()){
    		return false;
    	}
    	if(!theme()){
    		return false;
    	}
		if(check.checkNull("picPath")){
    		check.showErrorMessage("picPath", "请上传图片！");
    		return false;
    	}
		if(!checkDescription()){
			return false;
		}
    	//清除所有错误信息
		$("span[id$=ErrorSpan]").html("");
    	return true;
	}
	
	function name(){
		if(check.checkNull("name")){
    		check.showErrorMessage("name", "请输入商品名称！");
    		return false;
    	}
    	if($("#name").val().length < 4){
    		check.showErrorMessage("name", "长度太短！");
    		return false;
    	}
		if (!check.checkFormat(check.nameReg, "name")) {
			check.showErrorMessage("name", '只能包含汉字、数字、字母、下划线并且不能以下划线开头和结尾，长度为4-16位！');
			return false;
		}
		check.clearErrorMessage("name");
		return true;
	}
	
	function theme(){
		if(check.checkNull("theme")){
			check.showErrorMessage("theme", "请输入商品名称！");
			return false;
		}
		if($("#theme").val().length < 4){
			check.showErrorMessage("theme", "长度太短！");
			return false;
		}
		if (!check.checkFormat(check.nameReg, "theme")) {
			check.showErrorMessage("theme", '只能包含汉字、数字、字母、下划线并且不能以下划线开头和结尾，长度为4-16位！');
			return false;
		}
		check.clearErrorMessage("theme");
		return true;
	}
	
	//返回
	$(".return-btn").click(function(){
		history.back();
	});
	
});