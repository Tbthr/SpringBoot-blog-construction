package com.lyq.blog.repository;

import com.lyq.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {
    Blog findByTitle(String title);
}
