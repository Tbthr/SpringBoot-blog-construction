/**
 * Author:    lyq_power
 * Date:      2019/9/21 19:18
 */
package com.lyq.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable int id,@PathVariable String name) {
//        int a = 2 / 0;
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundExcepiton("博客找不到");
//        }
        return "index.html";
    }
}