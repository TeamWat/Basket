package jp.wat.basket.controller;

import java.sql.SQLException;
import java.util.List;

import jp.wat.basket.service.ScheduleService;
import jp.wat.basket.dao.UserDao;
import jp.wat.basket.entity.Schedule;
import jp.wat.basket.entity.UserMaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScheduleController {
	   
	@Autowired
	ScheduleService scheduleService;

	@RequestMapping(value="/schedule", method=RequestMethod.GET)
	public String index(Model model){
		
		// TODO 年度を設定する仕組みを検討。暫定で2017を設定
		Integer nendo = Integer.valueOf(2017);
		Integer month = Integer.valueOf(10);
		
		List<Schedule> scheduleList= scheduleService.getScheduleData(nendo, month);

		model.addAttribute("scheduleList", scheduleList);
		model.addAttribute("month", month);
		model.addAttribute("msg", "引数が渡っていることを確認");
		return "schedule";
	}    
	
	// ******************************************************************************************
	// 下記を参考に作成するのが良さそう。
	// Serviceクラスと、Repositoryクラスを作成
	// http://inaz2.hatenablog.com/entry/2017/04/05/200206
	// ******************************************************************************************
	
	
	// ******************************************************************************************
	// TODO 　spring boot では　xml　は不要
	// ******************************************************************************************
//	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//	@SuppressWarnings("unchecked")
//	private static ScheduleDao<ScheduleData> scheduleDao = (ScheduleDao<ScheduleData>)context.getBean("scheduleDao");
    
//	@RequestMapping(value="/schedule", method=RequestMethod.GET)
//	public String schedule (Model model){
//		
//		int nendo = 2017;
//		List<ScheduleData> scheduleList = scheduleDao.getMonthlyList(nendo);
//		ScheduleData scheduleData = scheduleList.get(0);
//		model.addAttribute("scheduleDto", scheduleData);	
//		model.addAttribute("scheduleList", scheduleList);
//		model.addAttribute("msg", "引数が渡っていることを確認");
//		return "schedule";
//		
//		//System.out.println("");
//	}
//	
	
//	@RequestMapping("/schedule2")
//	public ModelAndView attend(ModelAndView mav){
//		mav.setViewName("attendList");
//				
//		@SuppressWarnings("unCheck")
//		ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
//		UserDao userDao = (UserDao) context.getBean("userDao");
//		
//		List<UserMaster> userList = null;
//		try{
//			
//			// DBからユーザー一覧を全件取得する
//			userList = userDao.getUserList();
//			
//			// 対象データがない場合はエラーメッセージを設定する
//			if(userList.size() == 0){
//				mav.addObject("error", "データがありませんでした。");
//			}
//			
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//		mav.addObject("userList", userList);
//		return mav;
//	}
}
