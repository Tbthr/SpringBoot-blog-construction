package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogSearch {
    private String title;
    private Long typeId;
    private boolean recommend;
}
