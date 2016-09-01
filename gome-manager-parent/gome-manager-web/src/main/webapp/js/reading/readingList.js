/**
 * Created by caowei on 2015/11/09
 */
$(function(){
	//商品搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var title = $("#title").val();
		window.location.href = '../reading/queryReadingView?title='+title;
	});
	
	//导出
	$(".export-btn").click(function(){
		var searchConditions = $("#searchConditions").val();
		window.location.href = '../goods/exportExcel?conditions='+searchConditions;
	});
	
	//回车搜索事件
	$("#goodsName").bind("keydown",function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {    
	        //回车执行查询
	        $(".search_btn").click();
	     }    
	});
});

function changeSort(textId,id,code){
	var content={};
	content.id=id;
	content.sortNum=$(textId).val();
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
					 window.location.href="../professor/queryMeetProfessorListView?id="+code;
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
}