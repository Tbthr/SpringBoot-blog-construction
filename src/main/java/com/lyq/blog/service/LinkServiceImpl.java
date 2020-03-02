package com.lyq.blog.service;

import com.lyq.blog.model.Link;
import com.lyq.blog.repository.LinkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkServiceImpl {
    @Resource
    private LinkRepository linkRepository;

    public List<Link> listBGASC() {
        return linkRepository.findAllByBigGuyTrue();
    }

    public List<Link> listFriendASC() {
        return linkRepository.findAllByBigGuyFalse();
    }

    public void saveComment(Link link) {
        linkRepository.save(link);
    }
}
