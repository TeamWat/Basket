package jp.wat.basket.controller;

import java.sql.Timestamp;

import jp.wat.basket.common.Enum.EnumGrade;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.form.MemberForm;
import jp.wat.basket.form.UserForm;
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
public class MemberRegistController {
	private static final Logger logger = LoggerFactory.getLogger(MemberRegistController.class);
	
	@ModelAttribute("userInfo")
	UserInfo userInfo() {
	    return new UserInfo();
	}
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	CommonService commonService;

	// 登録画面　表示
	@RequestMapping(value="/member/regist/input", method=RequestMethod.GET)
	public String registInput(UserInfo userInfo, ModelMap model){
	
		userInfo.setStartViewName("/member/regist/input");
		
		// セレクトボックスのItemを取得（チーム区分、学年、背番号）
		model.addAttribute("selectTeam", EnumTeamKubun.values());
		model.addAttribute("selectGrade", EnumGrade.values());
		model.addAttribute("selectNo", EnumSebango.values());
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		// 確認画面で修正ボタンを押下してリダイレクトしてきた時はフォームの入力内容（memberForm）が Modelオブジェクトに設定されている
		// 初期画面表示時はuserFormは設定されていないため新規作成する
		MemberForm memberForm = model.containsKey("memberForm")? (MemberForm) model.get("memberForm") : new MemberForm();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", memberForm);
		
		return "member/regist/memberRegistInput";
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
			model.addAttribute("selectTeam", EnumTeamKubun.values());
			model.addAttribute("selectGrade", EnumGrade.values());
			model.addAttribute("selectNo", EnumSebango.values());
			return "member/regist/memberRegistInput";
		}
		
		//TODO 変更時の変更有無チェック
		//TODO Formに変更有無を追加して、チェック結果を格納
		
		model.addAttribute("EnumTeam", EnumTeamKubun.decode(memberForm.getTeamKubun()));
		model.addAttribute("EnumGrade", EnumGrade.decode(memberForm.getGrade()));
		model.addAttribute("EnumNo", EnumSebango.decode(memberForm.getNo()));
		
		// ユーザー情報取得
		LoginUser loginUser = commonService.getLoginUser();
		
		model.addAttribute("userName", loginUser.getUserName());
		model.addAttribute("memberForm", memberForm);
		return "member/regist/memberRegistConfirm";
	}
	
	// 登録処理
	@RequestMapping(value={"/member/regist/transactfinish"}, method=RequestMethod.POST)
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
		
		logger.info("[member登録]");
		loggerInfoMember(member);

		// DB更新処理 
		memberService.registMember(member);
		
		// 登録
		redirectAttributes.addFlashAttribute("message","登録が完了しました");
		return "redirect:/member/regist/input";
	}	

	// ブラウザバック対策
	@RequestMapping(value={"/member/confirm"}, method=RequestMethod.GET)
	public String registConfirmBack(Model model){
		return "redirect:/member";
	}
	
	/**
	 *  登録確認画面　キャンセル
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/regist/registCancel", method = RequestMethod.POST)
	public String registCorrect(MemberForm memberForm, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("memberForm", memberForm);
		return "redirect:/member/regist/input";
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
