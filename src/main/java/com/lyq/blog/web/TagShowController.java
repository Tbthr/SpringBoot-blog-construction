package com.lyq.blog.web;

import com.lyq.blog.model.Tag;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TagShowController {
    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TagServiceImpl tagService;

    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id,
                        @PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Tag> tags = tagService.listTagTop(1000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}