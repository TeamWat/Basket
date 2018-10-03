package jp.wat.basket.config;

import jp.wat.basket.framework.security.MyAuthenticationFailureHandler;
import jp.wat.basket.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security設定クラス.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                            "/images/**",
                            "/css/**",
                            "/javascript/**",
                            "/webjars/**");
    }

	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認可の設定（ROLEは SpringSecurityがROLE_ をつけてチェックをする仕様のため「ROLE_」を除去した値を設定）
        http.authorizeRequests()
            .antMatchers("/", "/index").permitAll() // indexは全ユーザーアクセス許可
            .antMatchers("/user*", "/user/*").hasAnyRole("OFFICER", "ADMIN") // 利用者設定
            .antMatchers("/*").hasAnyRole("PUBLIC", "OFFICER", "ADMIN") // 上記以外はROLEがないとアクセス不可
            .anyRequest().authenticated();

        // ログイン設定
        http.formLogin()
            .loginProcessingUrl("/login")   // 認証処理のパス
            .loginPage("/index")            // ログインフォームのパス
            .failureHandler(new MyAuthenticationFailureHandler())       // 認証失敗時に呼ばれるハンドラクラス
            .defaultSuccessUrl("/top",true)     // 認証成功時の遷移先
            .usernameParameter("userId").passwordParameter("password")  // ユーザー名、パスワードのパラメータ名
            .and();

        // ログアウト設定
        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))       // ログアウト処理のパス
            .logoutSuccessUrl("/index")
            .invalidateHttpSession(true);                                        // ログアウト完了時のパス
    }
	
	 @Configuration
	    protected static class AuthenticationConfiguration
	    extends GlobalAuthenticationConfigurerAdapter {
		 
	        @Autowired
	        UserDetailsServiceImpl userDetailsService;

	        @Override
	        public void init(AuthenticationManagerBuilder auth) throws Exception {
	        	
	            // 認証するユーザーを設定する
	            auth.userDetailsService(userDetailsService)
	            // 入力値をbcryptでハッシュ化した値でパスワード認証を行う
	            .passwordEncoder(new BCryptPasswordEncoder());
	        }
	        
	 }
	
}
