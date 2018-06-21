package jp.wat.basket.common;

import org.springframework.stereotype.Component;

@Component("enum")
public class Enum {

	/*
	 * チーム名のEnum
	 */
	public enum EnumTeamKubun implements Encodable<Integer>{

	    BIG (1,"ビッグハリケーン", "BIG"),
	    YABEDAN (2, "ヤベダン" , "YB");
	    
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
	
	
	/*
	 * 学年のEnum
	 */
	public enum EnumGrade implements Encodable<Integer>{

	    ONE   (1, "１年生", "1"),
	    TWO   (2, "２年生", "2"),
	    THREE (3, "３年生", "3"),
	    FOUR  (4, "４年生", "4"),
	    FIVE  (5, "５年生", "5"),
	    SIX   (6, "６年生", "6");

	    
	    /** デコーダー */
	    private static final Decoder<Integer, EnumGrade> DECODER = Decoder.create(values());
	  
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
	    private EnumGrade(Integer code, String name, String sname) {
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
	    public static EnumGrade decode(Integer code) {
	        return DECODER.decode(code);
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public String getSName() {
	        return sname;
	    }
	}
	
	/*
	 * 背番号のEnum
	 */
	public enum EnumSebango implements Encodable<Integer>{


	    FOUR  (4, "　４", "4"),
	    FIVE  (5, "　５", "5"),
	    SIX   (6, "　６", "6"),
	    SEVEN (7, "　７", "7"),
	    EIGHT (8, "　８", "8"),
	    NINE  (9, "　９", "9"),
	    TEN   (10, "１０", "10"),
	    ELEVEN   (11, "１１", "11"),
	    TWELVE   (12, "１２", "12"),
	    THIRTEEN  (13, "１３", "13"),
	    FOURTEEN  (14, "１４", "14"),
	    FIFTEEN   (15, "１５", "15"),
	    SIXTEEN   (16, "１６", "16"),
	    SEVENTEEN  (17, "１７", "17"),
	    EIGHTEEN   (18, "１８", "18"),
	    NINETEEN   (19, "１９", "19"),
	    MARU_FOUR  (104, "　④", "④"),
	    MARU_FIVE  (105, "　⑤", "⑤"),
	    MARU_SIX   (106, "　⑥", "⑥"),
	    MARU_SEVEN (107, "　⑦", "⑦"),
	    MARU_EIGHT (108, "　⑧", "⑧"),
	    MARU_NINE  (109, "　⑨", "⑨"),
	    MARU_TEN   (110, "　⑩", "⑩"),
	    MARU_ELEVEN   (111, "　⑪", "⑪"),
	    MARU_TWELVE   (112, "　⑫", "⑫"),
	    MARU_THIRTEEN  (113, "　⑬", "⑬"),
	    MARU_FOURTEEN  (114, "　⑭", "⑭"),
	    MARU_FIFTEEN   (115, "　⑮", "⑮"),
	    MARU_SIXTEEN   (116, "　⑯", "⑯"),
	    MARU_SEVENTEEN  (117, "　⑰", "⑰"),
	    MARU_EIGHTEEN   (118, "　⑱", "⑱"),
	    MARU_NINETEEN   (119, "　⑲", "⑲");
	    
	    /** デコーダー */
	    private static final Decoder<Integer, EnumSebango> DECODER = Decoder.create(values());
	  
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
	    private EnumSebango(Integer code, String name, String sname) {
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
	    public static EnumSebango decode(Integer code) {
	        return DECODER.decode(code);
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public String getSName() {
	        return sname;
	    }
	}
	
	/*
	 * ロールのEnum
	 */
	public enum EnumRole implements Encodable<Integer>{

	    PUBLIC   (1, "一般", "ROLE_PUBLIC"),
	    OFFICER  (2, "役員", "ROLE_OFFICER"),
	    ADMIN    (9, "管理者", "ROLE_ADMIN");

	    
	    /** デコーダー */
	    private static final Decoder<Integer, EnumRole> DECODER = Decoder.create(values());
	  
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
	    private EnumRole(Integer code, String name, String sname) {
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
	    public static EnumRole decode(Integer code) {
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
