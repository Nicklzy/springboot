package hello.configuration;

import hello.mapper.UserMapper;
import hello.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfigRation {
    @Bean
    public UserService userService(UserMapper userMapper) {
        return new UserService(userMapper);
    }

//    @Bean
//    public OrderService orderService() {
//        return new OrderService(userService());
//    }
}
