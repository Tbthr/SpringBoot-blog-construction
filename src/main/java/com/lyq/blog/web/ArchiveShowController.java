package com.lyq.blog.web;

import com.lyq.blog.service.BlogServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class ArchiveShowController {
    @Resource
    private BlogServiceImpl blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogsCount",blogService.countBlogs());
        return "archives";
    }
}
