$(function(){
	
	// パスワード入力欄の入力制御設定
	passwordFieldCtrl();
	
	// パスワードチェックボックス押下	
	$('#passUpdCheck').on("click", function(){
		passwordFieldCtrl();
	});
	
	//　パスワード入力欄の入力制御設定
	function passwordFieldCtrl() {
		// チェックが入っていたら有効化
	    if ($('#passUpdCheck').prop('checked')){ 
	        // 入力フォームを有効化
	    	$('#password').prop("disabled", false);
			$('#repassword').prop("disabled", false);
	    } else { 
	        // 入力フォームを無効化
	    	$('#password').prop("disabled", true);
			$('#repassword').prop("disabled", true);
	    }
	}
	
	$('#backinput').on("click", function(){
		// 入力画面に戻る
		$userId = $('#userId').val();
	    window.location.href = '/user/edit/input/' + $userId;
	});
	
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
				 $('#userUpdateForm').submit();
			} else {
			      // キャンセルボタンを押した時の処理
//			      swal({
//			        text: "キャンセルしました",
//			        icon: "warning",
//			        buttons: false,
//			        timer: 2500 // 2.5秒後に自動的に閉じる
//			      });
			}
		});
	});
		
	$('#cancelbtn').on("click", function(){
		// 確認ダイアログを出して、変更をする
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
				$userId = $('#userId').val();
			    window.location.href = '/user/userDetail/' + $userId;
			}else{
				// キャンセルボタン押下時処理
				// 何もしない
			}
		});	
	});
	
	$('#deletelbtn').on("click", function(){
		// 確認ダイアログを出して、変更をする
		swal({
			  text: "ユーザーを削除してよろしいですか？",
			  type: "info",
			  showCancelButton: true,
			  confirmButtonText: 'OK',
		      allowOutsideClick: false
		}).then(function(isConfirm){
			if (isConfirm.value) {
				// OKボタンが押された時はユーザー情報の削除を行う				
				 $('#userdelete').submit();
			}else{
				// キャンセルボタン押下時処理
				// 何もせずダイアログを閉じる
			}
		});	
	});
	
});
	