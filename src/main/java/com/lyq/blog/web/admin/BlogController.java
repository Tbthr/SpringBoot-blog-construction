package com.lyq.blog.web.admin;

import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {

    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TypeServiceImpl typeService;

    @GetMapping
    public String blogs(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, BlogSearch blog, Model model){
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, BlogSearch blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }
}
