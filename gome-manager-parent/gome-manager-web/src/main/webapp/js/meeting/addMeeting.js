/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//保存
	$("#save_btn").click(function(){
		var content={};
    	content.name=$("#name").val();
    	content.code=$("#code").val();
    	content.theme=$("#theme").val();
    	content.meetAddr=$("#meetAddr").val();
    	content.beginTime=$("#beginTime").val();
    	content.endTime=$("#endTime").val();
    	content.picPath=$("#picPath").val();
    	content.letterContent=$("#letterContent").val();
    	content.operateUser=$("#operateUser").val();
    	content.status=$("input[name='status']:checked").val();
    	$.ajax({
    		url:'../meeting/addMeeting',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			content:JSON.stringify(content)
    		},
    		success:function(data){
    			if(data.code==1){
    				$("#tishi").text("添加成功");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		$("#altbtn").unbind("click");
			  		$("#altbtn").bind("click",function(){
						 $("#mod-dialog").hide();
						 $("#mod-dialog-bg").hide();
						 window.location.href="../meeting/queryMeetListView";
					});
    			}else{
    				$("#tishi").text("添加失败");
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
	
	//返回
	$(".return-btn").click(function(){
		history.back();
	});
	
});