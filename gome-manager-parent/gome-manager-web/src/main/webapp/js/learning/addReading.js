/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	var yearValue = $("#yearValue1").val();
	var catname = $("#catname1").val();
	$("#yearValue").find("option[value='"+yearValue+"']").attr("selected",true);
	$("#catname").find("option[value='catname']").attr("selected",true);
	//返回
	$(".user_submit").click(function(){
		setInterval(function(){ifExist();}, 2000);  
	});
	//返回
	$(".return-btn").click(function(){
		history.back();
	});
});

function ifExist(){
	var iframe = $(window.frames['target'].document.body).html();
	if(iframe!=null && iframe!=""){
		window.location.href="../learning/queryReadingView";
	}
}