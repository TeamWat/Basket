package jp.wat.basket.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schedule_details")
public class ScheduleDetail {
	
	@Id
	@Column(name="seq")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer seq;
	private Integer nendo;
	private Integer month;
	private Integer day;
	private String week;
	private String timeFrame;
	private String place;
	private String program;
	private String tantouParent;
	private String tantouSupporter;
	private String tantouYakuin;
	private String bikou;
	private byte gameFlg;
	private String registUser;
	private Timestamp registTime;
	private String updateUser;
	private Timestamp updateTime;
	
	// JPA requirement
    protected ScheduleDetail() {}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getNendo() {
		return nendo;
	}

	public void setNendo(Integer nendo) {
		this.nendo = nendo;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getTantouParent() {
		return tantouParent;
	}

	public void setTantouParent(String tantouParent) {
		this.tantouParent = tantouParent;
	}

	public String getTantouSupporter() {
		return tantouSupporter;
	}

	public void setTantouSupporter(String tantouSupporter) {
		this.tantouSupporter = tantouSupporter;
	}

	public String getTantouYakuin() {
		return tantouYakuin;
	}

	public void setTantouYakuin(String tantouYakuin) {
		this.tantouYakuin = tantouYakuin;
	}

	public String getBikou() {
		return bikou;
	}

	public void setBikou(String bikou) {
		this.bikou = bikou;
	}

	public byte getGameFlg() {
		return gameFlg;
	}

	public void setGameFlg(byte gameFlg) {
		this.gameFlg = gameFlg;
	}

	public String getRegistUser() {
		return registUser;
	}

	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}

	public Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
