package jp.wat.basket.controller;

import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.service.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping({"/index","/"})
	public String index(@AuthenticationPrincipal UserDetails userDetails, Model model){
		
		// ログイン済みの場合はログイン後トップにリダイレクト
		if(userDetails != null){
			return "redirect:/top";
			
		// 未ログインの場合はログイン画面を表示
		}else{
			return "index";
		}
	}
}
