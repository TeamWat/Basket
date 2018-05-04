package jp.wat.basket.framework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class MyAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	/* (非 Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler
     * #onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException authenticationException)
                    throws IOException, ServletException {

        String errorId = "";
        // ExceptionからエラーIDをセットする
//        if(authenticationException instanceof BadCredentialsException){
//            errorId = SampleErrorMessageMap.MSG_ERROR_0001;
//        }

        // ログイン画面にリダイレクトする
        
		System.out.println("ログイン失敗");
		
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/"
                + httpServletRequest.getParameter("identifier") + "/index?error=" + errorId);
    }

}
