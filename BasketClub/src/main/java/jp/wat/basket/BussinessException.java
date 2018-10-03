package jp.wat.basket;

import org.springframework.stereotype.Component;

@Component
public class BussinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BussinessException() {
        super();
    }

    public BussinessException(String message_key) {
        super(message_key); 
    }
}
