package com.lyq.blog.service;

import com.lyq.blog.mapper.LinkCommentMapper;
import com.lyq.blog.model.LinkComment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LinkCommentServiceImpl {
    @Resource
    private LinkCommentMapper linkCommentMapper;
    //存放 迭代找出的所有子代的集合
    private List<LinkComment> tempReplys = new ArrayList<>();

    public void saveLinkComment(LinkComment linkComment) {
        Long parentLinkCommentId = linkComment.getParentLinkCommentId();
        if (parentLinkCommentId != -1) {
            linkComment.setParentLinkCommentId(parentLinkCommentId);
        } else {
            linkComment.setParentLinkCommentId(null);
        }
        linkComment.setCreateTime(new Date());
        linkCommentMapper.save(linkComment);
    }

    public List<LinkComment> linkLinkCommentASC() {
        List<LinkComment> linkComments = eachLinkComment(linkCommentMapper.findAllByParentLinkCommentNull());
        for (LinkComment linkComment : linkComments) {
            List<LinkComment> replayLinkComments = linkComment.getReplayLinkComments();
            for (LinkComment lk : replayLinkComments) {
                lk.setParentLinkComment(linkCommentMapper.setParentById(lk.getParentLinkCommentId()));
            }
        }
        return linkComments;
    }

    // 循环每个顶级的评论节点
    private List<LinkComment> eachLinkComment(List<LinkComment> linkComments) {
        List<LinkComment> linkCommentsView = new ArrayList<>(linkComments);
        //合并评论的各层子代-->第一级子代集合中
        combineChildren(linkCommentsView);
        return linkCommentsView;
    }

    //合并评论的各层子代-->第一级子代集合中
    private void combineChildren(List<LinkComment> linkComments) {
        // 遍历父评论
        for (LinkComment linkComment : linkComments) {
            List<LinkComment> sons = linkComment.getSons();
            for (LinkComment c : sons) {
                recursively(c);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            linkComment.setReplayLinkComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    // 递归迭代子类
    private void recursively(LinkComment linkComment) {
        tempReplys.add(linkComment);//顶节点添加到临时存放集合
        List<LinkComment> son = linkCommentMapper.findByParentId(linkComment.getId());
        if (son.size() > 0) {
            for (LinkComment c : son) {
                tempReplys.add(c);
                List<LinkComment> byParentId = linkCommentMapper.findByParentId(c.getId());
                if (byParentId.size() > 0) {
                    byParentId.forEach(this::recursively);
                }
            }
        }
    }
}
