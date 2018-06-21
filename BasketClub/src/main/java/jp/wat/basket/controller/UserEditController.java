package jp.wat.basket.controller;

import java.sql.Timestamp;
import java.util.List;

import jp.wat.basket.common.Enum.EnumGrade;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.User;
import jp.wat.basket.form.MemberForm;
import jp.wat.basket.form.UserForm;
import jp.wat.basket.model.UserViewModel;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserEditController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonService commonService;
	
	/********************************************************
	 * ユーザー登録
	 *******************************************************/
	// 登録画面　表示
	@RequestMapping(value="/user/regist/input", method=RequestMethod.GET)
	public String registInput(UserInfo userInfo, Model model){
				
		// セレクトボックスのItemを取得（チーム区分、学年、ロール）
		model.addAttribute("selectTeam", EnumTeamKubun.values());
		model.addAttribute("selectGrade", EnumGrade.values());
		model.addAttribute("selectRole", EnumRole.values());
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userForm", new UserForm());
		
		return "/user/regist/userRegistInput";
	}
	
	// 登録画面　キャンセル
	@RequestMapping(value = "/user/regist/confirm", method = RequestMethod.POST, params = "cancel")
	public String registCancel(Model model) {
		return "redirect:/user";
	}
	
	// 登録確認画面　表示　
	@RequestMapping(value = "/user/regist/confirm", method = RequestMethod.POST, params = "confirm")
	public String registComfirm(@Validated UserForm userForm,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model){
		
		if(result.hasErrors()){
			// ロールセレクトボックスのItemを設定
			model.addAttribute("selectRole", EnumRole.values());
			return "/user/regist/userRegistInput";
		}
		
		// ロールの設定
		model.addAttribute("EnumRole", EnumRole.decode(userForm.getRoleCode()));
		userForm.setRole(EnumRole.decode(userForm.getRoleCode()).getSName());
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userForm", userForm);
				
		return "/user/regist/userRegistConfirm";
	
	}
	
	// 登録／変更処理（共通）
	@RequestMapping(value={"/user/regist/transactfinish","/user/edit/transactfinish"}, method=RequestMethod.POST, params="submit")
	public String registComplete(@Validated UserForm form, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes){

		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(form, User.class);
				
		// 共通項目の設定
		LoginUser loginUser = commonService.getLoginUser();
		user.setDeleteFlg(0);
		user.setRegistUser(loginUser.getUserId());
		user.setRegistTime(new Timestamp(System.currentTimeMillis()));
		user.setUpdateUser(loginUser.getUserId());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		// DB更新処理 
		userService.registUser(user);
		
		String nextViewName = userInfo.getStartViewName();
		sessionStatus.setComplete();
		
		// 登録／編集判定
		//if(nextViewName.equals("/user/regist/input")){
			redirectAttributes.addFlashAttribute("message","登録が完了しました");
			return "redirect:/user/regist/input";
		
	}	
	
		
}
