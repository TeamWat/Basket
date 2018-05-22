package jp.wat.basket.controller;

import java.sql.Timestamp;

import jp.wat.basket.common.Enum.EnumGrade;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.form.MemberForm;
import jp.wat.basket.service.CommonService;
import jp.wat.basket.service.MemberService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes(value={"userInfo"})
@EnableWebSecurity
public class MemberEditController {
	private static final Logger logger = LoggerFactory.getLogger(MemberEditController.class);
	
	@ModelAttribute("userInfo")
	UserInfo userInfo() {
	    return new UserInfo();
	}
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	CommonService commonService;

	/********************************************************
	 * メンバー登録
	 *******************************************************/
	// 登録画面　表示
	@RequestMapping(value="/member/regist/input", method=RequestMethod.GET)
	public String registInput(UserInfo userInfo, Model model){
	
		userInfo.setStartViewName("/member/regist/input");
		
		// セレクトボックスのItemを取得（チーム区分、学年、背番号）
		model.addAttribute("selectTeam", EnumTeamKubun.values());
		model.addAttribute("selectGrade", EnumGrade.values());
		model.addAttribute("selectNo", EnumSebango.values());
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", new MemberForm());
		
		return "/member/regist/input";
	}
	
	// 登録画面　キャンセル
	@RequestMapping(value = "/member/regist/confirm", method = RequestMethod.POST, params = "cancel")
	public String registCancel(Model model) {
		return "redirect:/member";
	}
	
	// 登録確認画面　表示
	@RequestMapping(value = "/member/regist/confirm", method = RequestMethod.POST, params = "confirm")
	public String registConfirm(
			@Validated MemberForm memberForm,
			BindingResult result,
			Model model) {

		loggerInfoMemberForm(memberForm);
		
		if(result.hasErrors()){
			//TODO URLが変わってしまい、キャンセルボタンでエラーが発生する
			return "/member/regist/input";
		}
		
//		//重複チェック
//		Member befMember = memberService.findByNo(memberForm.getNo());
//		
//		//TODO 背番号の重複チェック
//		if(befMember != null){
//			model.addAttribute("message", "登録済みの背番号です");
//			return "/member/edit";
//		}
		
		//TODO 変更時の変更有無チェック
		//TODO Formに変更有無を追加して、チェック結果を格納
		
		model.addAttribute("EnumTeam", EnumTeamKubun.decode(memberForm.getTeamKubun()));
		model.addAttribute("EnumGrade", EnumGrade.decode(memberForm.getGrade()));
		model.addAttribute("EnumNo", EnumSebango.decode(memberForm.getNo()));
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", memberForm);
		return "/member/regist/confirm";
	}
	
	// 登録／変更処理（共通）
	@RequestMapping(value={"/member/regist/transactfinish","/member/edit/transactfinish"}, method=RequestMethod.POST, params="submit")
	public String registComplete(@Validated MemberForm form, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model,
			RedirectAttributes redirectAttributes){

		ModelMapper modelMapper = new ModelMapper();
		Member member = modelMapper.map(form, Member.class);
				
		// 共通項目の設定
		LoginUser loginUser = commonService.getLoginUser();
		member.setDeleteFlg(0);
		member.setRegistUser(loginUser.getUserId());
		member.setRegistTime(new Timestamp(System.currentTimeMillis()));
		member.setUpdateUser(loginUser.getUserId());
		member.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		logger.info("[member登録／member更新]");
		loggerInfoMember(member);

		// DB更新処理 
		memberService.registMember(member);
		
		String nextViewName = userInfo.getStartViewName();
		sessionStatus.setComplete();
		
		// 登録／編集判定
		if(nextViewName.equals("/member/regist/input")){
			redirectAttributes.addFlashAttribute("message","登録が完了しました");
			return "redirect:/member/regist/input";
			
		} else if(nextViewName.equals("/member/edit/input")){
			redirectAttributes.addFlashAttribute("message","更新が完了しました");
			return "redirect:/member";
			
		} else {
			//異常値の場合はトップに遷移
			return "redirect:/";
		}
	}	

	/********************************************************
	 * メンバー変更
	 *******************************************************/
	// 変更画面　表示
	@RequestMapping(value="/member/edit/input/{mid}", method=RequestMethod.GET)
	public String memberEdit(@PathVariable("mid") Integer mid, UserInfo userInfo,  Model model){
		
		//セッションに遷移元画面を格納
		userInfo.setStartViewName("/member/edit/input");
		
		//変更前情報取得
		Member member = memberService.findById(mid);
		ModelMapper modelMapper = new ModelMapper();
		MemberForm befMemberForm = modelMapper.map(member, MemberForm.class);

		model.addAttribute("EnumTeam", EnumTeamKubun.decode(befMemberForm.getTeamKubun()));
		model.addAttribute("EnumSebango", EnumSebango.decode(befMemberForm.getNo()));
		
		// セレクトボックスのItemを取得（チーム区分、学年、背番号）
		model.addAttribute("selectTeam", EnumTeamKubun.values());
		model.addAttribute("selectGrade", EnumGrade.values());
		model.addAttribute("selectNo", EnumSebango.values());
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", befMemberForm);
		return "/member/edit/editInput";
	}
	
