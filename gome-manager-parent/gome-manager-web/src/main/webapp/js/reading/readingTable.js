/**
 * Created by caowei on 2015/11/09
 */
$(function(){
	//删除商品
	$("a[name=deleteReading]").unbind("click");
	$("a[name=deleteReading]").bind("click", function(){
		if(window.confirm("您确定要删除这个文献导读信息吗？")){
			var id = $(this).attr("readingId");
	    	$.ajax({
	    		url:'../reading/deleteReading',
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
							 window.location.href="../reading/queryReadingView";
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
	
	//分页搜索
	$(".pageNumber").unbind("click");
	$(".pageNumber").bind("click", function(){
		var page = $(this).attr("pageNo");
		if(typeof(page) == "undefined"){
			page = null;
		}
		var searchConditions = $("#searchConditions").val();
		var title = $("#title").val();
		if(searchConditions == ""){
			var content = {};
			content.search=true;
			searchConditions = JSON.stringify(content);
		}
		$.ajax({
			url:'../reading/queryReadingView',
			type:'POST',
			dataType:'html',
			data:{
				title:title,
				page:page
			},
			success:function(data){
				$(".table-area").empty();
				$(".table-area").append(data);
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
