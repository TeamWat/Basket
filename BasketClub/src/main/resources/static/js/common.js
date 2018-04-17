$(function(){
//    $("#flash-message").fadeIn(1000).delay(5000).fadeOut(2000).queue(function(next) {
//      $(this).hide();
//      next();
//    });
});

function flashMessage($message, $result){
	swal("", $message, $result);
}


/*
$(function(){
    // 表示ボタンクリックでメッセージを表示
    $('#show').on('click', function() {    	
        $('.alert').fadeIn(1000).delay(2000).fadeOut(2000);
    });
    // メッセージ内の×ボタンクリックでメッセージを非表示にする
    $('.alert .close').on('click', function() {
        $(this).parents('.alert').hide();
    });
});
*/