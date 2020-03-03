package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Link {

    @Id
    @GeneratedValue
    private Long id;

    private String blogUrl;

    private String name;

    private String description;

    private boolean friend;

    private String imgUrl;
}
