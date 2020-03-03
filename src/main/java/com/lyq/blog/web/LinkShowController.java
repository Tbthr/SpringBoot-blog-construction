package com.lyq.blog.web;

import com.lyq.blog.model.LinkComment;
import com.lyq.blog.model.User;
import com.lyq.blog.service.LinkCommentServiceImpl;
import com.lyq.blog.service.LinkServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LinkShowController {

    @Resource
    private LinkCommentServiceImpl linkCommentService;
    @Resource
    private LinkServiceImpl linkService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/links")
    public String links(Model model){
        model.addAttribute("comments",linkCommentService.listCommentASC());
        model.addAttribute("BGs",linkService.listBGASC());
        model.addAttribute("friends",linkService.listFriendASC());
        return "links";
    }

    @GetMapping("/linksComments")
    public String comments(Model model) {
        model.addAttribute("comments", linkCommentService.listCommentASC());
        return "links :: commentList";
    }

    @PostMapping("/linksComments")
    public String post(LinkComment comment, HttpSession session) {
        User user= (User) session.getAttribute("user");
        if (user!=null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        linkCommentService.saveComment(comment);
        return "redirect:/linksComments";
    }
}
