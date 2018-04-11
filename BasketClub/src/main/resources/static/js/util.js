function getDayOfWeek(ymd) { //  ymdの形式： yyyy/mm/dd
	
	var strDate = ymd + ' ' + '00:00:00'
	var date = new Date(strDate);
	var dayOfWeek = date.getDay() ;	// 曜日(数値)
	var dayOfWeekStr = [ "日", "月", "火", "水", "木", "金", "土" ][dayOfWeek] ;

	return dayOfWeekStr;
}
