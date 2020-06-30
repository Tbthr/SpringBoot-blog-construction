package com.lyq.blog.mapper;

import com.lyq.blog.model.Link;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LinkMapper {

    @Insert("insert into link(friend,blog_url,description,img_url,name) values(#{friend,jdbcType=BIT},#{blogUrl,jdbcType=VARCHAR},#{description},#{imgUrl},#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long save(Link link);

    @Delete("delete from link where id = #{id}")
    void deleteById(Long id);

    @Update("update link set friend = #{friend,jdbcType=BIT},blog_url = #{blogUrl},description = #{description},img_url = #{imgUrl,jdbcType=VARCHAR},name = #{name} where id = #{id}")
    int update(Link link);

    @Select("select * from link where id = #{id}")
    Link findById(Long id);

    @Select("select * from link where name = #{name}")
    Link findByName(String name);

    @Select("select * from link")
    List<Link> findAll();

    @Select("select * from link where friend = true")
    List<Link> findAllByFriendTrue();

    @Select("select * from link where friend = false")
    List<Link> findAllByFriendFalse();

    @Select("select * from link order by friend and id")
    List<Link> findAllByFriendAndId();
}
