package hello.service;

import hello.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {
    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.findUserById(id);
    }
}
