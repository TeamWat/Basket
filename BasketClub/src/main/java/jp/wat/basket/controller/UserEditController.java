package jp.wat.basket.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wat.basket.BussinessException;
import jp.wat.basket.common.Util;
import jp.wat.basket.common.Enum.EnumGrade;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.UserMember;
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
	
	/////////////////////////////////////////////////////////
	// ユーザー変更
	/////////////////////////////////////////////////////////
	/**
	 *  変更画面　表示
	 * @param uid
	 * @param userInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/edit/input/{uid}", method=RequestMethod.GET)
	public String editInput(@PathVariable("uid") String uid, UserInfo userInfo, RedirectAttributes redirectAttributes, Model model) throws BussinessException{

		userInfo.setStartViewName("/user/edit/input");
		UserForm userForm = new UserForm();

		// ユーザー情報取得
		LoginUser user = userService.findById(uid);

		// 変更対象のユーザーが存在しない場合はエラー
		if (user == null) {
			//throw new BussinessException("userIdがDBに存在しません。uid=" + uid);
			logger.error("[editInput] userIdがDBに存在しません。uid=" + uid);
			redirectAttributes.addFlashAttribute("errorMessage", "想定外のエラーが発生しました。<br>操作をやり直してください。");
			// 詳細画面にリダイレクト
			return "redirect:/user/userDetail/" + uid;
		}

		ModelMapper modelMapper = new ModelMapper();
		userForm = modelMapper.map(user, UserForm.class);
		
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
		
	/**
	 *  変更画面　キャンセル
	 * @param userForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/edit/cancel", method = RequestMethod.POST, params = "cancel")
	public String editCancel(UserForm userForm, Model model) {
		
		// 詳細画面にリダイレクト
		return "redirect:/user/userDetail/" + userForm.getUserId();
	}
	
		
	/**
	 *  変更処理
	 */
	@RequestMapping(value={"/user/edit/transactfinish"}, method=RequestMethod.POST)
	public String editComplete(@Validated UserForm userForm, BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes) throws Exception{
		
		// 変更対象のユーザーが存在しない場合はエラー
		LoginUser userBfUpdate = userService.findById(userForm.getUserId());
		if(userBfUpdate == null){
			logger.error("[editComplete] userIdがDBに存在しません。userForm.getUserId()=" + userForm.getUserId());
			redirectAttributes.addFlashAttribute("errorMessage", "想定外のエラーが発生しました。<br>操作をやり直してください。");
			// 詳細画面にリダイレクト
			return "redirect:/user/userDetail/" + userForm.getUserId();
		}

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
		
		// 更新用データ設定
		ModelMapper modelMapper = new ModelMapper();
		LoginUser user = modelMapper.map(userForm, LoginUser.class);
		
		// Roleの設定
		user.setRole(EnumRole.decode(userForm.getRoleCode()).getSName());
		
		if(userForm.isPassUpdCheck()){
			// 入力パスワードを暗号化してセット
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} else {
			// 変更前のDBのパスワードを取得してセット
			user.setPassword(userBfUpdate.getPassword());
		}
		
		// 共通項目の設定
		LoginUser loginUser = commonService.getLoginUser();
		user.setRegistUser(userBfUpdate.getRegistUser());
		//user.setRegistTime(userBfUpdate.getRegistTime());
		user.setUpdateUser(loginUser.getUserId());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		user.setDeleteFlg(userBfUpdate.isDeleteFlg());

		LoginUser testUser = new LoginUser();
		
		// DB更新処理 
		userService.save(user);
		redirectAttributes.addFlashAttribute("message","更新が完了しました");
		return "redirect:/user/userDetail/" + user.getUserId();
		
	}	
		
	/////////////////////////////////////////////////////////
	// ユーザー削除
	/////////////////////////////////////////////////////////
	/**
	 * 削除処理
	 */
	@RequestMapping(value={"/user/delete/transactfinish"}, method=RequestMethod.POST)
	public String deleteUser(@Validated UserForm userForm, BindingResult result, SessionStatus sessionStatus, Model model, RedirectAttributes redirectAttributes) {
		
		try{
			// ユーザー情報および紐づく情報を削除する
			userService.deleteUser(userForm.getUserId());
		
		} catch(Exception e) {
			logger.error("ユーザーの削除に失敗しました（ユーザーID: " + userForm.getUserId() + ")");
			redirectAttributes.addFlashAttribute("errorMessage","ユーザーの削除ができませんでした");
			return "redirect:/user/userDetail/" + userForm.getUserId();
		}
		
		redirectAttributes.addFlashAttribute("message","ユーザーの削除処理が完了しました");
		return "redirect:/user";
	}
		
	/////////////////////////////////////////////////////////
	// ユーザー詳細設定
	/////////////////////////////////////////////////////////
	
	/**
	 *  ユーザー詳細画面　表示
	 * @param uid
	 * @param userInfo
	 * @param model
	 * @return
	 */
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
	
	
	
	/**
	 *  メンバー閲覧設定画面　表示
	 * @param uid
	 * @param userInfo
	 * @param model
	 * @return
	 */
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
