/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//保存
	$("#save_btn").click(function(){
		var content={};
		content.stageId=$("#stageId").val();
    	content.code=$("#meetId").val();
    	content.stage=$("#stage").val();
    	$.ajax({
    		url:'../meetingSchedule/addScheduleStage',
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
						 window.location.href="../meetingSchedule/toScheduleStageView?id="+$("#meetId").val();
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
		var meetId = $("#meetId").val();
		//删除商品
		$("a[name=deleteStage]").unbind("click");
		$("a[name=deleteStage]").bind("click", function(){
			if(window.confirm("您确定要删除吗？")){
				var id = $(this).attr("stageId");
		    	$.ajax({
		    		url:'../meetingSchedule/delStage',
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
								 window.location.href="../meetingSchedule/toScheduleStageView?id="+meetId;
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