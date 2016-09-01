/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//保存
	$("#save_btn").click(function(){
		var content={};
		content.id=$("#id").val();
		content.code=$("#meetId").val();
    	content.stageId=$("#stageId").val();
    	content.beginTimes=$("#beginTimes").val();
    	content.endTimes=$("#endTimes").val();
    	content.content=$("#content").val();
    	content.operateUser=$("#operateUser").val();
    	$.ajax({
    		url:'../meetingSchedule/addSchedule',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			content:JSON.stringify(content)
    		},
    		success:function(data){
    			if(data.code==1){
    				$("#tishi").text("新增成功");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		$("#altbtn").unbind("click");
			  		$("#altbtn").bind("click",function(){
						 $("#mod-dialog").hide();
						 $("#mod-dialog-bg").hide();
						 window.location.href="../meetingSchedule/toScheduleView?id="+$("#meetId").val();
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
	
	//返回
	$(".return-btn").click(function(){
		history.back();
	});
	
});


$(function(){
	//删除商品
	var meetId = $("#meetId").val();
	$("a[name=deleteSchedule]").unbind("click");
	$("a[name=deleteSchedule]").bind("click", function(){
		if(window.confirm("您确定要删除这个日程信息吗？")){
			var id = $(this).attr("scheduleId");
	    	$.ajax({
	    		url:'../meetingSchedule/delSchedule',
	    		type:'POST',
	    		dataType:'json',
	    		data:{
	    			id:id
	    		},
	    		success:function(data){
	    			if(data.code==1){
	    				$("#tishi").text("删除成功");
						$("#mod-dialog").show();
				  		$("#mod-dialog-bg").show();
				  		$("#altbtn").unbind("click");
				  		$("#altbtn").bind("click",function(){
							 $("#mod-dialog").hide();
							 $("#mod-dialog-bg").hide();
							 window.location.href="../meetingSchedule/toScheduleView?id="+meetId;
						});
	    			}else{
	    				$("#tishi").text("删除失败");
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
		}
	});
});

