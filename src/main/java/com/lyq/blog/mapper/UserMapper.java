package com.lyq.blog.mapper;


import com.lyq.blog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(String username, String password);
}
