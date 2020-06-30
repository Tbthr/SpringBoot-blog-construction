package com.lyq.blog.web;

import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.CommentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class BlogShowController {
    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private CommentServiceImpl commentService;

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        return "blog";
    }
}
