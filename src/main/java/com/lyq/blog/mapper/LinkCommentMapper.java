package com.lyq.blog.mapper;

import com.lyq.blog.model.LinkComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LinkCommentMapper {

    @Insert("insert into link_comment" +
            "(admin_comment,avatar,content,create_time,email,nickname,parent_comment_id)" +
            "values(#{adminComment,jdbcType=BIT},#{avatar},#{content},#{createTime},#{email},#{nickname,jdbcType=VARCHAR},#{parentLinkCommentId,jdbcType=BIGINT})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(LinkComment linkComment);

    @Select("select * from link_comment where id = #{id}")
    LinkComment setParentById(Long id);

    @Select("select * from link_comment where parent_comment_id = #{id}")
    @Result(column = "parent_comment_id", property = "parentLinkCommentId")
    List<LinkComment> findByParentId(Long id);

    List<LinkComment> findAllByParentLinkCommentNull();
}
