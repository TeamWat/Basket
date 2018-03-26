package jp.wat.basket.entity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class UserMaster {
	
	private int user_id;		// ID
	private String user_name;	// ユーザー名
	private String mail;		// メールアドレス
	private String role;		// 権限
	
	/*--- 共通項目 ---*/
	private String regist_time;	// 登録日時
	private String regist_user;	// 登録ユーザー
	private String update_time;	// 更新日時
	private String update_user;	// 更新ユーザー
	private boolean delete_flg;	// 削除フラグ
	/*--- 共通項目 ---*/

	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRegist_time() {
		return regist_time;
	}
	public void setRegist_time(String regist_time) {
		this.regist_time = regist_time;
	}
	public String getRegist_user() {
		return regist_user;
	}
	public void setRegist_user(String regist_user) {
		this.regist_user = regist_user;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public boolean getDelete_flg() {
		return delete_flg;
	}
	public void setDelete_flg(boolean delete_flg) {
		this.delete_flg = delete_flg;
	}

	
}
