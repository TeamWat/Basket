package jp.wat.basket.service;

import jp.wat.basket.Repository.LoginUserRepository;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.framework.security.AccountUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private LoginUserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		
		// 認証を行うユーザー情報を格納する
        LoginUser user = null;
        
        try {
            // 入力したユーザーIDから認証を行うユーザー情報を取得する
        	user = repository.findById(userId);
        	
        	System.out.println("UserDetailsServiceImpl.java : 入力したユーザーIDをもとにDBからユーザー情報を取得する");
        	if(user == null){
        		System.out.println("ユーザー情報取得できなかった");
        	}else{
        		System.out.println("ユーザー情報取得できた");
        	}
        	
        	
            // 処理内容は省略
        } catch (Exception e) {
            // 取得時にExceptionが発生した場合
        	System.out.println("例外発生：" + e);
            throw new UsernameNotFoundException("It can not be acquired User");
        }

        // ユーザー情報を取得できなかった場合
        if(user == null){
        	System.out.println("null");
            throw new UsernameNotFoundException("User not found for login id: " + userId);
        }

        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        // return new AccountUserDetails(user);
        return new AccountUserDetails(user, user.getRole());
	}

}
