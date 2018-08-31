$(function(){
	$('#passUpdCheck').on("click", function(){
		// チェックが入っていたら有効化
	    if ($(this).prop('checked')){ 
	        // 入力フォームを有効化
	    	$('#password').prop("disabled", false);
			$('#repassword').prop("disabled", false);
	    } else { 
	        // 入力フォームを無効化
	    	$('#password').prop("disabled", true);
			$('#repassword').prop("disabled", true);
	    }
	});
	
	$('#backinput').on("click", function(){
		// 入力画面に戻る
		$userId = $('#userId').val();
	    window.location.href = '/user/edit/input/' + $userId;
	});
	
});
	