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
    	content.code=$("#meetId").val();
    	content.unit=$("#unit").val();
    	content.office=$("#office").val();
    	content.jobs=$("#jobs").val();
    	content.position=$("#position").val();
    	content.phone=$("#phone").val();
    	content.operateUser=$("#operateUser").val();
    	$.ajax({
    		url:'../meetManager/addJoinPer',
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
						 window.location.href="../meetManager/toJoinPerView?id="+$("#meetId").val();
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
	$("a[name=deletejoinPer]").unbind("click");
	$("a[name=deletejoinPer]").bind("click", function(){
		if(window.confirm("您确定要删除这个人员信息吗？")){
			var id = $(this).attr("joinPerId");
	    	$.ajax({
	    		url:'../meetManager/delJoinPer',
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
							 window.location.href="../meetManager/toJoinPerView?id="+meetId;
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

/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//保存
	$("#query_btn").click(function(){
    	var signTime=$("#signTime").val();
    	if(signTime=="" || signTime == ''){
    		alert("签到时间不能为空！");
    		return false;
    	}
    	var code=$("#meetId").val();
		window.location.href="../meetManager/queryPer?signTime="+signTime+"&code="+code;
	});
});

//保存商品
$(function(){
	//保存
	$("#export_btn").click(function(){
    	var signTime=$("#signTime").val();
    	if(signTime=="" || signTime == ''){
    		alert("签到时间不能为空！");
    		return false;
    	}
    	var code=$("#meetId").val();
		window.location.href="../meetManager/exportPer?signTime="+signTime+"&code="+code;
	});
});