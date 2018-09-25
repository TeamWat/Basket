package jp.wat.basket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.wat.basket.common.Util;
import jp.wat.basket.common.Enum.EnumGrade;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.form.UserForm;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.UserService;
import jp.wat.basket.service.MemberService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes(value={"userInfo"})
public class UserRegistController {
	private static final Logger logger = LoggerFactory.getLogger(UserRegistController.class);
	
	@ModelAttribute("userInfo")
	UserInfo userInfo() {
	    return new UserInfo();
	}
	
	@Autowired
	UserService userService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	Util util;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/////////////////////////////////////////////////////////
	// ユーザー登録
	/////////////////////////////////////////////////////////
	/**
	 *  登録画面　表示
	 * @param userInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/regist/input", method=RequestMethod.GET)
	public String registInput(UserInfo userInfo, Model model){
					
		// セレクトボックスのItemを取得（チーム区分、学年、ロール）
		model.addAttribute("selectTeam", EnumTeamKubun.values());
		model.addAttribute("selectGrade", EnumGrade.values());
		model.addAttribute("selectRole", EnumRole.values());
		
		// ログインユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userForm", new UserForm());
		
		return "/user/regist/userRegistInput";
	}
	
	/**
	 *  登録画面　キャンセル
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/regist/confirm", method = RequestMethod.POST, params = "cancel")
	public String registCancel(Model model) {
		return "redirect:/user";
	}
	
	/**
	 *  登録確認画面　表示　
	 * @param userForm
	 * @param result
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/regist/confirm", method = RequestMethod.POST, params = "confirm")
	public String registComfirm(@Validated UserForm userForm,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		// ユーザーIDの重複チェック
		if (userService.findById(userForm.getUserId()) != null)  {
			result.rejectValue("userId",null , "登録済みのIDです。"); 
		}
		
		// パスワード/パスワード確認の入力チェック
		Map<String, List<String>> errorMap = userForm.passwordValidate(true);
		List<String> passwordErrorMsgList = errorMap.get("password") != null? errorMap.get("password"): new ArrayList<String>();
		List<String> rePasswordErrorMsgList = errorMap.get("rePassword")!= null? errorMap.get("rePassword"): new ArrayList<String>();
		
		// パスワードエラーがあればエラーメッセージを設定
		for(String passwordErrorMsg : passwordErrorMsgList){
			result.rejectValue("password", null , passwordErrorMsg); 
		}
		
		// パスワード（確認用）にエラーがあればエラーメッセージを設定
		for(String rePasswordErrorMsg : rePasswordErrorMsgList){
			result.rejectValue("rePassword", null , rePasswordErrorMsg); 
		}
		
		if(result.hasErrors()){
			// ロールセレクトボックスのItemを設定
			model.addAttribute("selectRole", EnumRole.values());
			return "/user/regist/userRegistInput";
		}
					
		// ロールの設定
		model.addAttribute("EnumRole", EnumRole.decode(userForm.getRoleCode()));
		userForm.setRole(EnumRole.decode(userForm.getRoleCode()).getSName());
		
		// ログインユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userForm", userForm);
				
		return "/user/regist/userRegistConfirm";
	
	}
	
	/**
	 *  登録処理
	 * @param form
	 * @param userInfo
	 * @param result
	 * @param sessionStatus
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value={"/user/regist/transactfinish"}, method=RequestMethod.POST, params="submit")
	public String registComplete(@Validated UserForm form, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes){

		ModelMapper modelMapper = new ModelMapper();
		LoginUser user = modelMapper.map(form, LoginUser.class);
				
		// パスワード暗号化
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// 共通項目の設定
		LoginUser loginUser = commonService.getLoginUser();
		user.setDeleteFlg(0);
		user.setRegistUser(loginUser.getUserId());
		user.setRegistTime(new Timestamp(System.currentTimeMillis()));
		user.setUpdateUser(loginUser.getUserId());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		try{
			// DB更新処理 
			userService.registUser(user);
		
		} catch(RuntimeException exception){
			logger.error("[@registComplete]userForm.userId=" + form.getUserId() +" errorMessage:" + exception.getMessage());
			
			//入力画面に遷移
			model.addAttribute("selectRole", EnumRole.values());
			model.addAttribute("userForm", form);
			model.addAttribute("errorMessage", "想定外のエラーが発生しました。<br>操作をやり直してください。");
			return "/user/regist/userRegistInput";
		}

		redirectAttributes.addFlashAttribute("message","登録が完了しました");
		return "redirect:/user/regist/input";
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException exception, RedirectAttributes redirectAttributes) {
		
		logger.error("RuntimeExceptionHandler", exception);
	    redirectAttributes.addFlashAttribute("errorMessage","想定外のエラーが発生しました。<br>操作をやり直してください。");
	    
		//ユーザー一覧に遷移
		return "redirect:/user";
	}
	
}
