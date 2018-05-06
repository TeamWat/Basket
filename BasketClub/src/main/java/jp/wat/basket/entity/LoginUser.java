package jp.wat.basket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_users")
public class LoginUser {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String userId;		// ID
	private String password;		// パスワード
	private String userName;		// ユーザー名
	private String userNameKn;	// ユーザー名カナ
	private String role;			// 権限
	private String mail;			// メールアドレス	
	
	/*--- 共通項目 ---*/
	private String registTime;	// 登録日時
	private String registUser;	// 登録ユーザー
	private String updateTime;	// 更新日時
	private String updateUser;	// 更新ユーザー
	private boolean deleteFlg;	// 削除フラグ
	/*--- 共通項目 ---*/
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getRegistUser() {
		return registUser;
	}
	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public boolean isDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}