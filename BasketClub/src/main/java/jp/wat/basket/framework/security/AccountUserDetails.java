package jp.wat.basket.framework.security;

import jp.wat.basket.entity.LoginUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


public class AccountUserDetails extends User {
		
	/**
     * ログインユーザー
     */
    private final LoginUser loginUser;


    /**
     * コンストラクタ
     * @param user
     */
    public AccountUserDetails(LoginUser user, String role) {
        // スーパークラスのユーザーID、パスワードに値をセットする
        // 実際の認証はスーパークラスのユーザーID、パスワードで行われる
        //super(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    	super(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList(role));
        this.loginUser = user;
    }
    
    /**
     *
     * @return
     */
    public LoginUser getUser() {
        return loginUser;
    }
   
}
