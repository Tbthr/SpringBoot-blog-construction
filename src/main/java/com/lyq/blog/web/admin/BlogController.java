package com.lyq.blog.web.admin;

import com.lyq.blog.model.*;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Slf4j
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
    public String AllBlogs(@RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(required = false, defaultValue = "100") int rows,
                           Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("page", blogService.listAllBlog(page, rows));
        return "admin/blogs";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "15") int rows,
                         BlogSearch blog, Model model) {
        blog.setTitle("%" + blog.getTitle() + "%");
        model.addAttribute("page", blogService.listAllBlogBySearch(page, rows, blog));
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
        Blog blog = blogService.findById(id);
        blog.tags_tagsNames();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @PostMapping //add + update
    public String add(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        Type type = typeService.findByName(blog.getType().getName());
        if (type == null) {
            typeService.saveType(blog.getType());
            blog.setType(typeService.findById(blog.getType().getId()));
        } else {
            blog.setType(type);
        }
        List<Tag> tags = tagService.getTags(blog.getTagNames());
        blog.setTags(tags);
        Long id;
        if (blog.getId() == null) {
            id = blogService.saveBlog(blog);
            blogService.saveBlogTags(blog.getId(), tags);
            if (id > 0) {
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
        } else {
            id = (long) blogService.updateBlog(blog.getId(), blog);
            blogService.updateBlogTags(blog.getId(), tags);
            if (id > 0) {
                if (blog.isPublished()) {
                    attributes.addFlashAttribute("message", "发布成功（更新）");
                } else {
                    attributes.addFlashAttribute("message", "保存成功（更新）");
                }
            } else {
                if (blog.isPublished()) {
                    attributes.addFlashAttribute("message", "发布失败（更新）");
                } else {
                    attributes.addFlashAttribute("message", "保存失败（更新）");
                }
            }
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        blogService.deleteBlogTagsByBlogId(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    //    editormd上传图片
    @ResponseBody
    @PostMapping("/uploadFile")
    public JSONObject hello(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {

        JSONObject jsonObject = new JSONObject();
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = request.getSession().getServletContext().getRealPath("/static/img/blog/");
            log.info("editormd上传图片：{}",rootPath);
            // 文件路径不存在则需要创建文件路径
            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            // 最终文件名
            File realFile = new File(rootPath + File.separator + attach.getOriginalFilename());
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);
            // 下面response返回的json格式是editor.md所限制的，规范输出就OK
            jsonObject.put("success", 1);
            jsonObject.put("message", "上传成功");
            jsonObject.put("url", "/static/img/blog/" + attach.getOriginalFilename());
        } catch (Exception e) {
            jsonObject.put("success", 0);
        }
        return jsonObject;
    }
}