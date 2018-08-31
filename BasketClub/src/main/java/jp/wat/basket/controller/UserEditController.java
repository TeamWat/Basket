package jp.wat.basket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wat.basket.common.Util;
import jp.wat.basket.common.Enum.EnumGrade;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.UserMember;
import jp.wat.basket.entity.UserNendo;
import jp.wat.basket.form.MemberForm;
import jp.wat.basket.form.UserForm;
import jp.wat.basket.model.UserViewModel;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes(value={"userInfo"})
public class UserEditController {
	private static final Logger logger = LoggerFactory.getLogger(UserEditController.class);
	
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
	
	/********************************************************
	 * ユーザー登録
	 *******************************************************/
	// 登録画面　表示
	@RequestMapping(value="/user/regist/input", method=RequestMethod.GET)
	public String registInput(UserInfo userInfo, Model model){
				
		userInfo.setStartViewName("/user/regist/input");
		
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
	
	// 登録／変更処理（共通）
	@RequestMapping(value={"/user/regist/transactfinish","/user/edit/transactfinish"}, method=RequestMethod.POST, params="submit")
	public String registComplete(@Validated UserForm form, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes){

		ModelMapper modelMapper = new ModelMapper();
		LoginUser user = modelMapper.map(form, LoginUser.class);
				
		// パスワード暗号化
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// 共通項目の設定
		//LoginUser loginUser = commonService.getLoginUser();
		user.setDeleteFlg(0);
		user.setRegistUser(user.getUserId());
		user.setRegistTime(new Timestamp(System.currentTimeMillis()));
		user.setUpdateUser(user.getUserId());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		// DB更新処理 
		userService.registUser(user);
		
		String nextViewName = userInfo.getStartViewName();
		sessionStatus.setComplete();

		// 登録／編集判定
		if(nextViewName.equals("/user/regist/input")){
			redirectAttributes.addFlashAttribute("message","登録が完了しました");
			return "redirect:/user/regist/input";
		}else {
			redirectAttributes.addFlashAttribute("message","更新が完了しました");
			return "redirect:/user/userDetail/" + user.getUserId();
		}
		
	}	
	
	/********************************************************
	 * ユーザー変更
	 *******************************************************/
	// 変更画面　表示
		@RequestMapping(value="/user/edit/input/{uid}", method=RequestMethod.GET)
		public String editInput(@PathVariable("uid") String uid, UserInfo userInfo, Model model){

			userInfo.setStartViewName("/user/edit/input");
			
			// ユーザー情報取得
			LoginUser user = userService.findById(uid);
			
			ModelMapper modelMapper = new ModelMapper();
			UserForm userForm = modelMapper.map(user, UserForm.class);
			// パスワードは非表示
			userForm.setPassword("");
			
			// セレクトボックスのItemを取得（チーム区分、学年、ロール）
			model.addAttribute("selectTeam", EnumTeamKubun.values());
			model.addAttribute("selectGrade", EnumGrade.values());
			model.addAttribute("selectRole", EnumRole.values());
			
			// ロールの設定
			EnumRole enumRole = EnumRole.getByName(userForm.getRole());
			model.addAttribute("EnumRole", enumRole);
			userForm.setRoleCode(enumRole.getCode());	
			
			// ユーザー情報取得
			LoginUser loginUser = commonService.getLoginUser();
			
			model.addAttribute("userName", loginUser.getUserName());
			model.addAttribute("userForm", userForm);
			
			return "/user/edit/userEditInput";
			
		}
		
		// 変更画面　キャンセル
		@RequestMapping(value = "/user/edit/confirm", method = RequestMethod.POST, params = "cancel")
		public String editCancel(UserForm userForm, Model model) {
			
			// 詳細画面にリダイレクト
			return "redirect:/user/userDetail/" + userForm.getUserId();
		}
		
		// 変更確認画面
		@RequestMapping(value="/user/edit/confirm", method=RequestMethod.POST, params = "confirm")
		public String editComfirm(@Validated UserForm userForm,
				BindingResult result,
				RedirectAttributes redirectAttributes,
				Model model) throws Exception{

				// ユーザーIDが変わっていたらエラーとする
				if(userService.findById(userForm.getUserId()) == null){
					logger.error("Formから渡されたユーザーIDがDBに存在しません");
					throw new Exception();
				}

				// TODO パスワード変更チェックボックスにチェックが入っていた場合はパスワードを変更情報として渡す
				// TODO 入力画面でエラー発生させ確認画面に遷移して戻るをすると、システムエラー
				// パスワード/パスワード確認の入力チェック
				Map<String, List<String>> errorMap = userForm.passwordValidate(userForm.isPassUpdCheck());
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
					return "/user/edit/userEditInput";
				}
				
				// ロールの設定
				model.addAttribute("EnumRole", EnumRole.decode(userForm.getRoleCode()));
				userForm.setRole(EnumRole.decode(userForm.getRoleCode()).getSName());			
				
				// ログインユーザー情報取得
				LoginUser loginUser = commonService.getLoginUser();
				
				model.addAttribute("userName", loginUser.getUserName());
				model.addAttribute("userForm", userForm);
				
				return "/user/edit/userEditConfirm";
		}	
	
