package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LinkComment {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;
    private String email;
    private String avatar;
    private String content;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private boolean adminComment;

    @OneToMany(mappedBy = "parentComment")
    private List<LinkComment> replayComments=new ArrayList<>();

    @ManyToOne
    private LinkComment parentComment;
}
