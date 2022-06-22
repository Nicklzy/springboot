package hello;

import hello.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HelloController {
    @Inject
    public HelloController(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;


    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}