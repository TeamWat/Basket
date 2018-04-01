package jp.wat.basket.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Util {

	public Integer getNendo(){
		
		// 本日日付から nendoを取得
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter df1 = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy");
		Integer month = Integer.valueOf(df1.format(today));
		Integer year = Integer.valueOf(df2.format(today));
		Integer nendo = month <= 3? year-1 : year;
		return nendo;
	}
	
}
