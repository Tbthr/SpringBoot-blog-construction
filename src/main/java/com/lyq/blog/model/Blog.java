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
public class Blog {

    @Id
    @GeneratedValue
    private Long id;

    private String flag; //原创 or 转载
    private String title; //标题
    private String content; //内容
    private String firstPicture; //首图

    private Integer views; //浏览次数

//    @Lob
//    @Basic(fetch = FetchType.LAZY)
    private String description; //文章简介

    private boolean recommend; //推荐
    private boolean shareStatement; //转载声明
    private boolean appreciation; //赞赏
    private boolean commentabled; //评论

    private boolean published; //发布 or 保存

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //for date.format(time)
    private Date createTime; //创建时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime; //更新时间

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST}) //级联新增
    private List<Tag> tags = new ArrayList<>();

    @Transient //不映射到数据表
    private String tagIds;
    public void tags_tagsIds() {
        this.tagIds = (tagsTotagsIds(this.tags));
    }
    public String tagsTotagsIds(List<Tag> tags) {
        StringBuilder builder = new StringBuilder();
        int sum = tags.size();
        int cnt = 0;
        for (Tag tag : tags) {
            builder.append(tag.getId());
            cnt++;
            if (cnt != sum) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    @Transient //不映射到数据表
    private String tagNames;
    public void tags_tagsNames() {
        this.tagNames = (tagsTotagsNames(this.tags));
    }
    public String tagsTotagsNames(List<Tag> tags) {
        StringBuilder builder = new StringBuilder();
        int sum = tags.size();
        int cnt = 0;
        for (Tag tag : tags) {
            builder.append(tag.getName());
            cnt++;
            if (cnt != sum) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", flag='" + flag + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", views=" + views +
                ", description='" + description + '\'' +
                ", recommend=" + recommend +
                ", shareStatement=" + shareStatement +
                ", appreciation=" + appreciation +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", tagIds='" + tagIds + '\'' +
                ", tagNames='" + tagNames + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
