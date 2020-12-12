package com.lyq.blog.mapper;

import com.lyq.blog.model.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface TagMapper {

    @Select("select count(*) from tag")
    Long sum();

    @Select("select count(*) from blog_tags where tags_id = #{id}")
    Long blogs_sum(Long id);

    @Insert("insert into tag(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(Tag tag);

    @Delete("delete from tag where id = #{id}")
    void deleteById(Long id);

    @Update("update tag set name = #{name} where id = #{id}")
    int update(@Param("id") Long id, @Param("name") String name);

    @Select("select * from tag where id = #{id}")
    Tag findById(Long id);

    @Select("select * from tag where name = #{name}")
    Tag findByName(String name);

    @Select("select id,name from tag inner join (select tags_id,count(*) as sum from blog_tags group by tags_id) as temp on id = temp.tags_id order by sum desc")
    @Result(column = "id", property = "id")
    @Result(column = "name", property = "name")
    List<Tag> findTop();

    @Select("select * from tag")
    List<Tag> findAll();
}
