package jp.wat.basket.controller;

import java.util.ArrayList;
import java.util.List;

import jp.wat.basket.entity.Information;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.form.InformationForm;
import jp.wat.basket.form.TopForm;
import jp.wat.basket.framework.security.AccountUserDetails;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.InformationService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopController {
	
	@Autowired
	InformationService informationService;
	
	@Autowired
	CommonService commonService;
	
	@RequestMapping("/top")
	public String index(Model model){
		
		List<Information> informationList = informationService.getInformation();

			// TODO 後で削除（debug start)
			System.out.println("取得レコード数：" + informationList.size());
			// debug end

		ModelMapper modelMapper = new ModelMapper();
		TopForm topForm = new TopForm();
		List<InformationForm> informations = new ArrayList<>();
		
		InformationForm informationForm = new InformationForm();
		for (Information info :informationList) {
			informationForm = modelMapper.map(info, InformationForm.class);
			
			informationForm.setRegistDate(new java.util.Date(info.getDisplayRegistDate().getTime()));
			informations.add(informationForm);
		}
		
		topForm.setInformationList(informations);
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		
		model.addAttribute("topForm", topForm);
		model.addAttribute("bgColorNone", "bgcolor-none"); //background-color:none
		model.addAttribute("informationList", informationList);
		return "main";
		
	}  

}
