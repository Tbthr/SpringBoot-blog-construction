package com.lyq.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyq.blog.mapper.TagMapper;
import com.lyq.blog.model.Tag;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl {

    @Resource
    TagMapper tagMapper;

    public Long countTags() {
        return tagMapper.sum();
    }

    public Long countBlogs(Long id) {
        return tagMapper.blogs_sum(id);
    }

    public Long saveTag(Tag Tag) {
        return tagMapper.save(Tag);
    }

    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }

    public int updateTag(Long id, Tag Tag) {
        return tagMapper.update(id, Tag.getName());
    }

    public Tag findById(Long id) {
        return tagMapper.findById(id);
    }

    public Tag findByName(String name) {
        return tagMapper.findByName(name);
    }

    public List<Tag> getAllTags() {
        return tagMapper.findAll();
    }

    public PageInfo<Tag> listTag(int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<>(tagMapper.findAll());
    }

    public List<Tag> listTagTop() {
        return tagMapper.findTop();
    }

    public List<String> String_List(String Names) { // "aa,bb,cc" to list[aa,bb,cc]
        List<String> list = new ArrayList<>();
        if (Names != null && !Names.equals("")) {
            String[] strings = Names.split(",");
            list.addAll(Arrays.asList(strings));
        }
        return list;
    }

    public List<Tag> getTags(String Names) {
        List<String> list = String_List(Names);
        List<Tag> tags = new ArrayList<>();
        for (String s : list) {
            Tag tag = tagMapper.findByName(s);
            if (tag == null) {
                Tag t = new Tag(s);
                tagMapper.save(t);
                tags.add(tagMapper.findById(t.getId()));
            } else {
                tags.add(tag);
            }
        }
        return tags;
    }
}
