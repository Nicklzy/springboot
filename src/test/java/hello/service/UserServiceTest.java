package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockMapper;

    @InjectMocks
    UserService userService;

    @Test
    public void testSave() {

        Mockito.when(mockEncoder.encode("123456")).thenReturn("encodedPassword");

        userService.save("nick", "123456");

        Mockito.verify(mockMapper).save("nick", "encodedPassword");
    }

    @Test
    public void testGetUserByUserName() {
        userService.getUserByUserName("nick");
        Mockito.verify(mockMapper).findUserByUserName("nick");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nick"));
    }

    @Test
    public void returnUserDetailsWhenUserFound() {
        Mockito.when(mockMapper.findUserByUserName("nick")).thenReturn(new User(123, "nick", "encodedPassword"));
        UserDetails userDetails = userService.loadUserByUsername("nick");

        Assertions.assertEquals("nick", userDetails.getUsername());
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
    }

}