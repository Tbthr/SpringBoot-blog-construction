package com.lyq.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyq.blog.mapper.LinkMapper;
import com.lyq.blog.model.Link;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkServiceImpl {
    @Resource
    private LinkMapper linkMapper;

    public PageInfo<Link> listLinks(int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<>(linkMapper.findAllByFriendAndId());
    }

    public List<Link> listBGASC() {
        return linkMapper.findAllByFriendFalse();
    }

    public List<Link> listFriendASC() {
        return linkMapper.findAllByFriendTrue();
    }

    public Link findLinkById(Long id) {
        return linkMapper.findById(id);
    }

    public Link findByName(String name) {
        return linkMapper.findByName(name);
    }

    public Long saveLink(Link link) {
        return linkMapper.save(link);
    }

    public void deleteLink(Long id) {
        linkMapper.deleteById(id);
    }

    public int update(Link link) {
        return linkMapper.update(link);
    }
}
