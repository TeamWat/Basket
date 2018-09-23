package jp.wat.basket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
//public class ExceptionHandler {
//	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
////    @org.springframework.web.bind.annotation.ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
//    @ResponseBody
//    public Map<String, Object> handleError() {
//        Map<String, Object> errorMap = new HashMap<String, Object>();
//        errorMap.put("message", "許可されていないメソッド");
//        errorMap.put("status", HttpStatus.METHOD_NOT_ALLOWED);
//        return errorMap;
//    }
//	
//	// https://qiita.com/NagaokaKenichi/items/2f199134a881a776b717
//	// https://www.beeete2.com/blog/?p=612
//}

@ControllerAdvice
public class ExceptionHandler {
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
//    @org.springframework.web.bind.annotation.ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public String handleError() {
        
		Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put("message", "許可されていないメソッド");
        errorMap.put("status", HttpStatus.METHOD_NOT_ALLOWED);
        
        return "error.html";
    }
	
	// https://qiita.com/NagaokaKenichi/items/2f199134a881a776b717
	// https://www.beeete2.com/blog/?p=612
}
