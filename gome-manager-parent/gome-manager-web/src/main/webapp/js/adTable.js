/**
 * Created by caowei on 2015/10/31
 */
$(function(){
	//删除广告
	$("a[name=deleteAd]").unbind("click");
	$("a[name=deleteAd]").bind("click", function(){
		if(window.confirm("您确定要删除这个广告吗？")){
			var id = $(this).attr("adId");
	    	$.ajax({
	    		url:'../ad/deleteAd',
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
							 window.location.href="../ad/toAdListView";
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
		if(searchConditions == ""){
			var content = {};
			content.search=true;
			searchConditions = JSON.stringify(content);
		}
		$.ajax({
			url:'../ad/toAdListView',
			type:'POST',
			dataType:'html',
			data:{
				content:searchConditions,
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
