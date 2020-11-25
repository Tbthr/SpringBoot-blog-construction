package com.lyq.blog.mapper;

import com.lyq.blog.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface CommentMapper {

    @Select("select count(*) from comment")
    Long sum();

    @Insert("insert into comment" +
            "(create_time,avatar,email,nickname,content,blog_id,parent_comment_id,admin_comment)" +
            "values(#{createTime},#{avatar,jdbcType=VARCHAR},#{email},#{nickname},#{content},#{blogId},#{parentCommentId,jdbcType=BIGINT},#{adminComment,jdbcType=BIT})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment setParentById(Long id);

    @Select("select * from comment where parent_comment_id = #{id}")
    List<Comment> findByParentId(Long id);

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId);
}
