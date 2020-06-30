package com.lyq.blog.web;

import com.lyq.blog.model.Tag;
import com.lyq.blog.model.Type;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.CommentServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public String index(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "8") int rows,
                        Model model) {
        List<Type> types = typeService.listTypeTop();
        Map<Type, Long> typeMap = new LinkedHashMap<>();
        for (Type t : types) {
            typeMap.put(t, typeService.countBlogs(t.getId()));
        }
        List<Tag> tags = tagService.listTagTop();
        Map<Tag, Long> tagMap = new LinkedHashMap<>();
        for (Tag t : tags) {
            tagMap.put(t, tagService.countBlogs(t.getId()));
        }
        model.addAttribute("page", blogService.listAllBlog(page, rows));
        model.addAttribute("types", typeMap);
        model.addAttribute("tags", tagMap);
        model.addAttribute("typesCount", typeService.countTypes());
        model.addAttribute("tagsCount", tagService.countTags());
        model.addAttribute("commentsCount", commentService.countComments());
        return "index";
    }

    @GetMapping("/search") // 翻页功能待解决，故设为 100
    public String search(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "100") int rows,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listAllBlogByQuery("%" + query + "%", page, rows));
        model.addAttribute("query", query);
        return "search";
    }
}