package com.lyq.blog.service;

import com.lyq.blog.NotFoundExcepiton;
import com.lyq.blog.model.Type;
import com.lyq.blog.repository.TypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TypeServiceImpl {

    @Resource
    TypeRepository typeRepository;

    public Type saveType(Type type){
        return typeRepository.save(type);
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }

    public Type getType(Long id){
        return typeRepository.findById(id).get();
    }

    public Type findByName(String name){
        return typeRepository.findByName(name);
    }

    public Page<Type> listType(Pageable pageable){
        return typeRepository.findAll(pageable);
    }

    public Type updateType(Long id,Type type){
        if (!typeRepository.existsById(id)){
            throw new NotFoundExcepiton("不存在该分类");
        }
        return typeRepository.save(type);
    }

    public void deleteType(Long id){
        typeRepository.deleteById(id);
    }

}
