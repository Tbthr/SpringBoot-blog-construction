package com.lyq.blog.service;

import com.lyq.blog.NotFoundExcepiton;
import com.lyq.blog.model.Tag;
import com.lyq.blog.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class TagServiceImpl {

    @Resource
    TagRepository tagRepository;

    public Tag saveTag(Tag Tag){
        return tagRepository.save(Tag);
    }

    public Tag getTag(Long id){
        return tagRepository.findById(id).get();
    }

    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }

    public Page<Tag> listTag(Pageable pageable){
        return tagRepository.findAll(pageable);
    }

    public Tag updateTag(Long id,Tag Tag){
        try {
            tagRepository.findById(id);
        } catch (IllegalArgumentException e){
            throw new NotFoundExcepiton("不存在该标签");
        }
        return tagRepository.save(Tag);
    }

    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }

}
