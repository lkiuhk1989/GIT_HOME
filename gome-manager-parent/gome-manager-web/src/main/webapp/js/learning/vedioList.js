/**
 * Created by caowei on 2015/11/09
 */
$(function(){
	//商品搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var title = $("#title").val();
		window.location.href = '../vedio/queryVedioView?title='+title;
	});
	
	//导出
	
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

