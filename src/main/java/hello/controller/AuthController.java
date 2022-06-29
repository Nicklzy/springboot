package hello.controller;

import hello.entity.User;
import hello.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedInUser = userService.getUserByUserName(authentication == null ? null : authentication.getName());
        if (loggedInUser == null) {
            return new Result("ok", "用户没有登录", false);
        } else {
            return new Result("ok", null, true, loggedInUser);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUserName(username);
        if (loggedInUser == null) {
            return new Result("fail", "用户没有登录", false);
        } else {
            SecurityContextHolder.clearContext();
            return new Result("ok", "success", false);
        }
    }


    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username == null || password == null) {
            return new Result("fail", "username/password == null", false);
        }
        if (username.length() < 1 || username.length() > 15) {
            return new Result("fail", "invalid username", false);
        }
        if (password.length() < 6 || password.length() > 16) {
            return new Result("fail", "invalid password", false);
        }
        User user = userService.getUserByUserName(username);
        if (user == null) {
            userService.save(username, password);
            return new Result("ok", "success", false);
        } else {
            return new Result("fail", "user already exist", false);
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("fail", "用户不存在", false);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return new Result("ok", "登录成功", true, userService.getUserByUserName(username));
        } catch (BadCredentialsException e) {
            return new Result("fail", "用户名或密码不正确", false);
        }

    }

    private static class Result {


        public Result(String status, String msg, boolean isLogin) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
        }

        public Result(String status, String msg, boolean isLogin, Object data) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = data;
        }

        String status;
        String msg;

        public Object getData() { //之前返回里没有data就是因为没有配置getter
            return data;
        }

        Object data;

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isLogin() {
            return isLogin;
        }

        boolean isLogin;

    }
}
