/**
 * Created by caowei on 2015/11/09
 */
	$(function(){
		//保存
		$("#save_btn").click(function(){
			if(checkSubmit()){
				var content={};
				content.id=$("#goodsCategoryId").val();
				content.categoryName=$("#categoryName").val();
		    	content.status=$("input[name='status']:checked").val();
		    	content.operateUser=$("#operateUser").val();
		    	$.ajax({
		    		url:'../goodsCategory/editGoodsCategory',
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
					  		$("#altbtn").click(function(){
								 $("#mod-dialog").hide();
								 $("#mod-dialog-bg").hide();
								 window.location.href="../goodsCategory/toGoodsCategoryListView";
							});
		    			}else{
		    				$("#tishi").text("修改失败");
							$("#mod-dialog").show();
					  		$("#mod-dialog-bg").show();
					  		$("#altbtn").click(function(){
								 $("#mod-dialog").hide();
								 $("#mod-dialog-bg").hide();
							});
		    			}
		    				
		    		},
		    		error:function(){
		    			$("#tishi").text("系统异常");
						$("#mod-dialog").show();
				  		$("#mod-dialog-bg").show();
				  		$("#altbtn").click(function(){
							 $("#mod-dialog").hide();
							 $("#mod-dialog-bg").hide();
						});
		    		}
		    		
		    	});
			} else {
				return false;
			}
			
		});
		
		//检查是否可以提交
		function checkSubmit(){
			if(!checkCategoryName()){
				return false;
			}
			
			//清除所有错误信息
			$("span[id$=ErrorSpan]").html("");
			return true;
		}
		
		//检查商品分类名称
		function checkCategoryName(){
			if(check.checkNull("categoryName")){
				check.showErrorMessage("categoryName", "请输入商品类别名称！");
				return false;
			}
			if($("#categoryName").val().length < 4){
				check.showErrorMessage("categoryName", "名称太短！");
				return false;
			}
			if($("#categoryName").val().length > 16){
				check.showErrorMessage("categoryName", "名称太长！");
				return false;
			}
			if (!check.checkFormat(check.nameReg, "categoryName")) {
				check.showErrorMessage("categoryName", '只能包含汉字、数字、字母、下划线并且不能以下划线开头和结尾，长度为4-16位！');
				return false;
			}
			if(checkCategoryNameUsed()){
				return false;
			}
			check.clearErrorMessage("categoryName");
			return true;
		}
		
		//检查商品分类名称是否已被占用
		function checkCategoryNameUsed(){
			var usedFlag = false;
			var categoryId = $("#goodsCategoryId").val();
			var categoryName = $("#categoryName").val();
	    	$.ajax({
	    		url:'../goodsCategory/checkIsUsed',
	    		type:'POST',
	    		dataType:'json',
	    		async:false,
	    		data:{
	    			id:categoryId,
	    			categoryName:categoryName
	    		},
	    		success:function(data){
    				if(data.code == 5){    //名称已被占用
    					check.showErrorMessage("categoryName", "该名称已经被占用！");
    					usedFlag = true;
    				} else if(data.code == 6){  //名称未被占用
    					check.clearErrorMessage("categoryName");
    					usedFlag = false;
    				} else {
    					$("#tishi").text("系统异常");
    					$("#mod-dialog").show();
    			  		$("#mod-dialog-bg").show();
    			  		$("#altbtn").click(function(){
							 $("#mod-dialog").hide();
							 $("#mod-dialog-bg").hide();
						});
    			  		usedFlag = false;
    				}
	    				
	    		},
	    		error:function(){
	    			$("#tishi").text("系统异常");
					$("#mod-dialog").show();
			  		$("#mod-dialog-bg").show();
			  		$("#altbtn").click(function(){
						 $("#mod-dialog").hide();
						 $("#mod-dialog-bg").hide();
					});
			  		usedFlag = false;
	    		}
	    		
	    	});
	    	return usedFlag;
		}
		
		//即时检查商品分类名称
		$("#categoryName").blur(function(){
			if(!checkCategoryName()){
				return false;
			}
	    	
		});
		
		//返回
		$(".return-btn").click(function(){
			history.back();
		});
	
});
