package com.lyq.blog.web.admin;

import com.lyq.blog.model.Tag;
import com.lyq.blog.service.TagServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/tags")
public class TagController {
    @Resource
    private TagServiceImpl tagService;

    @GetMapping //get all
    public String tags(@RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "20") int rows,
                       Model model) {
        model.addAttribute("page", tagService.listTag(page, rows));
        return "admin/tags";
    }

    @GetMapping("/input") // to标签页面
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/input/{id}") // to标签页面 attached "id"
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.findById(id));
        model.addAttribute("edit", "修改");
        return "admin/tags-input";
    }

    @PostMapping //add
    public String post(@Valid Tag Tag, BindingResult result, RedirectAttributes attributes) {
        //BindingResult需紧跟@Valid参数
        if (tagService.findByName(Tag.getName()) != null) {
            result.rejectValue("name", "nameError", "该标签已存在，不可重复添加");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Long id = tagService.saveTag(Tag);
        if (id != null) {
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/{id}") //update
    public String edit(Tag Tag, @PathVariable Long id, RedirectAttributes attributes) {
        int res = tagService.updateTag(id, Tag);
        if (res > 0) {
            attributes.addFlashAttribute("message", "更新成功");
        } else {
            attributes.addFlashAttribute("message", "更新失败");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/delete/{id}") //delete
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
