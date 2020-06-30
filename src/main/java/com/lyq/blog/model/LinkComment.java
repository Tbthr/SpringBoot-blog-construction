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
public class LinkComment {

    private Long id;

    private String nickname;
    private String email;
    private String avatar;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private boolean adminComment;

    private List<LinkComment> sons;
    private List<LinkComment> replayLinkComments = new ArrayList<>();

    private Long parentLinkCommentId;
    private LinkComment parentLinkComment;
}
