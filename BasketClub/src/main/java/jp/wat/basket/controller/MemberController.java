package jp.wat.basket.controller;

import java.util.List;

import jp.wat.basket.entity.Member;
import jp.wat.basket.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/member")
	public String index(Model model){
	
		List<Member> memberList = memberService.getInformation();
		
		model.addAttribute("memberList", memberList);
		return "member";
		
	} 
		
}
