package com.lyq.blog.web;

import com.lyq.blog.service.BlogServiceImpl;
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
@RequestMapping("/")
public class IndexController {

    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TypeServiceImpl typeService;
    @Resource
    private TagServiceImpl tagService;

    @GetMapping
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog")
    public String blogs() {
        return "blog";
    }

    @GetMapping("/blog/{id}")
    public String OneBlog(@PathVariable Long id) {
        return "blog";
    }
}