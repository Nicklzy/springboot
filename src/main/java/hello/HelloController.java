package hello;

import hello.service.OrderService;
import hello.service.User;
import hello.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HelloController {
    @Inject
    public HelloController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @GetMapping("/")
    public User index() {
        return this.userService.getUserById(1);
    }

}