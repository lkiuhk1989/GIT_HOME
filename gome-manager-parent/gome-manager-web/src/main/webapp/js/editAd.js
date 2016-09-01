/**
 * Created by caowei on 2015/11/01
 */
$(function(){
	//频道列表change事件
	$("#channelId").change(function(){
		//清空位置列表 
		$("#sort>option").remove(); 
		//获取当前选中的频道
		var channelId = $(this).val();
		//查询所选频道的展示图片数
		$.ajax({
    		url:'../ad/getChannelPicNum',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			channelId:channelId
    		},
    		success:function(data){
    			if(data.code==1){
    				var picNum = data.attach;
    				var html = "";
    				for(var i=1; i<=picNum; i++){
    					html = html + "<option value=" + i + ">" + i + "</option>";
    				}
    				$("#sort").html(html);
    			}else{
    				$("#tishi").text("系统异常");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		
    			}
    				
    		},
    		error:function(){
    			$("#tishi").text("系统异常");
				$("#mod-dialog").show();
		  		$("#mod-dialog-bg").show();
    		}
    		
    	});
		if(checkIsFull(channelId)){
			$("#tishi").text("该频道下的广告位已满，请更换其他频道！");
			$("#mod-dialog").show();
	  		$("#mod-dialog-bg").show();
		}
		
	});
	
	//检查所选频道下的广告位是否已满
	function checkIsFull(channelId){
		var fullFlag = false;
		$.ajax({
    		url:'../ad/checkIsFull',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			channelId:channelId
    		},
    		success:function(data){
    			if(data.code==7){  //已满
    				fullFlag = true;
    			}else{
    				fullFlag = false;
    			}
    		},
    		error:function(){
    			$("#tishi").text("系统异常");
				$("#mod-dialog").show();
		  		$("#mod-dialog-bg").show();
    		}
    		
    	});
		return fullFlag;
	}
	
    $("#altbtn").click(function(){
		 $("#mod-dialog").hide();
		 $("#mod-dialog-bg").hide();
	});
	
	//保存
	$("#save_btn").click(function(){
		if(!checkSubmit()){
			return false;
		}
		var content={};
		content.id=$("#adId").val();
    	content.channelId=$("#channelId").val();
    	content.sort=$("#sort").val();
    	content.picName=$("#picName").val();
    	content.picPath=$("#picPath").val();
    	content.links=$("#links").val();
    	content.status=$("input[name='status']:checked").val();
    	content.operateUser=$("#operateUser").val();
    	$.ajax({
    		url:'../ad/editAd',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			content:JSON.stringify(content)
    		},
    		success:function(data){
    			if(data.code==7){
    				$("#tishi").text("该频道下的广告位已满，无法修改！");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		
    			} else if(data.code==10){
    				$("#tishi").text("该频道下的相应位置已存在广告位，无法修改！");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
    			} else if(data.code==1){
    				$("#tishi").text("修改成功");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		$("#altbtn").unbind("click");
			  		$("#altbtn").bind("click",function(){
						 $("#mod-dialog").hide();
						 $("#mod-dialog-bg").hide();
						 window.location.href="../ad/toAdListView";
					});
    			}else{
    				$("#tishi").text("修改失败");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
    			}
    		},
    		error:function(){
    			$("#tishi").text("系统异常");
				$("#mod-dialog").show();
		  		$("#mod-dialog-bg").show();
    		}
    		
    	});
	});
	
	$("#picName").blur(checkPicName);
	$("#links").blur(checkLinks);
	
	function checkSubmit(){
    	if(check.checkNull("channelId")){
    		check.showErrorMessage("channelId", "请选择频道！");
    		return false;
    	}
    	if(check.checkNull("sort")){
    		check.showErrorMessage("sort", "请选择位置！");
    		return false;
    	}
    	if(!checkPicName()){
    		return false;
    	}
		if(check.checkNull("picPath")){
    		check.showErrorMessage("picPath", "请上传图片！");
    		return false;
    	}
		if(!checkLinks()){
    		return false;
    	}
    	
    	//清除所有错误信息
		$("span[id$=ErrorSpan]").html("");
    	return true;
	}
	
	function checkPicName(){
		if(check.checkNull("picName")){
    		check.showErrorMessage("picName", "请输入图片名称！");
    		return false;
    	}
    	if($("#picName").val().length < 4){
    		check.showErrorMessage("picName", "长度太短！");
    		return false;
    	}
		if (!check.checkFormat(check.nameReg, "picName")) {
			check.showErrorMessage("picName", '只能包含汉字、数字、字母、下划线并且不能以下划线开头和结尾，长度为4-16位！');
			return false;
		}
		check.clearErrorMessage("picName");
		return true;
	}
	
	function checkLinks(){
		if(check.checkNull("links")){
    		check.showErrorMessage("links", "请输入链接地址！");
    		return false;
    	}
		if($("#links").val().length > 80){
    		check.showErrorMessage("links", "长度不能超过80位！");
    		return false;
    	}
    	if (!check.checkFormat(check.urlReg, "links")) {
			check.showErrorMessage("links", '链接地址不合法，只能以http/https开头！');
			return false;
		}
    	check.clearErrorMessage("links");
		return true;
	}
	
	//检查频道名称是否已经被占用
	/*
	$("#channelName").blur(function(){
		var id = $("#channelId").val();
    	var channelName = $("#channelName").val();
    	if(channelName == null || channelName == ''){
    		alert("频道名称不能为空！");
    		return false;
    	}
    	$.ajax({
    		url:'../channel/checkIsUsed',
    		type:'POST',
    		dataType:'json',
    		data:{
    			id:id,
    			channelName:channelName
    		},
    		success:function(data){
    				//5:名称已被占用
    				//-1:数据异常
    				//2:操作失败
    				alert(data.attach);
    				
    		},
    		error:function(){
    			alert("操作失败");
    			
    		}
    		
    	});
	});
	*/
	
	//返回
	$(".return-btn").click(function(){
		history.back();
	});
	
});