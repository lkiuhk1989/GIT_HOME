/**
 * Created by caowei on 2015/11/09
 */
$(function(){
	//商品搜索
	$(".search_btn").unbind("click");
	$(".search_btn").bind("click", function(){
		var pName = $("#pName").val();
		var id = $("#rid").val();
		window.location.href = '../readProfessor/queryProfessorListView?id='+id+'&pName='+pName;
	});
});