package jp.wat.basket.model;

import jp.wat.basket.entity.User;

public class UserViewModel extends User{

	// userクラスでは保持していないロール名などはこちらのクラスで保持する
	
	private String RoleName;
	private Integer RoleCode;
	
	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public Integer getRoleCode() {
		return RoleCode;
	}

	public void setRoleCode(Integer roleCode) {
		RoleCode = roleCode;
	}	

}
