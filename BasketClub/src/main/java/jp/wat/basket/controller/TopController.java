package jp.wat.basket.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wat.basket.entity.Information;
import jp.wat.basket.form.InformationForm;
import jp.wat.basket.form.TopForm;
import jp.wat.basket.service.InformationService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TopController {
	
	@Autowired
	InformationService informationService;
	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav){
		mav.setViewName("main");
	
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
		
		mav.addObject("topForm", topForm);
		mav.addObject("informationList", informationList);
		mav.addObject("msg", "引数が渡っていることを確認");
		return mav;
		
	}  

}
