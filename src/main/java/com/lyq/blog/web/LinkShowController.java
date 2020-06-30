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
        model.addAttribute("comments", linkCommentService.linkLinkCommentASC());
        model.addAttribute("BGs", linkService.listBGASC());
        model.addAttribute("friends", linkService.listFriendASC());
        return "links";
    }

    @GetMapping("/linksComments")
    public String linkComments(Model model) {
        model.addAttribute("comments", linkCommentService.linkLinkCommentASC());
        return "links :: commentList";
    }

    @PostMapping("/linksComments")
    public String post(LinkComment linkComment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            linkComment.setAvatar(user.getAvatar());
            linkComment.setAdminComment(true);
        } else {
            linkComment.setAvatar(avatar);
        }
        linkCommentService.saveLinkComment(linkComment);
        return "redirect:/linksComments";
    }
}
