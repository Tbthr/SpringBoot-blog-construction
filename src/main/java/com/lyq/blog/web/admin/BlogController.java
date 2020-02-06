package com.lyq.blog.web.admin;

import com.lyq.blog.model.Blog;
import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.model.User;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {

    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TypeServiceImpl typeService;
    @Resource
    private TagServiceImpl tagService;

    @GetMapping
    public String AllBlogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, BlogSearch blog, Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                 Pageable pageable, BlogSearch blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/input")
    public String input(Model model) {
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    @GetMapping("/input/{id}")
    public String inputAttachedId(@PathVariable Long id, Model model) {
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("edit","修改");
        Blog blog=blogService.getBlog(id);
        blog.tags_tagsIds();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @PostMapping //add + update
    public String add(Blog blog, RedirectAttributes attributes, HttpSession session) {
        if (blog.getId() == null) {
            blog.setUser((User) session.getAttribute("user"));
        }
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.getTags(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if (b != null) {
            if (blog.isPublished()) {
                attributes.addFlashAttribute("message", "发布成功");
            } else {
                attributes.addFlashAttribute("message", "保存成功");
            }
        } else {
            if (blog.isPublished()) {
                attributes.addFlashAttribute("message", "发布失败");
            } else {
                attributes.addFlashAttribute("message", "保存失败");
            }
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}
