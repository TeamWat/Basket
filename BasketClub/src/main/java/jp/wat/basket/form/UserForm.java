package jp.wat.basket.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	//@Size(min = 8, max = 32, message = "8文字以上、32文字以内で入力してください")
	//@Pattern(regexp="[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*",message="登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です")
	private String password;
	
	//@Size(min = 8, max = 32, message = "8文字以上、32文字以内で入力してください")
	//@Pattern(regexp="[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*",message="登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です")
	private String rePassword;
	
	private String role;
	
	@NotNull(message = "選択してください")
	private Integer roleCode;
	
	@Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$",message="メールアドレスの形式で入力ください")
	private String mail;

	public boolean passUpdCheck;

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
	
	public boolean isPassUpdCheck() {
		return passUpdCheck;
	}

	public void setPassUpdCheck(boolean passUpdCheck) {
		this.passUpdCheck = passUpdCheck;
	}
	
//	@AssertTrue(message="パスワード と パスワード（確認用）の入力値が一致しません")
//    public boolean isPasswordMatch() {
//		if(password != null && rePassword != null){
//			// 戻り値がfalseの時にValidationエラーが有りと判定される
//			return password.equals(rePassword);
//		}
//        return true;
//    }


	/** パスワードバリデーション
	 * @param isCheckFlg パスワードとパスワード（確認用）の入力チェックをする場合はtrue
	 * 
	 * チェック内容
	 * ・パスワード／パスワード（確認用）の入力必須
	 * ・チェック内容
	 *  　@Size(min = 8, max = 32, message = "8文字以上、32文字以内で入力してください")
	 *  　@Pattern(regexp="[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*",message="登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です")
	 * ・パスワードとパスワード（確認用）の入力値が一致していること
	 * 
	*/	
	public Map<String, List<String>> passwordValidate(boolean isCheckFlg){
		
		// TODO パラメーターが２つあるので、返却方法は工夫が必要。Mapにする？でも複数エラー返さない？？
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> passwordErrorMsgList = new ArrayList<String>();
		List<String> rePasswordErrorMsgList = new ArrayList<String>();
		
		// 登録時　もしくは、変更チェックが入っている場合は、パスワードの入力値チェックを行う。
		if(isCheckFlg) {
			// パスワードチェック
			if(this.getPassword() == null){
				passwordErrorMsgList.add("8文字以上、32文字以内で入力してください");
			}else{
				if(this.getPassword().length() <= 8 || this.getPassword().length() > 32 ){
					passwordErrorMsgList.add("8文字以上、32文字以内で入力してください");
				}
				if(!this.getPassword().matches("[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*")){
					passwordErrorMsgList.add("登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です");
				}
				
			}
			
			// パスワード（確認用）チェック
			if(this.getRePassword() == null){
				rePasswordErrorMsgList.add("8文字以上、32文字以内で入力してください");
			}else{
				if(this.getRePassword().length() <= 8 || this.getRePassword().length() > 32 ){
					rePasswordErrorMsgList.add("8文字以上、32文字以内で入力してください");
				}
				if(!this.getRePassword().matches("[a-zA-Z0-9\\#\\$\\%\\&\\_\\&.]*")){
					rePasswordErrorMsgList.add("登録可能な文字は「英字」と「数字」と「記号（「#」、「$」、「%」、「&」、「_」、「.」）」です");
				}
			}
			
			// パスワード と　パスワード（確認用）の相関チェック
			if(passwordErrorMsgList.size() == 0 || rePasswordErrorMsgList.size() == 0){
				if(!this.getPassword().equals(this.getRePassword())){
					rePasswordErrorMsgList.add("パスワード と パスワード（確認用）の入力値が一致しません");
				}
			}
		}
		
		if(passwordErrorMsgList.size() > 0){
			map.put("password", passwordErrorMsgList);
		}
		if(rePasswordErrorMsgList.size() > 0){
			map.put("rePassword", rePasswordErrorMsgList);
		}
	
		return map;
	}
	
	
	
}
