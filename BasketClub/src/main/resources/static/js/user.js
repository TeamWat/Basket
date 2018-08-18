$(function(){

	// ユーザーに紐づくメンバーのON/OFFを設定する
	// TODO コードが重複しているため、clickイベントを押すように変更する
	$userId = $('#userId').attr("name");
	$nendo = Number($('#myTabContent > .active').attr("id").slice(3));	//nendo情報は[nendo-YYYY] 
	getUserMember($userId, $nendo);
	
	
	$('.nendoSetting').on("click", function(){
		
//			if ($(this).hasClass("btn-selected")){
//				removeUserNendo(this);
//			} else {
//				addUserNendo(this);
//				getPermissionMember(this);
//			}
		$userId = $('#userId').text();
		$nendo = Number($(this).attr("id").substr(6,4)); 
		
		window.location.href = '/user/usersMember?uid=' + $userId + '&nendo=' + $nendo;
			
	});
	
	// クリックしたメンバーをユーザーに紐づける／はずす（トグル）
	$('.memberSetting').on("click", function(){
		
		$userId = $('#userId').attr("name");
		$nendo = Number($('#myTabContent > .active').attr("id").slice(3));	//nendo情報は[nendo-YYYY]
		$memberId = Number($(this).attr("id").slice(7)); 	//member情報は[member-9999]
	
		if ($(this).hasClass("btn-selected")){
			removeUserMember(this, $userId, $nendo, $memberId);
		} else {
			addUserMember(this, $userId, $nendo, $memberId);
		}	
	});
	
	// ユーザーに紐づくメンバーのON/OFFを設定する
	$('.nav-link').on("click", function(){
		$userId = $('#userId').attr("name");
		$nendo = Number($('#myTabContent > .active').attr("id").slice(3));	//nendo情報は[nendo-YYYY] 
		getUserMember($userId, $nendo);
	});
	
});
	
/**
 * ユーザーがアクセス可能な年度を設定する
 * 
 * @param $_this
 */
function addUserNendo($_this) {

		// CSRFトークンの設定
		 var token = $("meta[name='_csrf']").attr("content");
		 var header = $("meta[name='_csrf_header']").attr("content"); 
		 $(document).ajaxSend(function(e, xhr, options) {
		     xhr.setRequestHeader(header, token);
		 });
		
		// 更新情報の取得
		var $userId = $('#userId').text();
		var $nendo = Number($($_this).attr("id").substr(6,4));  //nendo情報は[nendo-YYYY]
	
		var $upddata = {userId:$userId, nendo:$nendo};
		var $upddatajson = JSON.stringify($upddata);
		
	    // ユーザーの年度設定更新
		$.ajax({
	        type: 'POST',
	        url: '/user/addUserNendo',
	        //dataType : "json",  //APIの実行後にステータスコードだけ返してレスポンスボディが空のものはエラー扱いになるらしい。この指定をとればうまくいく（https://teratail.com/questions/33996）
	        contentType: 'application/json',
	        data: $upddatajson, 
	    })
		.done(function(json,status,xhr) {
			console.log("成功");
			$($_this).removeClass("btn-light");
			$($_this).addClass("btn-selected");
			
	    }).fail(function(xhr, textStatus, error) {
	    	console.log("XMLHttpRequest : " + xhr.status);
	        console.log("textStatus     : " + textStatus);
	        console.log("error    : " + error);
	   });
}

/**
 * ユーザーがアクセス可能な年度をはずす
 * 
 * @param $_this
 */
function removeUserNendo($_this) {

	// CSRFトークンの設定
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content"); 
	 $(document).ajaxSend(function(e, xhr, options) {
	     xhr.setRequestHeader(header, token);
	 });
	
	// 更新情報の取得
	var $userId = $('#userId').text();
	var $nendo = Number($($_this).attr("id").substr(6,4));  //nendo情報は[nendo-YYYY]

	var $remove = {userId:$userId, nendo:$nendo};
	var $rmdatajson = JSON.stringify($remove);
	
    // ユーザーの年度設定更新
	$.ajax({
        type: 'POST',
        url: '/user/removeUserNendo',
        //dataType : "json",  //APIの実行後にステータスコードだけ返してレスポンスボディが空のものはエラー扱いになるらしい。この指定をとればうまくいく（https://teratail.com/questions/33996）
        contentType: 'application/json',
        data: $rmdatajson, 
    })
	.done(function(json,status,xhr) {
		console.log("成功");
		$($_this).addClass("btn-light");
		$($_this).removeClass("btn-selected");
    }).fail(function(xhr, textStatus, error) {
    	console.log("XMLHttpRequest : " + xhr.status);
        console.log("textStatus     : " + textStatus);
        console.log("error    : " + error);
   });
}

