package jp.wat.basket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class BasketApplication {

	public static void main(String[] args) {
		
        SpringApplication application = new SpringApplication(BasketApplication.class);
        ApplicationContext context = application.run(args);

	}
}
