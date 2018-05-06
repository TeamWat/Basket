package jp.wat.basket.controller;

import java.util.List;

import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/member")
	public String index(Model model){
	
		List<Member> memberList = memberService.getAllMember();
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberList", memberList);
		return "member";
		
	} 
		
}
