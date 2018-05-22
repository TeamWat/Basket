package jp.wat.basket.model;

import jp.wat.basket.entity.Member;

public class MemberViewModel extends Member{

	// memberクラスでは保持していないチーム名などはこちらのクラスで保持する
	
	private String TeamName;
	private String DisplayNo;

	public String getTeamName() {
		return TeamName;
	}

	public void setTeamName(String teamName) {
		TeamName = teamName;
	}

	public String getDisplayNo() {
		return DisplayNo;
	}

	public void setDisplayNo(String displayNo) {
		DisplayNo = displayNo;
	}
	
	
}
