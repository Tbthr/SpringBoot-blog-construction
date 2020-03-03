package com.lyq.blog.repository;

import com.lyq.blog.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByFriendTrue();

    List<Link> findAllByFriendFalse();

    Link findByName(String name);
}
