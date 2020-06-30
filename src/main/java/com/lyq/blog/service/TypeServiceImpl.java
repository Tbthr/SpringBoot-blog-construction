package com.lyq.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyq.blog.mapper.TypeMapper;
import com.lyq.blog.model.Type;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl {

    @Resource
    TypeMapper typeMapper;

    public Long countTypes() {
        return typeMapper.sum();
    }

    public Long countBlogs(Long id) {
        return typeMapper.blogs_sum(id);
    }

    public Long saveType(Type type) {
        return typeMapper.save(type);
    }

    public void deleteType(Long id) {
        typeMapper.deleteById(id);
    }

    public int updateType(Long id, Type type) {
        return typeMapper.update(id, type.getName());
    }

    public Type findById(Long id) {
        return typeMapper.findById(id);
    }

    public Type findByName(String name) {
        return typeMapper.findByName(name);
    }

    public List<Type> getAllTypes() {
        return typeMapper.findAll();
    }

    public PageInfo<Type> listType(int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<>(typeMapper.findAll());
    }

    public List<Type> listTypeTop() {
        return typeMapper.findTop();
    }
}
