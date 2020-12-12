package com.lyq.blog.mapper;

import com.lyq.blog.model.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface TypeMapper {

    @Select("select count(*) from type")
    Long sum();

    @Select("select count(*) from blog where type_id = #{id}")
    Long blogs_sum(Long id);

    @Insert("insert into type(name) values (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(Type type);

    @Delete("delete from type where id = #{id}")
    void deleteById(Long id);

    @Update("update type set name = #{name} where id = #{id}")
    int update(@Param("id") Long id, @Param("name") String name);

    @Select("select * from type where id = #{id}")
    Type findById(Long id);

    @Select("select * from type where name = #{name}")
    Type findByName(String name);

    // 根据包含的博客数量倒序排序、选择前n个类型
    @Select("select id, name from type inner join (select type_id,count(*) as sum from blog group by type_id) as temp on temp.type_id = id order by sum desc")
    @Result(column = "id", property = "id")
    @Result(column = "name", property = "name")
    List<Type> findTop();

    @Select("select * from type")
    List<Type> findAll();
}
