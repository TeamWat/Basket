package jp.wat.basket.service;

import jp.wat.basket.Repository.LoginUserRepository;
import jp.wat.basket.entity.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class CommonService {
	
	@Autowired
	LoginUserRepository loginUserRepository;
		
	/*
	 * ログインチェックをおこない、ログイン状態の場合はログインユーザー情報を返却する
	 */
	public LoginUser getLoginUser() {
		
		// 認証に成功した後、UserDetailsクラスは org.springframework.security.core.context.SecurityContextHolderに格納される。
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null) {
	    	
	    	// Authenticationオブジェクトから認証ユーザ情報であるUserDetailsオブジェクトを取得する。
	        Object principal = authentication.getPrincipal(); 
	        if (principal instanceof UserDetails) {
	        	
	        	// 認証ユーザー情報のキーであるusernameをもとに対応するLoginUser情報を取得する。
	        	// なお、SecurityConfigクラスで認証ユーザー情報のusername と LoginUserのuserId を対応付けている。
	        	LoginUser loginUser = loginUserRepository.findById( ((UserDetails) principal).getUsername() );
	      
	            return loginUser;
	        }
	    }
	    
		return null;
	}

}
