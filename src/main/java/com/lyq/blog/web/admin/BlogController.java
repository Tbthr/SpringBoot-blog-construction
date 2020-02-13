package com.lyq.blog.web.admin;

import com.lyq.blog.model.Blog;
import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.model.Type;
import com.lyq.blog.model.User;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TagServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
        blog.tags_tagsNames();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    @PostMapping //add + update
    public String add(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        Type type = typeService.findByName(blog.getType().getName());
        if (type == null) {
            blog.setType(typeService.getType(typeService.saveType(blog.getType()).getId()));
        } else {
            blog.setType(type);
        }
        blog.setTags(tagService.getTags(blog.getTagNames()));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        }else{
            b=blogService.updateBlog(blog.getId(),blog);
        }
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
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
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