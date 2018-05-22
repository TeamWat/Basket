package jp.wat.basket.form;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class MemberForm {
	
	private Integer memberId;
	
	@NotNull(message = "入力してください")
	private Integer no;
	
	private Integer nendo;
	
	@NotNull(message = "選択してください")
	private Integer teamKubun;
	
	private String teamName;
	
	@NotBlank(message = "入力してください")
	private String memberName;
	
	@NotBlank(message = "入力してください")
	private String memberNameKn;
	
	@NotNull(message = "入力してください")
	@Min(value=1, message = "学年は1〜6の範囲で入力してください")
	@Max(value=6, message = "学年は1〜6の範囲で入力してください")
	private Integer grade;
	
	private String updKbn;

	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getNendo() {
		return nendo;
	}
	public void setNendo(Integer nendo) {
		this.nendo = nendo;
	}
	public Integer getTeamKubun() {
		return teamKubun;
	}
	public void setTeamKubun(Integer teamKubun) {
		this.teamKubun = teamKubun;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberNameKn() {
		return memberNameKn;
	}
	public void setMemberNameKn(String memberNameKn) {
		this.memberNameKn = memberNameKn;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getUpdKbn() {
		return updKbn;
	}
	public void setUpdKbn(String updKbn) {
		this.updKbn = updKbn;
	}
	
}
