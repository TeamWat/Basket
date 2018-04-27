package jp.wat.basket.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ScheduleDetailForm {
	
	private Integer seq;
	private Integer nendo;
	private Integer month;
	
	@NotNull(message = "入力してください")
	@Min(value=1, message = "有効な日付を入力してください")
	@Max(value=31, message = "有効な日付を入力してください")
	private Integer day;
	
	private String week;
	private String timeFrame;
	private String place;
	private String program;
	private String tantouParent;
	private String tantouSupporter;
	private String tantouYakuin;
	private String bikou;
	private Integer gameFlg;
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
	public Integer getGameFlg() {
		return gameFlg;
	}
	public void setGameFlg(Integer gameFlg) {
		this.gameFlg = gameFlg;
	}
	
}
