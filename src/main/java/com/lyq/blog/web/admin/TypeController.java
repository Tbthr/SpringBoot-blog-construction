package com.lyq.blog.web.admin;

import com.lyq.blog.model.Type;
import com.lyq.blog.service.TypeServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/types")
public class TypeController {

    @Resource
    private TypeServiceImpl typeService;

    @GetMapping //get all
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/input") // to分类标签页面
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/input/{id}") // to分类标签页面 attached "id"
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        model.addAttribute("edit","修改");
        return "admin/types-input";
    }

    @PostMapping //add
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //BindingResult需紧跟@Valid参数
        if (typeService.findByName(type.getName())!=null){
            result.rejectValue("name","nameError","该分类已存在，不可重复添加");
        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.saveType(type);
        if (t != null){
            attributes.addFlashAttribute("message","添加成功");
        } else {
            attributes.addFlashAttribute("message","添加失败");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/{id}") //update
    public String edit(Type type,@PathVariable Long id, RedirectAttributes attributes){
        Type t=typeService.updateType(id,type);
        if (t != null){
            attributes.addFlashAttribute("message","更新成功");
        } else {
            attributes.addFlashAttribute("message","更新失败");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/delete/{id}") //delete
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
