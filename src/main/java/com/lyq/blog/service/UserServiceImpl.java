package com.lyq.blog.service;


import com.lyq.blog.model.User;
import com.lyq.blog.repository.UserRepository;
import com.lyq.blog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl {

    @Resource
    private UserRepository userRepository;

    public User checkUser(String username,String password){

        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

}
