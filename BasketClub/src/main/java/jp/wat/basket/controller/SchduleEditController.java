package jp.wat.basket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import jp.wat.basket.common.Util;
import jp.wat.basket.entity.Schedule;
import jp.wat.basket.form.ScheduleForm;
import jp.wat.basket.service.ScheduleService;

import org.modelmapper.ModelMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes(value={"userInfo"})
@EnableWebSecurity
public class SchduleEditController {
	private static final Logger logger = LoggerFactory.getLogger(SchduleEditController.class);
	
	@ModelAttribute("userInfo")
	UserInfo userInfo() {
	    return new UserInfo();
	}
	
	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	Util util;

	/********************************************************
	 * スケジュール編集
	 *******************************************************/
	// スケジュール編集画面　表示
	@RequestMapping(value="/schedule/edit/{month}", method=RequestMethod.GET)
	public String memberEdit(@PathVariable("month") Integer month, UserInfo userInfo,  Model model){
				
		// TODO 年度の初期設定はログイン時にセッションに格納するよう変更
		Integer nendo = util.getNendo(userInfo);
				
		//スケジュール一覧取得
		List<Schedule> scheduleList= scheduleService.getScheduleData(nendo, month);
		ModelMapper modelMapper = new ModelMapper();
		
		List<ScheduleForm> scheduleFormList = new ArrayList<ScheduleForm>();
		
		for(Schedule schedule: scheduleList){
			ScheduleForm scheduleForm = modelMapper.map(schedule, ScheduleForm.class);
			scheduleFormList.add(scheduleForm);
		}
		
		model.addAttribute("nendo", nendo);
		model.addAttribute("scheduleFormList", scheduleFormList);
		return "/schedule/edit/scheduleEdit";
	}
		
	@ResponseBody
	@RequestMapping(value={"/schedule/edit/complete"}, method=RequestMethod.POST)
	public String asyncUpdate(@Validated ScheduleForm scheduleForm, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes){

		logger.info("非同期処理スタート");
		logger.debug(ToStringBuilder.reflectionToString(scheduleForm, ToStringStyle.DEFAULT_STYLE)); 
		
		Integer newSeq = null;
		
		try {
			ModelMapper modelMapper = new ModelMapper();
			Schedule schedule = modelMapper.map(scheduleForm, Schedule.class);
	
			// 共通項目の設定
			schedule.setRegistUser(1); //TODO ログインユーザーに変更
			schedule.setRegistTime(new Timestamp(System.currentTimeMillis()));
			schedule.setUpdateUser(1); //TODO ログインユーザーに変更
			schedule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			
			// DB更新処理
			if(schedule.getSeq() == null){
				newSeq = scheduleService.insert(schedule);
				logger.info(Integer.toString(newSeq));
			}else{
				scheduleService.save(schedule);
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			sessionStatus.setComplete();
		}
		
		// Json形式で返却する
		String responseJson = "{\"seq\" :" + newSeq + "}";
		return responseJson;
		
	}
	
	
//	// 変更画面　キャンセル
//	@RequestMapping(value = {"/member/edit/confirm"}, method = RequestMethod.POST, params = "cancel")
//	public String editCancel(Model model) {
//		return "redirect:/member";
//	}
	
//	// 変更確認画面　表示
//	@RequestMapping(value = {"/member/edit/confirm"}, method = RequestMethod.POST, params = "confirm")
//	public String editConfirm(
//			@Validated MemberForm form,
//			BindingResult result,
//			Model model) {
//					
//		//変更前情報取得
//		Member member = memberService.findById(form.getMemberId());
//		ModelMapper modelMapper = new ModelMapper();
//		
//		MemberForm befMemberForm = modelMapper.map(member, MemberForm.class);
//		
//		//TODO バリデーションチェック
//		model.addAttribute("befMemberF", befMemberForm);
//		model.addAttribute("memberForm", form);
//		
//		return "/member/edit/editConfirm";
//	}
	
	@ResponseBody
	@RequestMapping(value={"/schedule/edit/delete"}, method=RequestMethod.POST)
	public void deleteComplete(@Validated ScheduleForm scheduleForm, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes){

		logger.info("schedule削除 スタート");
		logger.debug(ToStringBuilder.reflectionToString(scheduleForm, ToStringStyle.DEFAULT_STYLE)); 
		
		try {
			ModelMapper modelMapper = new ModelMapper();
			Schedule schedule = modelMapper.map(scheduleForm, Schedule.class);
	
			// DB更新処理 
			scheduleService.delete(schedule);
		
		}catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			sessionStatus.setComplete();
		}
	}
	
	
}
