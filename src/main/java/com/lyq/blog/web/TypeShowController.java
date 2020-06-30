package com.lyq.blog.web;

import com.lyq.blog.model.Type;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
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
public class TypeShowController {
    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TypeServiceImpl typeService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "8") int rows,
                        Model model) {
        List<Type> types = typeService.listTypeTop();
        System.out.println(types);
        if (id == -1) {
            id = types.get(0).getId();
        }
        Map<Type, Long> typeMap = new LinkedHashMap<>();// LinkedHashMap保证有序
        for (Type t : types) {
            typeMap.put(t, typeService.countBlogs(t.getId()));
        }
        System.out.println(typeMap);
        model.addAttribute("types", typeMap);
        model.addAttribute("page", blogService.listAllBlogByTypeId(id, page, rows));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}