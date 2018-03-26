
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
	
	$.ajax({
//		type:"POST",
//		url: '/schedule/edit/complete',
		url: $form.attr("action"),
		type: $form.attr("method"),
		data: $form.serialize(), 
		success: function(result, textStatus, xhr) {
			//alert("成功");
		},
		error: function(xhr, textStatus, error) {
		    alert("NG");
		}
	});
}


