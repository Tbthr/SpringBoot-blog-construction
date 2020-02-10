package com.lyq.blog.web;

import com.lyq.blog.model.BlogSearch;
import com.lyq.blog.model.Type;
import com.lyq.blog.service.BlogServiceImpl;
import com.lyq.blog.service.TypeServiceImpl;
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
public class TypeShowController {
    @Resource
    private BlogServiceImpl blogService;
    @Resource
    private TypeServiceImpl typeService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,
                        @PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Type> types = typeService.listTypeTop(1000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogSearch blogSearch = new BlogSearch();
        blogSearch.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogSearch));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}