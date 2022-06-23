package hello.service;

import hello.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        save("nick", "123456");
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Map<String, String> userPasswordMap = new HashMap<>();

    public void save(String username, String password) {
        userPasswordMap.put(username, bCryptPasswordEncoder.encode(password));
    }

    public String getPassword(String username) {
        return userPasswordMap.get(username);
    }

    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userPasswordMap.containsKey(username)) {
            throw new UsernameNotFoundException(username + " 不存在！");
        }
        String password = userPasswordMap.get(username);

        return new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList());
    }
}
