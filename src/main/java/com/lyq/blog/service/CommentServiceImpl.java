package com.lyq.blog.service;

import com.lyq.blog.mapper.CommentMapper;
import com.lyq.blog.model.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl {
    @Resource
    private CommentMapper commentMapper;
    //存放 迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    public Long countComments() {
        return commentMapper.sum();
    }

    public void saveComment(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
        if (parentCommentId != -1) {
            comment.setParentCommentId(parentCommentId);
        } else {
            comment.setParentCommentId(null);
        }
        comment.setCreateTime(new Date());
        commentMapper.save(comment);
    }

    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = eachComment(commentMapper.findByBlogIdAndParentCommentNull(blogId));
        for (Comment comment : comments) {
            List<Comment> replayComments = comment.getReplayComments();
            for (Comment c : replayComments) {
                c.setParentComment(commentMapper.setParentById(c.getParentCommentId()));
            }
        }
        return comments;
    }

    // 循环每个顶级的评论节点
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>(comments);
        //合并评论的各层子代-->第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    //合并评论的各层子代-->第一级子代集合中
    private void combineChildren(List<Comment> comments) {
        // 遍历父评论
        for (Comment comment : comments) {
            List<Comment> sons = comment.getSons();
            for (Comment c : sons) {
                recursively(c);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplayComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    // 递归迭代子类
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        List<Comment> son = commentMapper.findByParentId(comment.getId());
        if (son.size() > 0) {
            for (Comment c : son) {
                tempReplys.add(c);
                List<Comment> byParentId = commentMapper.findByParentId(c.getId());
                if (byParentId.size() > 0) {
                    byParentId.forEach(this::recursively);
                }
            }
        }
    }
}
