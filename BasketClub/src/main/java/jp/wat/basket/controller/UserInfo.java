package jp.wat.basket.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String startViewName;
    private Integer nendo;
	
	public Integer getNendo() {
		return nendo;
	}
	public void setNendo(Integer nendo) {
		this.nendo = nendo;
	}
	public String getStartViewName() {
		return startViewName;
	}
	public void setStartViewName(String startViewName) {
		this.startViewName = startViewName;
	}

	
}
