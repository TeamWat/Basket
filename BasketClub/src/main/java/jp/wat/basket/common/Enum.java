package jp.wat.basket.common;

import org.springframework.stereotype.Component;

@Component
public class Enum {

	/*
	 * チーム名のEnum
	 */
	public enum EnumTeamKubun implements Encodable<Integer>{

	    BIG (1,"Big Hurricane", "BIG"),
	    YABEDAN (2, "Yabedan" , "YB");
	    
	    /** デコーダー */
	    private static final Decoder<Integer, EnumTeamKubun> DECODER = Decoder.create(values());
	  
	    private final Integer code;  /* コード値 */
	    private final String name;   /* 名称 */
	    private final String sname;  /* 略称 */
	    
	    /**
	     * コンストラクタ.
	     * 
	     * @param code コード値
	     * @param name 名称
	     * @param sname 略称
	     */
	    private EnumTeamKubun(Integer code, String name, String sname) {
	        this.code = code;
	        this.name = name;
	        this.sname = sname;
	    }
	    
	    @Override
	    public Integer getCode() {
	        return code;
	    }
	    
	    /**
	     * コード値からEnumクラスを取得する.
	     * 
	     * @param code コード値
	     * @return 受領形式Enumクラス
	     */
	    public static EnumTeamKubun decode(Integer code) {
	        return DECODER.decode(code);
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public String getSName() {
	        return sname;
	    }
	}
	
	
}
