package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link implements Serializable {

    private Long id;

    private String blogUrl;

    private String name;

    private String description;

    private boolean friend;

    private String imgUrl;
}
