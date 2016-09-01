$(function(){
	var meetId = $("#meetId").val();
	$("a[name=onWall]").unbind("click");
	$("a[name=onWall]").bind("click", function(){
			var id = $(this).attr("interactId");
			var iswallrun = $(this).attr("iswallrun");
			if(iswallrun=='1'){
				alert("已上墙！");
				return;
			}
	    	$.ajax({
	    		url:'../meetingInteract/onWall',
	    		type:'POST',
	    		dataType:'json',
	    		data:{
	    			id:id
	    		},
	    		success:function(data){
	    			if(data.code==1){
	    				$("#tishi").text("上墙成功");
						$("#mod-dialog").show();
				  		$("#mod-dialog-bg").show();
				  		$("#altbtn").unbind("click");
				  		$("#altbtn").bind("click",function(){
							 $("#mod-dialog").hide();
							 $("#mod-dialog-bg").hide();
							 window.location.href="../meetingInteract/toInteractView?id="+meetId;
						});
	    			}else{
	    				$("#tishi").text("上墙失败");
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

function deleteInteractResult(meetId){
	$.ajax({
		url:'../meetingInteract/deleteWall',
		type:'POST',
		dataType:'json',
		data:{
			meetId:meetId
		},
		success:function(data){
			if(data.code==1){
				$("#tishi").text("互动内容已清空");
				$("#mod-dialog").show();
		  		$("#mod-dialog-bg").show();
		  		$("#altbtn").unbind("click");
		  		$("#altbtn").bind("click",function(){
					 $("#mod-dialog").hide();
					 $("#mod-dialog-bg").hide();
					 window.location.href="../meetingInteract/toInteractView?id="+meetId;
				});
			}else{
				$("#tishi").text("互动内容已清空");
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

function exportInteractResult(meetId){    
    location.href="../meetingInteract/exportExcel?meetId="+meetId;    
}   