	/********************************************************
	 * ユーザー詳細設定
	 *******************************************************/
	// ユーザー詳細画面　表示
	@RequestMapping(value="/user/userDetail/{uid}", method=RequestMethod.GET)
	public String registDetailInput(@PathVariable("uid") String uid, UserInfo userInfo, Model model){
		
		// ユーザー情報取得
		LoginUser user = userService.findById(uid);
		
		ModelMapper modelMapper = new ModelMapper();
		UserForm userForm = modelMapper.map(user, UserForm.class);
		
		// 設定可能な年度の取得
		List<Integer> nendoList = memberService.getMembersNendoList();
		
		// 参照可能な年度の取得
		// List<Integer> permissionNendoList = userService.getPermissionUserNendo(userForm.getUserId()); 
		
		// 参照設定しているメンバーの取得
		List<UserMember> memberList = memberService.getUserMember(uid);
		
		// 設定可能な年度リストと、年度のON/OFF状態の初期値を設定する
		// TODO Moduleに切り出し
		HashMap <Integer, List<Member>> nendoKeys = new LinkedHashMap<>();
		Boolean existFlg;
		List<Member> memberListPerNendo = new ArrayList<Member>();
		for (Integer nendo : nendoList) {
			existFlg =false;
			
			//　年度別に、ユーザーが参照可能なユーザーを設定する
			for (UserMember userMember : memberList) {
				if(nendo.intValue() == userMember.getNendo().intValue()){
					existFlg = true;
					memberListPerNendo.add(userMember.getMember());
				}
			}
				
			// 参照可能なメンバーが存在しない年度は設定なしを初期セットする
			if (existFlg){
				nendoKeys.put(nendo, memberListPerNendo);
			} else {
				nendoKeys.put(nendo, null);
			}
		}
		
		// TODO　デバッグコード。後で削除。
		for (Integer key : nendoKeys.keySet()) {
			System.out.println(key + ":" + nendoKeys.get(key));
		}
			
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		// ロールEnumを設定
		EnumRole role = EnumRole.getByName(userForm.getRole());
		model.addAttribute("EnumRole", role);
	
		model.addAttribute("nendoKeys", nendoKeys);
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userForm", userForm);
		model.addAttribute("nendoList", nendoList);
		
		return "/user/userDetail";
	}
	
	
	
	// メンバー閲覧設定画面　表示
	@RequestMapping(value="/user/usersMember", method=RequestMethod.GET)
	public String userMemberInput(@RequestParam("uid") String uid, UserInfo userInfo, Model model){
		
		// ユーザー情報取得
		LoginUser user = userService.findById(uid);
		
		ModelMapper modelMapper = new ModelMapper();
		UserForm userForm = modelMapper.map(user, UserForm.class);

		// 設定可能な年度の取得
		List<Integer> nendoList = memberService.getMembersNendoList();
		
		List<Member> usersMemberList = new ArrayList<Member>();
		HashMap<Integer, List<Member>> nendoMembers = new LinkedHashMap<Integer, List<Member>>();
		
		for (int nendo : nendoList) {
			// パラメータの年度に登録されているメンバーリストを取得してMAPに追加する
			usersMemberList = memberService.getPermissionMember(nendo);
			nendoMembers.put(nendo, usersMemberList);
		}

		// 初期表示時の年度はセッションから取得する
		Integer currentNendo = util.getNendo(userInfo);
		//List<Member> usersMemberList =  memberService.getPermissionMember(currentNendo);
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("nendoList", nendoList);
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("userForm", userForm);
		model.addAttribute("currentNendo", currentNendo);
		model.addAttribute("usersMemberList", usersMemberList);
		model.addAttribute("nendoMembers", nendoMembers);
		
		return "/user/usersMember";
	}
	
}
