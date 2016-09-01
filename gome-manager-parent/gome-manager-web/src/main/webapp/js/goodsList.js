/**
 * Created by caowei on 2015/11/09
 */
$(function(){
	//商品搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var content = {};
		var name = $("#name").val();
		if(name != "" && name != null){
			content.name=name;
		}
		content.search=true;
		
		$.ajax({
			url:'../meeting/toMeetingListView',
			type:'POST',
			dataType:'html',
			data:{
				content:JSON.stringify(content)
			},
			success:function(data){
				$(".table-area").empty();
				$(".table-area").append(data);
				$("#searchConditions").val(JSON.stringify(content));
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