package io.muic.designpattern.services;


import io.muic.designpattern.configurations.SecurityConfiguration;
import io.muic.designpattern.model.User;
import io.muic.designpattern.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
