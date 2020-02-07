package com.lyq.blog.repository;

import com.lyq.blog.model.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {
    Blog findByTitle(String title);

    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE blog SET appreciation = ?1, commentabled = ?2, " +
            "content = ?3, create_time = ?4, " +
            "first_picture = ?5, flag = ?6, " +
            "published = ?7, recommend = ?8, share_statement = ?9, " +
            "title = ?10, update_time = ?11, " +
            "views = ?12, description = ?13 WHERE id = ?14",nativeQuery = true)
    void update(boolean appreciation, boolean commentabled, String content, Date createTime,
                   String firstPicture,String flag, boolean published,boolean recommend,
                   boolean shareStatement,String title,Date updateTime,Integer views,
                   String description,Long id);
}
