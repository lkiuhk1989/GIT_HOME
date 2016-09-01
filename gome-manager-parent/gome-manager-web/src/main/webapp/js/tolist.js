$(function(){
	var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
	$(".content").css("height",wHeight-180);
})	


var maxInteractId = '0';
window.setInterval(queryinteract,1000);

var backTextG;
function queryinteract(){
		var meetId = $("#meetId").val();
    	$.ajax({
    		url:'../meetingInteract/queryOnWall',
    		type:'POST',
    		dataType:'json',
    		async:false,
    		data:{
    			meetId:meetId,
    			maxInteractId:maxInteractId
    		},
    		success:function(data){
    			if(data.code==1){
    				var length = data.attach.length;
    				for(var i=0;i<length;i++){
    					var perName = data.attach[i].perName;
    					var unitName = data.attach[i].unitName;
    					var content = data.attach[i].content;
    					var times = data.attach[i].times;
    					var floor = data.attach[i].floor;
    					maxInteractId = data.attach[i].maxInteractId;
    					var str = '<li id="li'+i+'"><span class="head"><img src="../image/user-photo.png" alt="" /></span> <div class="fl"> <h3><span class="dateTime"><u>'+times+'  '+floor+'楼</u></span><u>'+perName +'  --  '+unitName+'</u></h3></u> <p>'+content+'</p> </div> </li>';
    					//var ids = "li"+i;
    					//$("#"+ids).slideToggle(500);
    			        //$("#"+ids).hide();
    			        sleep(100);
    					$('#ulContent').append(str); 
    				}
    				$(".list li .fl p").css("color",backTextG);
					$(".list li .fl h3").css("color",backTextG);
    			}else{
    			}
    		},
    		error:function(){
    			alert("网络出问题啦！");
    		}
    	});
	}
	
	function sleep(numberMillis) {
	    var now = new Date();
	    var exitTime = now.getTime() + numberMillis;
	    while (true) {
	        now = new Date();
	        if (now.getTime() > exitTime)
	            return;
	    }
	}
	
	function add(){
		var str = '<li> <span class="head"><img src="../image/icon.png" alt="" /></span> <div class="fl"> <h3><span class="dateTime">2016-05-17 15:00:20  19楼</span>3188活动标题</h3> <p>欢迎大家来到微信体验版欢迎大家来到微信体验版欢迎大家来到大家来到微信体验版欢迎大家来到大家来到微信体验版欢迎大家来到大家来到微信体验版欢迎大家来到微信体验版</p> </div> </li>';
		$('#ulContent').before(str); 
	}
	
	function changePic(backPic){
		$(".wrap").css("background-image","url("+backPic+")");
	}
	function changeText(backText){
		backTextG = backText;
		$(".list li .fl p").css("color",backText);
		$(".list li .fl h3").css("color",backText);
	}
