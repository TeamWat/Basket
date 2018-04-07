package jp.wat.basket.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jp.wat.basket.service.ScheduleService;
import jp.wat.basket.common.Util;
import jp.wat.basket.entity.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {
	   
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	Util util;

	@RequestMapping(value="/schedule", method=RequestMethod.GET)
	public String index(Model model, @RequestParam(value = "month", required = false) Integer month){
		
		// TODO 年度の初期設定はログイン時にセッションに格納するよう変更
		Integer nendo = util.getNendo();
		if(month == null){
			// 月のパラメータが設定されていない場合は当月を初期設定
			LocalDateTime today = LocalDateTime.now();
			DateTimeFormatter df1 = DateTimeFormatter.ofPattern("MM");
			month = Integer.valueOf(df1.format(today));
		}

		List<Schedule> scheduleList= scheduleService.getScheduleData(nendo, month);

		model.addAttribute("scheduleList", scheduleList);
		model.addAttribute("month", month);
		model.addAttribute("msg", "引数が渡っていることを確認");
		return "schedule";
	}    
}
