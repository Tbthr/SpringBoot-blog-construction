package com.lyq.blog.web;

import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.CommentServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class IndexShowController {
    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TypeServiceImpl typeService;
    @Resource
    private TagServiceImpl tagService;
    @Resource
    private CommentServiceImpl commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/search") // 翻页功能待解决，故设为 100
    public String search(@PageableDefault(size = 100, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog",blogService.getAndConvert(id));
        model.addAttribute("comments",commentService.listCommentByBlogId(id));
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }
}