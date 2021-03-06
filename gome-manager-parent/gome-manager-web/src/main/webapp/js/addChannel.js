/**
 * Created by caowei on 2015/10/29
 */

	$(function(){
		//保存
		$("#save_btn").click(function(){
			if(checkSubmit()){
				var content={};
		    	content.channelName=$("#channelName").val();
		    	content.picNum=$("#picNum").val();
		    	content.status=$("input[name='status']:checked").val();
		    	content.operateUser=$("#operateUser").val();
		    	$.ajax({
		    		url:'../channel/addChannel',
		    		type:'POST',
		    		dataType:'json',
		    		async:false,
		    		data:{
		    			content:JSON.stringify(content)
		    		},
		    		success:function(data){
		    			if(data.code==1){
		    				$("#tishi").text("添加成功");
							$("#mod-dialog").show();
					  		$("#mod-dialog-bg").show();
					  		$("#altbtn").click(function(){
								 $("#mod-dialog").hide();
								 $("#mod-dialog-bg").hide();
								 window.location.href="../channel/toChannelListView";
							});
		    			}else{
		    				$("#tishi").text("添加失败");
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
			if(!checkChannelName()){
				return false;
			}
			
			//清除所有错误信息
			$("span[id$=ErrorSpan]").html("");
			return true;
		}
		
		//检查频道名称
		function checkChannelName(){
			if(check.checkNull("channelName")){
				check.showErrorMessage("channelName", "请输入频道名称！");
				return false;
			}
			if($("#channelName").val().length < 4){
				check.showErrorMessage("channelName", "名称太短！");
				return false;
			}
			if($("#channelName").val().length > 16){
				check.showErrorMessage("channelName", "名称太长！");
				return false;
			}
			if (!check.checkFormat(check.nameReg, "channelName")) {
				check.showErrorMessage("channelName", '只能包含汉字、数字、字母、下划线并且不能以下划线开头和结尾，长度为4-16位！');
				return false;
			}
			if(checkChannelNameUsed()){
				return false;
			}
			check.clearErrorMessage("channelName");
			return true;
		}
		
		//检查频道名称是否已被占用
		function checkChannelNameUsed(){
			var usedFlag = false;
			var channelName = $("#channelName").val();
	    	$.ajax({
	    		url:'../channel/checkIsUsed',
	    		type:'POST',
	    		dataType:'json',
	    		async:false,
	    		data:{
	    			channelName:channelName
	    		},
	    		success:function(data){
    				if(data.code == 5){     //名称已被占用
    					check.showErrorMessage("channelName", "该名称已经被占用！");
    					usedFlag = true;
    				} else if(data.code == 6){     //名称未被占用
    					check.clearErrorMessage("channelName");
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
		
		//即时检查频道名称
		$("#channelName").blur(function(){
			if(!checkChannelName()){
				return false;
			}
	    	
		});
		
		//返回
		$(".return-btn").click(function(){
			history.back();
		});
	
});
