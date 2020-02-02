/**
 * Author:    lyq_power
 * Date:      2019/9/21 19:18
 */
package com.lyq.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        int a = 2 / 0;
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundExcepiton("博客找不到");
//        }
        return "index";
    }
}