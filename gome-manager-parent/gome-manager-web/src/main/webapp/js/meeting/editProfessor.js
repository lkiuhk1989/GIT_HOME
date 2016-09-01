/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//保存
	$("#save_btn").click(function(){
		var content={};
		content.id=$("#id").val();
		content.name=$("#name").val();
    	content.code=$("#code").val();
    	content.unit=$("#unit").val();
    	content.office=$("#office").val();
    	content.jobs=$("#jobs").val();
    	content.sortNum=$("#sortNum").val();
    	content.picUrl=$("#picPath").val();
    	$.ajax({
    		url:'../professor/editMeetingProfessor',
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
						 window.location.href="../professor/queryMeetProfessorListView?id="+$("#code").val();
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
	//保存
	$("#add_btn").click(function(){
		var meetId=$("#meetId").val();
		var content={};
		content.professorId=$("#id").val();
		content.recode=$("#recode").val();
		content.id=$("#resumeId").val();
    	$.ajax({
    		url:'../professor/addMeetingProfessorRecode',
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
						 window.location.href="../professor/toEditMeetingProfessorView?id="+$("#id").val()+"&meetId="+meetId;
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
});	
	
	$(function(){
		var meetId = $("#meetId").val();
		//删除商品
		$("a[name=deleteResume]").unbind("click");
		$("a[name=deleteResume]").bind("click", function(){
			if(window.confirm("您确定要删除吗？")){
				var id = $(this).attr("professorId");
		    	$.ajax({
		    		url:'../professor/delResume',
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
								 window.location.href="../professor/toEditMeetingProfessorView?id="+$("#id").val()+"&meetId="+meetId;
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