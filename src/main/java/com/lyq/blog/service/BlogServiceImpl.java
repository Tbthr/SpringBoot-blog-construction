package com.lyq.blog.service;

import com.lyq.blog.NotFoundExcepiton;
import com.lyq.blog.model.Blog;
import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.repository.BlogRepository;
import com.lyq.blog.util.MarkdownUtils;
import com.lyq.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Date date = new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    public Blog getAndConvert(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new NotFoundExcepiton("该博客不存在");
        }
        Blog blog = blogRepository.getOne(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id); //浏览次数 + 1
        return b;
    }

    public Blog findByName(String title) {
        return blogRepository.findByTitle(title);
    }

    public Page<Blog> listBlog(Pageable pageable, BlogSearch blog) {
        return blogRepository.findAll((Specification<Blog>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (blog.getTitle() != null && !("".equals(blog.getTitle()))) {
                predicates.add(cb.like(root.get("title"), "%" + blog.getTitle() + "%"));
            }
            if (blog.getTypeId() != null){
                predicates.add(cb.equal(root.<String>get("type").get("id"),blog.getTypeId()));
            }
            if (blog.isRecommend()){
                predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public Blog updateBlog(Long id,Blog blog){
        if (!blogRepository.existsById(id)) {
            throw new NotFoundExcepiton("不存在该博客");
        }
        Blog b=blogRepository.getOne(id);
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }
}
