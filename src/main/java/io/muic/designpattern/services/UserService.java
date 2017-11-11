package io.muic.designpattern.services;


import io.muic.designpattern.model.User;

public interface UserService {
   User findUserByUsername(String username);
   void saveUser(User user);

}