	// 変更画面　キャンセル
	@RequestMapping(value = {"/member/edit/confirm"}, method = RequestMethod.POST, params = "cancel")
	public String editCancel(Model model) {
		return "redirect:/member";
	}
	
	// 変更確認画面　表示
	@RequestMapping(value = {"/member/edit/confirm"}, method = RequestMethod.POST, params = "confirm")
	public String editConfirm(
			@Validated MemberForm memberForm,
			BindingResult result,
			Model model) {
					
		//変更前情報取得
		Member member = memberService.findById(memberForm.getMemberId());
		ModelMapper modelMapper = new ModelMapper();
		
		MemberForm befMemberForm = modelMapper.map(member, MemberForm.class);
		
		//TODO バリデーションチェック
		
		
		// 変更前用のEnumを取得（チーム区分、学年、背番号）
		model.addAttribute("befEnumTeam", EnumTeamKubun.decode(befMemberForm.getTeamKubun()));
		model.addAttribute("befEnumGrade", EnumGrade.decode(befMemberForm.getGrade()));
		model.addAttribute("befEnumNo", EnumSebango.decode(befMemberForm.getNo()));
		
		// 変更後用のEnumを取得（チーム区分、学年、背番号）
		model.addAttribute("EnumTeam", EnumTeamKubun.decode(memberForm.getTeamKubun()));
		model.addAttribute("EnumGrade", EnumGrade.decode(memberForm.getGrade()));
		model.addAttribute("EnumNo", EnumSebango.decode(memberForm.getNo()));
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("befMemberF", befMemberForm);
		model.addAttribute("memberForm", memberForm);
		
		return "/member/edit/editConfirm";
	}
	
	// ブラウザバック対策
	@RequestMapping(value={"/member/edit/confirm", "/member/confirm"}, method=RequestMethod.GET)
	public String registConfirmBack(Model model){
		return "redirect:/member";
	}
	
	/********************************************************
	 * メンバー削除
	 *******************************************************/
	// 削除確認画面　表示
	@RequestMapping(value = {"/member/delete/confirm/{mid}"}, method = RequestMethod.GET)
	public String deleteConfirm(@PathVariable("mid") Integer mid, Model model) {
					
		//登録情報取得
		Member member = memberService.findById(mid);
		ModelMapper modelMapper = new ModelMapper();
		MemberForm memberForm = modelMapper.map(member, MemberForm.class);
		
		//TODO バリデーションチェック
		
		model.addAttribute("EnumTeam", EnumTeamKubun.decode(memberForm.getTeamKubun()));
		model.addAttribute("EnumGrade", EnumGrade.decode(memberForm.getGrade()));
		model.addAttribute("EnumNo", EnumSebango.decode(memberForm.getNo()));
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", memberForm);
		
		return "/member/delete/deleteConfirm";
	}
	
	// 削除完了画面
	@RequestMapping(value={"/member/delete/transactfinish"}, method=RequestMethod.POST, params="submit")
	public String deleteComplete(@Validated MemberForm form, UserInfo userInfo,BindingResult result, SessionStatus sessionStatus, Model model, RedirectAttributes redirectAttributes){

		ModelMapper modelMapper = new ModelMapper();
		Member member = modelMapper.map(form, Member.class);
		
		// 共通項目の設定
		LoginUser loginUser = commonService.getLoginUser();
		member.setDeleteFlg(0);
		member.setRegistUser(loginUser.getUserId());
		member.setRegistTime(new Timestamp(System.currentTimeMillis()));
		member.setUpdateUser(loginUser.getUserId());
		member.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		// DB更新処理 
		memberService.deleteMember(member);
		
		String nextViewName = userInfo.getStartViewName();
		sessionStatus.setComplete();
		
		redirectAttributes.addFlashAttribute("message","削除が完了しました");
		return "redirect:/member";
	}
	
	/*
	 * MemberForm オブジェクトの内容をlogに出力する
	 */
	private void loggerInfoMemberForm(MemberForm memberForm){
		
		logger.info("memberForm.memberId : " + memberForm.getMemberId());
		logger.info("memberForm.no : " + memberForm.getNo());
		logger.info("memberForm.name : " + memberForm.getMemberName());
		logger.info("memberForm.namekana : " + memberForm.getMemberNameKn());
		logger.info("memberForm.grade : " + memberForm.getGrade());
	}
	
	/*
	 * Member オブジェクトの内容をlogに出力する
	 */
	private void loggerInfoMember(Member member){
		
		logger.info("member.memberId : " + member.getMemberId());
		logger.info("member.no : " + member.getNo());
		logger.info("member.name : " + member.getMemberName());
		logger.info("member.namekana : " + member.getMemberNameKn());
		logger.info("member.grade : " + member.getGrade());
	}
	
}
