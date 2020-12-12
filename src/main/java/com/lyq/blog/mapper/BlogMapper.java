package com.lyq.blog.mapper;

import com.lyq.blog.model.Blog;
import com.lyq.blog.model.Blog_tags;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface BlogMapper {

    @Select("select count(*) from blog")
    Long sum();

    Long save(Blog blog);

    @Insert("insert into blog_tags(blogs_id,tags_id) values(#{blogs_id},#{tags_id})")
    void saveBlogTags(@Param("blogs_id") Long blogs_id, @Param("tags_id") Long tags_id);

    @Select("select * from blog_tags where blogs_id = #{blogs_id} and tags_id = #{tags_id}")
    Blog_tags findBlogTagsByBlogIdAndTagId(@Param("blogs_id") Long blogs_id,@Param("tags_id") Long tags_id);

    @Select("select * from blog_tags where blogs_id = #{blogId}")
    List<Blog_tags> findBlogTagsByBlogId(@Param("blogId") Long blogId);

    @Delete("delete from blog where id = #{id}")
    void deleteById(Long id);

    @Delete("delete from blog_tags where blogs_id = #{blogId}")
    void deleteBlogTagsByBlogId(Long blogId);

    @Delete("delete from blog_tags where blogs_id = #{blogs_id} and tags_id = #{tags_id}")
    void deleteBlogTagsByBlogIdAndTagId(@Param("blogs_id") Long blogs_id, @Param("tags_id") Long tags_id);

    int update(Blog blog);

    @Update("update blog set views = views + 1 where id = #{id}")
    void updateViews(Long id);

    List<Blog> findByIF(Map<String, Object> map);

    @Select("select Year(update_time) from blog group by update_time order by update_time desc")
    List<String> findGroupYear();
}
