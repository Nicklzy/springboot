package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserMapper userMapper;
    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        save("nick", "123456");
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(String username, String password) {
        userMapper.save(username,bCryptPasswordEncoder.encode(password));
    }

    public User getUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " 不存在！");
        }

        return new org.springframework.security.core.userdetails.User(username, user.getEncryptedPassword(), Collections.emptyList());
    }
}
