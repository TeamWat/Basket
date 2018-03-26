package jp.wat.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wat.basket.dao.UserDao;
import jp.wat.basket.entity.UserMaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class TopController {
	
    public static void main(String[] args) {
        SpringApplication.run(TopController.class, args);
    }	

	@RequestMapping(value = "/")
	public String index(){
		return "test";
	}
	
	@RequestMapping("/hello")
	public ModelAndView hello(ModelAndView mav){
		mav.setViewName("index");
		mav.addObject("msg", "引数が渡っていることを確認");
		return mav;
	}
	
	@RequestMapping("/boot")
	public ModelAndView boot(ModelAndView mav){
		mav.setViewName("boot");
		//mav.addObject("msg", "引数が渡っていることを確認");
		return mav;
	}
	
	@RequestMapping("/top")
	public ModelAndView top(ModelAndView mav){
		mav.setViewName("top");
		//mav.addObject("msg", "引数が渡っていることを確認");
		return mav;
	}
	
	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mav){
		mav.setViewName("main");
		//mav.addObject("msg", "引数が渡っていることを確認");
		return mav;
	}
	
	@RequestMapping("/attend")
	public ModelAndView attend(ModelAndView mav){
		mav.setViewName("attendList");
		
		@SuppressWarnings("unCheck")
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
		UserDao userDao = (UserDao) context.getBean("userDao");
		
		List<UserMaster> userList = null;
		try{
			
			// DBからユーザー一覧を全件取得する
			userList = userDao.getUserList();
			
			// 対象データがない場合はエラーメッセージを設定する
			if(userList.size() == 0){
				mav.addObject("error", "データがありませんでした。");
			}
			
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		mav.addObject("userList", userList);
	
		return mav;
	}
	
//	@RequestMapping(value="/attend", method=RequestMethod.GET)
//	public String dispAttendList(Model model){
//		
//		@SuppressWarnings("unCheck")
//		ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
//		UserDao userDao = (UserDao) context.getBean("userDao");
//		
//		List<UserMaster> userList = null;
//		try{
//			userList = userDao.getUserList();
//
//		}catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//		System.out.println(userList.size());
//		model.addAttribute("userList", userList);
//		
//		return "attendList";
//	}
	
	
	
}
