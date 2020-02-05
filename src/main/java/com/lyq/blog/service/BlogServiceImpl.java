package com.lyq.blog.service;

import com.lyq.blog.NotFoundExcepiton;
import com.lyq.blog.model.Blog;
import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BlogServiceImpl {

    @Resource
    BlogRepository blogRepository;

    public Blog saveBlog(Blog blog){
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    public Blog getBlog(Long id){
        return blogRepository.findById(id).get();
    }

    public Blog findByName(String title){
        return blogRepository.findByTitle(title);
    }

    public Page<Blog> listBlog(Pageable pageable, BlogSearch blog){
        return blogRepository.findAll((Specification<Blog>) (root, query, cb) -> {
            List<Predicate> predicates=new ArrayList<>();
            if (blog.getTitle() != null && !("".equals(blog.getTitle())) ){
                predicates.add(cb.like(root.get("title"),"%"+blog.getTitle()+"%"));
            }
            if (blog.getTypeId() != null){
                predicates.add(cb.equal(root.<String>get("type").get("id"),blog.getTypeId()));
            }
            if (blog.isRecommend()){
                predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return null;
        },pageable);
    }

    public Blog updateBlog(Long id,Blog blog){
        if (!blogRepository.existsById(id)){
            throw new NotFoundExcepiton("不存在该博客");
        }
        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id){
        blogRepository.deleteById(id);
    }

}
