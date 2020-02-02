package com.lyq.blog.repository;


import com.lyq.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{

    User findByUsernameAndPassword(String username,String password);
}