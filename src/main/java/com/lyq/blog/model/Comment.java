package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;

    private String nickname;
    private String email;
    private String avatar;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private boolean adminComment;

    private Long blogId;

    private List<Comment> sons;
    private List<Comment> replayComments = new ArrayList<>();

    private Long parentCommentId;
    private Comment parentComment;
}
