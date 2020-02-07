package com.lyq.blog.service;

import com.lyq.blog.NotFoundExcepiton;
import com.lyq.blog.model.Tag;
import com.lyq.blog.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl {

    @Resource
    TagRepository tagRepository;

    public Tag saveTag(Tag Tag) {
        return tagRepository.save(Tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
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
            Tag tag = tagRepository.findByName(s);
            if (tag == null) {
                tag = tagRepository.save(Tag.builder().name(s).build());
            }
            tags.add(tag);
        }
        return tags;
    }

    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    public Page<Tag> listTag(Pageable pageable){
        return tagRepository.findAll(pageable);
    }

    public Tag updateTag(Long id,Tag Tag){
        if (!tagRepository.existsById(id)){
            throw new NotFoundExcepiton("不存在该标签");
        }
        return tagRepository.save(Tag);
    }

    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }

}
