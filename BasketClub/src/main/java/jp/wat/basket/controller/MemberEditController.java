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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	
	/////////////////////////////////////////////////////////
	// メンバー情報変更
	/////////////////////////////////////////////////////////
	
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
		return "/member/edit/memberEditInput";
	}
	
	// 変更画面　キャンセル
	@RequestMapping(value = {"/member/edit/confirm"}, method = RequestMethod.POST, params = "cancel")
	public String editCancel(MemberForm memberForm, Model model) {
		return "redirect:/member/memberDetail/" + memberForm.getMemberId();
	}
		
	// 変更処理
	@RequestMapping(value={"/member/edit/transactfinish"}, method=RequestMethod.POST)
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
		
		logger.info("[member更新]");
		loggerInfoMember(member);

		// DB更新処理 
		memberService.registMember(member);
		
		redirectAttributes.addFlashAttribute("message","更新が完了しました");
		return "redirect:/member";
	}	
	
	/**
	 *  変更確認画面　キャンセル
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/edit/editCancel", method = RequestMethod.POST)
	public String registCorrect(MemberForm memberForm, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("memberForm", memberForm);
		return "redirect:/member/edit/input";
	}


	/////////////////////////////////////////////////////////
	// メンバー情報削除
	/////////////////////////////////////////////////////////
	
	// メンバー情報削除
	@RequestMapping(value={"/member/delete/transactfinish"}, method=RequestMethod.POST)
	public String deleteComplete(MemberForm form, UserInfo userInfo, Model model, RedirectAttributes redirectAttributes){

		try {
			ModelMapper modelMapper = new ModelMapper();
			Member member = modelMapper.map(form, Member.class);
			memberService.deleteMember(member);
		} catch (RuntimeException e) {
			logger.error("メンバーの削除に失敗しました（メンバーID: " + form.getMemberId() + ")");
			redirectAttributes.addFlashAttribute("errorMessage", "ユーザーの削除ができませんでした");
			return "redirect:/member/memberDetail/" + form.getMemberId();
		}
		
		redirectAttributes.addFlashAttribute("message","削除が完了しました");
		return "redirect:/member";
	}
	
	/////////////////////////////////////////////////////////
	// メンバー詳細設定
	/////////////////////////////////////////////////////////
	
	/**
	 *  メンバー詳細画面　表示
	 * @param mid
	 * @param userInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/member/memberDetail/{mid}", method=RequestMethod.GET)
	public String registDetailInput(@PathVariable("mid") String mid, UserInfo userInfo, Model model){
		
		//変更前情報取得
		Member member = memberService.findById(Integer.parseInt(mid));
		ModelMapper modelMapper = new ModelMapper();
		MemberForm befMemberForm = modelMapper.map(member, MemberForm.class);

		model.addAttribute("EnumTeam", EnumTeamKubun.decode(befMemberForm.getTeamKubun()));
		model.addAttribute("EnumGrade", EnumGrade.decode(befMemberForm.getGrade()));
		model.addAttribute("EnumSebango", EnumSebango.decode(befMemberForm.getNo()));
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", befMemberForm);
		
		return "/member/memberDetail";
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException exception, RedirectAttributes redirectAttributes) {
		
		logger.error("RuntimeExceptionHandler", exception);
	    redirectAttributes.addFlashAttribute("errorMessage","想定外のエラーが発生しました。<br>操作をやり直してください。");
	    
		//メンバー一覧に遷移
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
