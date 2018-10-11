$(function(){
	
	$('#submitbtn').on("click", function(){
		// 確認ダイアログを出して、変更をする
				
		swal({
			  text: "入力した内容でデータを更新してよいですか？",
			  type: "info",
			  showCancelButton: true,
			  confirmButtonText: 'OK',
		      allowOutsideClick: false
		}).then(function(isConfirm){
			if (isConfirm.value) {
			      // OKボタンが押された時はユーザー情報の更新をおこなう
				 $('#memberUpdateForm').submit();
			} else {
			      // キャンセルボタンを押した時の処理
			}
		});
	});
		
	$('#cancelbtn').on("click", function(){
		// 確認ダイアログを出して、編集内容を破棄してよいか確認する
		swal({
			  text: "編集内容をクリアして前画面に戻りますがよいですか？",
			  type: "info",
			  showCancelButton: true,
			  confirmButtonText: 'OK',
		      allowOutsideClick: false
		}).then(function(isConfirm){
			if (isConfirm.value) {
				// OKボタン押下時処理
				// 変更後ユーザー詳細画面に遷移する
				$memberId = $('#memberId').val();
			    window.location.href = '/member/memberDetail/' + $memberId;
			}else{
				// キャンセルボタン押下時処理
			}
		});	
	});
	
	$('#deletelbtn').on("click", function(){
		// 確認ダイアログを出して、削除してよいか確認する
		swal({
			  text: "ユーザーを削除してよろしいですか？",
			  type: "info",
			  showCancelButton: true,
			  confirmButtonText: 'OK',
		      allowOutsideClick: false
		}).then(function(isConfirm){
			if (isConfirm.value) {
				// OKボタンが押された時はユーザー情報の削除を行う				
				 $('#memberdelete').submit();
			}else{
				// キャンセルボタン押下時処理
				// 何もせずダイアログを閉じる
			}
		});	
	});
	
});
	