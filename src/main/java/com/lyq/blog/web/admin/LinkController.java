package com.lyq.blog.web.admin;

import com.lyq.blog.model.Link;
import com.lyq.blog.service.LinkServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/links")
public class LinkController {
    @Resource
    private LinkServiceImpl linkService;

    @GetMapping
    public String links(@RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "20") int rows,
                        Model model) {
        model.addAttribute("page", linkService.listLinks(page, rows));
        return "admin/links";
    }

    @PostMapping("/input")
    public String add(@Valid Link link, BindingResult result, RedirectAttributes attributes) {
        if (linkService.findByName(link.getName()) != null && link.getId() == null) {
            result.rejectValue("name", "nameError", "该友链已存在，不可重复添加");
        }
        if (result.hasErrors()) {
            return "admin/links-input";
        }
        boolean isAdd = false;
        if (link.getId() == null) {
            isAdd = true;
        }
        Long id;
        if (link.getId() == null) {
            id = linkService.saveLink(link);
        } else {
            id = (long) linkService.update(link);
        }
        if (id != null) {
            if (isAdd) {
                attributes.addFlashAttribute("message", "添加成功");
            } else {
                attributes.addFlashAttribute("message", "修改成功");
            }
        } else {
            if (isAdd) {
                attributes.addFlashAttribute("message", "添加失败");
            } else {
                attributes.addFlashAttribute("message", "修改失败");
            }
        }
        return "redirect:/admin/links";
    }

    @GetMapping("/input")
    public String add(Model model) {
        model.addAttribute("link", new Link());
        return "admin/links-input";
    }

    @GetMapping("/input/{id}")
    public String update(@PathVariable Long id, Model model) {
        System.out.println(linkService.findLinkById(id));
        model.addAttribute("link", linkService.findLinkById(id));
        return "admin/links-input";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        linkService.deleteLink(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/links";
    }
}
