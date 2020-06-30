package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private Long id;

    private String blogUrl;

    private String name;

    private String description;

    private boolean friend;

    private String imgUrl;
}
