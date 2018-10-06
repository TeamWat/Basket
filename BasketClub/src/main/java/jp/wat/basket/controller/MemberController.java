package jp.wat.basket.controller;

import java.util.List;

import jp.wat.basket.common.Util;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.model.MemberViewModel;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	Util util;
	
	@RequestMapping("/member")
	public String index(@RequestParam(name = "nendo", required = false) Integer nendo, UserInfo userInfo,  Model model){
		
		Integer selectedNendo =  (nendo != null) ? nendo :  util.getNendo(userInfo);;
		List<MemberViewModel> memberList = memberService.getMemberByNendo(selectedNendo);
		
		// 閲覧可能な年度の取得
		List<Integer> nendoList = memberService.getMembersNendoList();
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberList", memberList);
		model.addAttribute("nendoList", nendoList);
		model.addAttribute("selectedNendo", selectedNendo);
		return "member";
		
	} 
		
}
