package jp.wat.basket.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class UserForm {
	
	@NotBlank(message = "入力してください")
	@Pattern(regexp="[a-zA-Z0-9]*",message="登録可能な文字は「英字」と「数字」です")
	private String userId;
	
	@NotBlank(message = "入力してください")
	private String userName;
	
	@NotBlank(message = "入力してください")
	private String userNameKn;

	@Size(min = 8, max = 32, message = "8文字以上、32文字以内で入力してください")
	@Pattern(regexp="[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*",message="登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です")
	private String password;
	
	@Size(min = 8, max = 32, message = "8文字以上、32文字以内で入力してください")
	@Pattern(regexp="[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*",message="登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です")
	private String rePassword;
	
	private String role;
	
	@NotNull(message = "選択してください")
	private Integer roleCode;
	
	@Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$",message="メールアドレスの形式で入力ください")
	private String mail;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameKn() {
		return userNameKn;
	}

	public void setUserNameKn(String userNameKn) {
		this.userNameKn = userNameKn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Integer roleCode) {
		this.roleCode = roleCode;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@AssertTrue(message="パスワード と パスワード（確認用）の入力値が一致しません")
    public boolean isPasswordMatch() {
		if(password != null && rePassword != null){
			// 戻り値がfalseの時にValidationエラーが有りと判定される
			return password.equals(rePassword);
		}
        return true;
    }
	
}