function getPermissionMember($_this) {

	var $nendo = Number($($_this).attr("id").substr(6,4));  //nendo情報は[nendo-YYYY]
	var $data = {nendo:$nendo};
	var $datajson = JSON.stringify($data);
	
	// ユーザーの年度設定更新
	$.ajax({
        type: 'POST',
        url: '/user/getPermissionMember',
        //dataType : "json",  //APIの実行後にステータスコードだけ返してレスポンスボディが空のものはエラー扱いになるらしい。この指定をとればうまくいく（https://teratail.com/questions/33996）
        contentType: 'application/json',
        data: $datajson, 
    })
	.done(function(data) {
		console.log("成功");
		console.log(data);
		$('#usermember').empty();
		$.each(data, function(i, val) {
			console.log(i + ': ' + val);
			$('#usermember'+'-'+$nendo).append(
					$('<button type="button" class="btn-custom badge-pill btn-light" id="memberId-'+ val['memberId'] + '">').text(val['memberName']) 
			);
		});
		 
    }).fail(function(xhr, textStatus, error) {
    	console.log("XMLHttpRequest : " + xhr.status);
        console.log("textStatus     : " + textStatus);
        console.log("error    : " + error);
   });
}


/**
 * ユーザーが参照可能なメンバーを追加する
 * 
 * @param $_this
 */
function addUserMember($_this, $userId, $nendo, $memberId) {

	// CSRFトークンの設定
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content"); 
	 $(document).ajaxSend(function(e, xhr, options) {
	     xhr.setRequestHeader(header, token);
	 });

	var $upddata = {userId:$userId, nendo:$nendo, memberId:$memberId};
	var $upddatajson = JSON.stringify($upddata);
	
    // ユーザーの年度設定更新
	$.ajax({
        type: 'POST',
        url: '/user/addUserMember',
        //dataType : "json",  //APIの実行後にステータスコードだけ返してレスポンスボディが空のものはエラー扱いになるらしい。この指定をとればうまくいく（https://teratail.com/questions/33996）
        contentType: 'application/json',
        data: $upddatajson, 
    })
	.done(function(json,status,xhr) {
		console.log("成功");
		$($_this).removeClass("btn-light");
		$($_this).addClass("btn-selected");
		
    }).fail(function(xhr, textStatus, error) {
    	console.log("XMLHttpRequest : " + xhr.status);
        console.log("textStatus     : " + textStatus);
        console.log("error    : " + error);
   });
}


/**
 * ユーザーが参照可能なメンバーを削除する
 * 
 * @param $_this
 */
function removeUserMember($_this, $userId, $nendo, $memberId) {

	// CSRFトークンの設定
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content"); 
	 $(document).ajaxSend(function(e, xhr, options) {
	     xhr.setRequestHeader(header, token);
	 });

	var $upddata = {userId:$userId, nendo:$nendo, memberId:$memberId};
	var $upddatajson = JSON.stringify($upddata);
	
	console.log($upddata);
	console.log($upddatajson);
	
    // ユーザーの年度設定更新
	$.ajax({
        type: 'POST',
        url: '/user/removeUserMember',
        //dataType : "json",  //APIの実行後にステータスコードだけ返してレスポンスボディが空のものはエラー扱いになるらしい。この指定をとればうまくいく（https://teratail.com/questions/33996）
        contentType: 'application/json',
        data: $upddatajson, 
    })
	.done(function(json,status,xhr) {
		console.log("成功");
		$($_this).removeClass("btn-selected");
		$($_this).addClass("btn-light");
		
    }).fail(function(xhr, textStatus, error) {
    	console.log("XMLHttpRequest : " + xhr.status);
        console.log("textStatus     : " + textStatus);
        console.log("error    : " + error);
   });

}

/**
 * ユーザーが参照可能なメンバーのON/OFFを設定する
 * 
 * @param $_this
 */
function getUserMember($userId, $nendo) {
	
	$url = '/user/getUserMember?uid=' + $userId + '&nendo=' + $nendo;

    // ユーザーに紐づくメンバーIDのリストを取得する
	$.ajax({
        type: 'GET',
        url: $url,
        contentType: 'application/json',
    })
	.done(function(data, textStatus, jqXHR) {
		
		// 取得したメンバーを選択状態にする
		$.each(data, function(i, val) {
			$('#member-'+ val).addClass("btn-selected");
			$('#member-'+ val).removeClass("btn-light");
		});
		
    }).fail(function(xhr, textStatus, error) {
    	console.log("XMLHttpRequest : " + xhr.status);
        console.log("textStatus     : " + textStatus);
        console.log("error    : " + error);
   });
}



