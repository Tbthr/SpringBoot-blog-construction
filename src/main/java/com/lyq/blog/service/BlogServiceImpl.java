package com.lyq.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyq.blog.NotFoundExcepiton;
import com.lyq.blog.mapper.BlogMapper;
import com.lyq.blog.model.Blog;
import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.model.Blog_tags;
import com.lyq.blog.model.Tag;
import com.lyq.blog.util.MarkdownUtils;
import com.lyq.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl {

    @Resource
    BlogMapper blogMapper;

    // 返回博客总数
    public Long countBlogs() {
        return blogMapper.sum();
    }

    // 保存博客
    public Long saveBlog(Blog blog) {
        Date date = new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0);
        return blogMapper.save(blog);
    }

    // 保存博客标签关系
    public void saveBlogTags(Long blogId, List<Tag> tags) {
        Long tagId;
        for (Tag tag : tags) {
            tagId = tag.getId();
            if (blogMapper.findBlogTagsByBlogIdAndTagId(blogId, tagId) == null) {
                blogMapper.saveBlogTags(blogId, tagId);
            }
        }
    }

    // 更新博客标签关系
    public void updateBlogTags(Long blogId, List<Tag> tags) {
        Long tagId;
        List<Blog_tags> blogTags = blogMapper.findBlogTagsByBlogId(blogId);//原来的关系
        for (Blog_tags bt : blogTags) {
            boolean delete = true;
            for (Tag t : tags) {
                if (bt.getTagsId().equals(t.getId())) {
                    delete = false;
                }
                tagId = t.getId();
                if (blogMapper.findBlogTagsByBlogIdAndTagId(blogId, tagId) == null) {
                    blogMapper.saveBlogTags(blogId, tagId); //把新关系加入
                }
            }
            if (delete) {
                blogMapper.deleteBlogTagsByBlogIdAndTagId(blogId, bt.getTagsId());
            }
        }
    }

    public void deleteBlog(Long id) {
        blogMapper.deleteById(id);
    }

    public void deleteBlogTagsByBlogId(Long blogId) {
        blogMapper.deleteBlogTagsByBlogId(blogId);
    }

    public int updateBlog(Long id, Blog blog) {
        Map<String, Object> map = new HashMap<>();
        map.put("bId", id);
        Blog b = blogMapper.findByIF(map).get(0);
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));//source,target,ignoreProperties
        b.setUpdateTime(new Date());
        return blogMapper.update(b);
    }

    public Blog findById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("bId", id);
        return blogMapper.findByIF(map).get(0);
    }

    // 转换 md->html
    public Blog getAndConvert(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("bId", id);
        Blog blog = blogMapper.findByIF(map).get(0);
        if (blog == null) {
            throw new NotFoundExcepiton("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogMapper.updateViews(id); //浏览次数 + 1
        return b;
    }

    public Blog findByName(String title) {
        Map<String, Object> map = new HashMap<>();
        map.put("bTitle", title);
        return blogMapper.findByIF(map).get(0);
    }

    // 所有博客、时间倒序、分页展示
    public PageInfo<Blog> listAllBlog(int page, int rows) {
        PageHelper.startPage(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("oderByUpdateTimeDesc", true);
        return new PageInfo<>(blogMapper.findByIF(map));
    }

    // 根据分类ID、返回博客列表
    public PageInfo<Blog> listAllBlogByTypeId(Long typeId, int page, int rows) {
        PageHelper.startPage(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", typeId);
        map.put("oderByUpdateTimeDesc", true);
        return new PageInfo<>(blogMapper.findByIF(map));
    }

    // 根据标签ID、返回博客列表
    public PageInfo<Blog> listAllBlogByTagId(Long tagId, int page, int rows) {
        PageHelper.startPage(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("tagId", tagId);
        map.put("oderByUpdateTimeDesc", true);
        return new PageInfo<>(blogMapper.findByIF(map));
    }

    // 根据关键字、查询博客
    public PageInfo<Blog> listAllBlogByQuery(String query, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("bTitleLike", query);
        map.put("bContentLike", query);
        return new PageInfo<>(blogMapper.findByIF(map));
    }

    // 根据标题、类型、是否推荐、返回博客列表
    public PageInfo<Blog> listAllBlogBySearch(int page, int rows, BlogSearch blog) {
        PageHelper.startPage(page, rows);
        Map<String, Object> map = new HashMap<>();
        map.put("bTitleLike", blog.getTitle());
        map.put("typeId", blog.getTypeId());
        map.put("bRecommend", blog.isRecommend());
        map.put("oderByUpdateTimeDesc", true);
        return new PageInfo<>(blogMapper.findByIF(map));
    }

    // 时间倒序、展示前n个博客
    public PageInfo<Blog> listRecommendBlogTop(Integer size) {
        PageHelper.startPage(1, size);
        Map<String, Object> map = new HashMap<>();
        map.put("oderByUpdateTimeDesc", true);
        List<Blog> top = blogMapper.findByIF(map);
        return new PageInfo<>(top);
    }

    // 归档、按照年份倒序 分组展示
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            Map<String, Object> mapIF = new HashMap<>();
            mapIF.put("bUpdate_time", year);
            mapIF.put("oderByUpdateTimeDesc", true);
            map.put(year, blogMapper.findByIF(mapIF));
        }
        return map;
    }
}
