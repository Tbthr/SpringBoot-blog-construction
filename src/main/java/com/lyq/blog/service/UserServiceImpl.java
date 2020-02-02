package com.lyq.blog.service;


import com.lyq.blog.model.User;
import com.lyq.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username,String password){

        User user=userRepository.findByUsernameAndPassword(username,password);
        return user;
    }

}
