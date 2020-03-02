package com.lyq.blog.repository;

import com.lyq.blog.model.LinkComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkCommentRepository extends JpaRepository<LinkComment,Long>{
    List<LinkComment> findAllByParentCommentNull(Sort sort);
}
