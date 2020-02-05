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
import java.util.List;

@Service
@Transactional
public class TagServiceImpl {

    @Resource
    TagRepository tagRepository;

    public Tag saveTag(Tag Tag){
        return tagRepository.save(Tag);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    public List<Tag> getTags(String ids){
        return tagRepository.findAllById(convertToList(ids));
    }

    public List<Long> convertToList(String ids){ // 1,2,3 to list[1,2,3]
        List<Long> list=new ArrayList<>();
        if (ids!=null&&!ids.equals("")){
            String[] strings=ids.split(",");
            for (String string : strings) {
                list.add(Long.valueOf(string));
            }
        }
        return list;
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
        if (!tagRepository.existsById(id)){
            throw new NotFoundExcepiton("不存在该标签");
        }
        return tagRepository.save(Tag);
    }

    public void deleteTag(Long id){
        tagRepository.deleteById(id);
    }

}
