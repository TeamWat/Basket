package jp.wat.basket.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "members")
public class Member {
	
	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer memberId;
	private Integer nendo;
	private Integer teamKubun;
	private String memberName;
	private String memberNameKn;
	private Integer no;
	private Integer grade;
	private Integer registUser;
	private Timestamp registTime;
	private Integer updateUser;
	private Timestamp updateTime;
	private Integer deleteFlg;
	
	// JPA requirement
    protected Member() {}

	public Integer getMemberId() {
		return memberId;
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

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getRegistUser() {
		return registUser;
	}

	public void setRegistUser(Integer registUser) {
		this.registUser = registUser;
	}

	public Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Integer deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	
	
}
