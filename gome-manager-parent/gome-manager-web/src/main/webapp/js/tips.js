	function showSuccess(message){
		$("#tishi").html(message);
		$("#mod-dialog").fadeIn().delay(1000).fadeOut();
	}
	
	function showError(message){
		$("#tishi").html("<font color=red>" + message + "</font>");
		$("#mod-dialog").fadeIn().delay(1000).fadeOut();
	}
