/**
 * Created by caowei on 2015/11/10
 */
$(function(){
	//广告1频道列表change事件
	$("#channelId1").change(function(){
		//获取当前选中的频道
		var channelId = $(this).val();
		if(channelId == ""){
			return false;
		}
		//清空位置列表 
		$("#sort1>option").remove(); 
		var picNum = getPicNum(channelId);
		if(picNum < 1){
			return false;
		}
		var html = "<option value=''>请选择</option>";
		for(var i=1; i<=picNum; i++){
			html = html + "<option value=" + i + ">" + i + "</option>";
		}
		$("#sort1").html(html);
		var sort = $("#sort1").val();
		if(sort == ""){
			changeImg("img1", "");
		}else{
			var picPath = getPicPath(channelId, sort);
			changeImg("img1", picPath);
		}
	});
	
	//广告1位置列表change事件
	$("#sort1").change(function(){
		var channelId = $("#channelId1").val();
		var sort = $("#sort1").val();
		if(channelId == "" || sort == ""){
			changeImg("img1", "");
		}else{
			var picPath = getPicPath(channelId, sort);
			changeImg("img1", picPath);
		}
	});
	
	//广告2频道列表change事件
	$("#channelId2").change(function(){
		//获取当前选中的频道
		var channelId = $(this).val();
		if(channelId == ""){
			return false;
		}
		//清空位置列表 
		$("#sort2>option").remove(); 
		var picNum = getPicNum(channelId);
		if(picNum < 1){
			return false;
		}
		var html = "<option value=''>请选择</option>";
		for(var i=1; i<=picNum; i++){
			html = html + "<option value=" + i + ">" + i + "</option>";
		}
		$("#sort2").html(html);
		var sort = $("#sort2").val();
		if(sort == ""){
			changeImg("img2", "");
		}else{
			var picPath = getPicPath(channelId, sort);
			changeImg("img2", picPath);
		}
	});
	
	//广告2位置列表change事件
	$("#sort2").change(function(){
		var channelId = $("#channelId2").val();
		var sort = $("#sort2").val();
		if(channelId == "" || sort == ""){
			changeImg("img2", "");
		}else{
			var picPath = getPicPath(channelId, sort);
			changeImg("img2", picPath);
		}
	});
	
	function changeImg(imgId, picPath){
		if(picPath != ""){
			$("#" + imgId).attr("src", picPath);
		}else{
			$("#" + imgId).attr("src", "../image/image_uped.png");
		}
	}
	
	//查询所选频道的展示图片数
	function getPicNum(channelId){
		var picNum = 0;
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
    				picNum = data.attach;
    			}else{
    				showError("系统异常！");
    			}
    		},
    		error:function(){
    			showError("系统异常！");
    		}
    		
    	});
		return picNum;
	}
	
	//获取所选频道下的广告位的图片地址
	function getPicPath(channelId, sort){
		var picPath = "";
		$.ajax({
    		url:'../ad/getPicPath',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			channelId:channelId,
    			sort:sort
    		},
    		success:function(data){
    			if(data.code==1){  
    				picPath = data.attach;
    			}
    		},
    		error:function(){
    			showError("系统异常！");
    		}
    		
    	});
		return picPath;
	}
	
	//调换
	$("#save_btn").click(function(){
		var channelId1 = $("#channelId1").val();
		var sort1 = $("#sort1").val();
		var channelId2 = $("#channelId2").val();
		var sort2 = $("#sort2").val();
		if(channelId1 == ""){
			showError("请选择广告1的所属频道！");
	  		return false;
		}
		if(sort1 == ""){
			showError("请选择广告1的所在位置！");
	  		return false;
		}
		if(channelId2 == ""){
			showError("请选择广告2的所属频道！");
	  		return false;
		}
		if(sort2 == ""){
			showError("请选择广告2的所在位置！");
	  		return false;
		}
		var arr = new Array();
		var content1={};
    	content1.channelId=channelId1;
    	content1.sort=sort1;
    	arr.push(content1);
    	var content2={};
    	content2.channelId=channelId2;
    	content2.sort=sort2;
    	arr.push(content2);
    	if(content1.channelId == content2.channelId && content1.sort == content2.sort){
    		showError("广告1和广告2是同一个广告位，</br >无需调换！");
	  		return false;
    	}
    	$.ajax({
    		url:'../ad/exchangeAd',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			content:JSON.stringify(arr)
    		},
    		success:function(data){
    			if(data.code==1){
    				showSuccess("调换成功！");
    				window.location.href = "../ad/toAdListView";
    			} else {
    				showError("调换失败！");
    			}
    		},
    		error:function(){
    			showError("系统异常！");
    		}
    		
    	});
	});
	
	//返回
	$(".return-btn").click(function(){
		history.back();
	});
	
});