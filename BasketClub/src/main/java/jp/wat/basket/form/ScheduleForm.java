package jp.wat.basket.form;

import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ScheduleForm {
	
	private Integer seq;
	private Integer nendo;
	private Integer month;
	
	@NotNull(message = "入力してください")
	@Min(value=1, message = "有効な日付を入力してください")
	@Max(value=31, message = "有効な日付を入力してください")
	private String col1;
	
	@NotNull(message = "入力してください")
	private String col2;

	private String col3;
	private String col4;
	private String col5;
	private String col6;
	private String col7;
	private String col8;
	private String col9;
	private Integer game_flg;
	
	
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
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getCol6() {
		return col6;
	}
	public void setCol6(String col6) {
		this.col6 = col6;
	}
	public String getCol7() {
		return col7;
	}
	public void setCol7(String col7) {
		this.col7 = col7;
	}
	public String getCol8() {
		return col8;
	}
	public void setCol8(String col8) {
		this.col8 = col8;
	}
	public String getCol9() {
		return col9;
	}
	public void setCol9(String col9) {
		this.col9 = col9;
	}
	public Integer getGame_flg() {
		return game_flg;
	}
	public void setGame_flg(Integer game_flg) {
		this.game_flg = game_flg;
	}
	
}
