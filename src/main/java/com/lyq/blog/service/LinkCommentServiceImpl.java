package com.lyq.blog.service;

import com.lyq.blog.model.LinkComment;
import com.lyq.blog.repository.LinkCommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LinkCommentServiceImpl {
    @Resource
    private LinkCommentRepository linkCommentRepository;

    public List<LinkComment> listCommentASC() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        return eachComment(linkCommentRepository.findAllByParentCommentNull(sort));
    }

    public void saveComment(LinkComment linkComment) {
        Long parentCommentId = linkComment.getParentComment().getId();
        if (parentCommentId != -1) {
            linkComment.setParentComment(linkCommentRepository.findById(parentCommentId).get());
        } else {
            linkComment.setParentComment(null);
        }
        linkComment.setCreateTime(new Date());
        linkCommentRepository.save(linkComment);
    }

    //    循环每个顶级的评论节点
    private List<LinkComment> eachComment(List<LinkComment> linkComments) {
        List<LinkComment> commentsView = new ArrayList<>();
        for (LinkComment linkComment : linkComments) {
            LinkComment c = new LinkComment();
            BeanUtils.copyProperties(linkComment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    //    root根节点，blog不为空的对象集合
    private void combineChildren(List<LinkComment> linkComments) {
        for (LinkComment linkComment : linkComments) {
            List<LinkComment> replys1 = linkComment.getReplayComments();
            for (LinkComment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            linkComment.setReplayComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<LinkComment> tempReplys = new ArrayList<>();

    //    递归迭代子类
    private void recursively(LinkComment linkComment) {
        tempReplys.add(linkComment);//顶节点添加到临时存放集合
        if (linkComment.getReplayComments().size() > 0) {
            List<LinkComment> replys = linkComment.getReplayComments();
            for (LinkComment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplayComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
