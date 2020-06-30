package com.lyq.blog.web;

import com.lyq.blog.model.Tag;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TagShowController {
    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TagServiceImpl tagService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "8") int rows,
                       Model model) {
        List<Tag> tags = tagService.listTagTop();
        if (id == -1) {
            id = tags.get(0).getId();
        }
        Map<Tag, Long> tagMap = new LinkedHashMap<>();
        for (Tag t : tags) {
            tagMap.put(t, tagService.countBlogs(t.getId()));
        }
        model.addAttribute("tags", tagMap);
        model.addAttribute("page", blogService.listAllBlogByTagId(id, page, rows));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}