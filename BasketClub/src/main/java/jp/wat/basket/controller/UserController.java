package jp.wat.basket.controller;

import java.util.List;

import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.model.UserViewModel;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/user")
	public String index(Model model){
	
		List<UserViewModel> userList = userService.getAllUser();
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userList", userList);
		return "user/userlist";
	} 
		
}
