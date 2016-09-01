/**
 * Created by caowei on 2015/11/09
 */
//保存商品
$(function(){
	//返回
	$(".user_submit").click(function(){
		setInterval(function(){ifExist();}, 2000);  
	});
	//返回
	$(".return-btn").click(function(){
		window.location.href="../vedio/queryReadingView";
	});
});

function ifExist(){
	var iframe = $(window.frames['target'].document.body).html();
	if(iframe!=null && iframe!=""){
		window.location.href="../vedio/queryVedioView";
	}
}