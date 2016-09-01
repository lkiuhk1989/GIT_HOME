
(function(){
$("#danmu").danmu({
	// left:$("#danmuarea").offset().left,
	// top:$("#danmuarea").offset().top,
	// height: 445,
 //    width: 800,
	left:0,
	top:0,
	height:"100%",
	width:"100%",
    speed:30000,
    opacity:1,
    font_size_small:16,
    font_size_big:24,
      top_botton_danmu_time:6000
}
  );
})(jQuery);


query();
timedCount();


var first=true;

function timedCount()
{
   $("#time").text($('#danmu').data("nowtime"));
 
t=setTimeout("timedCount()",50)

}



function starter(){

  $('#danmu').danmu('danmu_start');

}
function pauser(){

  $('#danmu').danmu('danmu_pause');
}
function resumer(){

  $('#danmu').danmu('danmu_resume');
}
function stoper(){
  $('#danmu').danmu('danmu_stop');
}

function getime(){
  alert($('#danmu').data("nowtime"));
}

function getpaused(){
  alert($('#danmu').data("paused"));
}

function add(){
var newd=
{ "text":"new2" , "color":"green" ,"size":"1","position":"0","time":60};

  $('#danmu').danmu("add_danmu",newd);
}
function insert(){
  var newd= { "text":"new2" , "color":"green" ,"size":"1","position":"0","time":50};
  str_newd=JSON.stringify(newd);
  $.post("stone.php",{danmu:str_newd},function(data,status){alert(data)});
}
function query() {
  $.get("query.php",function(data,status){
     var danmu_from_sql=eval(data);   
     for (var i=0;i<danmu_from_sql.length;i++){
      var danmu_ls=eval('('+danmu_from_sql[i]+')');
      $('#danmu').danmu("add_danmu",danmu_ls);
     }
  });
}

function send(objStr){
  var text = objStr;
  var color = document.getElementById('color').value;
  var position = document.getElementById('position').value;
  var time = $('#danmu').data("nowtime")+5;
  var size =document.getElementById('text_size').value;
  var text_obj='{ "text":"'+text+'","color":"'+color+'","size":"'+size+'","position":"'+position+'","time":'+time+'}';
  $.post("stone.php",{danmu:text_obj});
  var text_obj='{ "text":"'+text+'","color":"'+color+'","size":"'+size+'","position":"'+position+'","time":'+time+',"isnew":""}';
  var new_obj=eval('('+text_obj+')');
  $('#danmu').danmu("add_danmu",new_obj);
  document.getElementById('text').value='';
}

function op(){
var op=document.getElementById('op').value;
 $('#danmu').data("opacity",op);
}


function changehide() {
	var op = document.getElementById('op').value;
	op = op / 100;
	if (document.getElementById("ishide").checked) {
		jQuery('#danmu').data("opacity", op);
		jQuery(".flying").css({
			"opacity": op
		});
	} else {
		jQuery('#danmu').data("opacity", 0);
		jQuery(".flying").css({
			"opacity": 0
		});
	}
}


function settime(){
	var t=document.getElementById("set_time").value;
	t=parseInt(t)
	console.log(t)
	$('#danmu').data("nowtime",t);
}
	