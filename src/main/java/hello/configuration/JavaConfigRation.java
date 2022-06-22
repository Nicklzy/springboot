package hello.configuration;

import hello.service.OrderService;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfigRation {
    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(userService());
    }
}
