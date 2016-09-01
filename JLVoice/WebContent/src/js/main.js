var meet = {
	init:function(){
		var _t = this;
		_t.config = meet_config;
		var _openId = _t.getHrefParam('openId');
		$.ajax({
			type: 'get',
			url: _t.config.url.main,
			data: {openId:_openId},
			dataType: 'json',
			timeout: 300,
			success: function(data){
				if(data.code=='1'){
					window.open('meet-index.html?meetId='+data.attach.code+'&userId='+data.attach.id+'&userName='+data.attach.name,'_self');
				}else{
					window.open('meet-signcode.html?openId='+_openId,'_self');
				}
			},
			error: function(){
			}
		});
	},
	getHrefParam:function(_name){
		var _value = '';
		if(window.location.href.split('?')[1]){
			var _paramArr = window.location.href.split('?')[1].split('&');
			$(_paramArr).each(function(_index,_element){
				var _param = _element.split('=');
				if(_param[0]==_name){
					_value = _param[1];
				}
			});
		}
		
		return _value;
	},
}
meet.init();
