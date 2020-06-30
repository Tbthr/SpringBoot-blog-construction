package com.lyq.blog.service;


import com.lyq.blog.mapper.UserMapper;
import com.lyq.blog.model.User;
import com.lyq.blog.util.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl {

    @Resource
    private UserMapper userMapper;

    public User checkUser(String username,String password){
        return userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
    }

}
