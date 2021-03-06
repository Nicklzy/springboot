package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authenticationManager)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("??????????????????")));
    }

    @Test
    void testLogin() throws Exception {
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("??????????????????")));

        //??????/auth/login??????
        Map<String, String> userNamePassword = new HashMap<>();
        userNamePassword.put("username", "nick");
        userNamePassword.put("password", "123456");

        Mockito.when(userService.loadUserByUsername("nick")).thenReturn(new User("nick", bCryptPasswordEncoder.encode("123456"), Collections.emptyList()));
        Mockito.when(userService.getUserByUserName("nick")).thenReturn(new hello.entity.User(123, "nick", bCryptPasswordEncoder.encode("123456")));

        MvcResult response = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(userNamePassword)))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("????????????")))
                .andReturn();


        HttpSession session = response.getRequest().getSession();

        // ????????????/auth?????????????????????????????????
        mvc.perform(get("/auth").session((MockHttpSession) session)).andExpect(status().isOk())
                .andExpect(result -> {
                    System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
                    Assertions.assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("nick"));
                });

    }
}