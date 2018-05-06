package jp.wat.basket.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.ScheduleService;
import jp.wat.basket.common.Util;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.ScheduleDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Controller
public class ScheduleController {
	   
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	Util util;
	
	@Autowired
	CommonService commonService;

	@RequestMapping(value="/schedule", method=RequestMethod.GET)
	public String index(Model model, UserInfo userInfo, @RequestParam(value = "month", required = false) Integer month){
		
		// TODO 年度の初期設定はログイン時にセッションに格納するよう変更
		Integer nendo = util.getNendo(userInfo);
		if(month == null){
			// 月のパラメータが設定されていない場合は当月を初期設定
			LocalDateTime today = LocalDateTime.now();
			DateTimeFormatter df1 = DateTimeFormatter.ofPattern("MM");
			month = Integer.valueOf(df1.format(today));
		}
	
		List<ScheduleDetail> scheduleDetailList= scheduleService.getScheduleData(nendo, month);		
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("scheduleDetailList", scheduleDetailList);
		model.addAttribute("month", month);
		model.addAttribute("msg", "引数が渡っていることを確認");
		return "schedule";
	}    
}
