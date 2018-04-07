
// 同期更新
//function asyncUpd(index) {
//	var formname = "form" + index;
//	
//	//js sample
//	//document.form0.submit(); //name指定
//	//jquery sample
//	//$('form[name="form0"]').submit(); //name指定
//	
//	$('#' + formname ).submit(); //id指定
//	//$('form[name=' + formname + ']').submit(); //name指定
//	
//}

//非同期更新
function asyncUpd(index) {
	
	var formname = "form" + index;
	var $form = $('#' + formname )
	
	return $.ajax({
        type: 'POST',
        url: $form.attr("action"),
        dataType : "json",
        data: $form.serialize(), 
    })
}

function asyncDel(formNo) {
	
	var formname = "form" + formNo;
	var $form = $('#' + formname )
	
	return $.ajax({
		type: 'POST',
		url: '/schedule/edit/delete',
		data: $form.serialize(), 
	})
}
