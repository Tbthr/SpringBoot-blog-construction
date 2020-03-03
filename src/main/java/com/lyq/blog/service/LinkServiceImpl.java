package com.lyq.blog.service;

import com.lyq.blog.model.Link;
import com.lyq.blog.repository.LinkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkServiceImpl {
    @Resource
    private LinkRepository linkRepository;

    public Page<Link> listLinks(Pageable pageable){
        return linkRepository.findAll(pageable);
    }

    public List<Link> listBGASC() {
        return linkRepository.findAllByFriendFalse();
    }

    public List<Link> listFriendASC() {
        return linkRepository.findAllByFriendTrue();
    }

    public Link findLinkById(Long id){
        return linkRepository.findById(id).get();
    }

    public Link findByName(String name){
        return linkRepository.findByName(name);
    }

    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    public void deleteLink(Long id){
        linkRepository.deleteById(id);
    }
}
