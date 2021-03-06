package hello.controller;

import hello.entity.LoginResult;
import hello.entity.Result;
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
            return LoginResult.success("用户没有登录", false);
        } else {
            return LoginResult.success(null, true, loggedInUser);
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.getUserByUserName(username);
        if (loggedInUser == null) {
            return LoginResult.fail("用户没有登录");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.success("success", false);
        }
    }


    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        if (username == null || password == null) {
            return LoginResult.fail("username/password == null");
        }
        if (username.length() < 1 || username.length() > 15) {
            return LoginResult.fail("invalid username");
        }
        if (password.length() < 6 || password.length() > 16) {
            return LoginResult.fail("invalid password");
        }
        User user = userService.getUserByUserName(username);
        if (user == null) {
            userService.save(username, password);
            return LoginResult.success("success", false);
        } else {
            return LoginResult.fail("user already exist");
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
            return LoginResult.fail("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success("登录成功", true, userService.getUserByUserName(username));
        } catch (BadCredentialsException e) {
            return LoginResult.fail("用户名或密码不正确");
        }

    }

}
