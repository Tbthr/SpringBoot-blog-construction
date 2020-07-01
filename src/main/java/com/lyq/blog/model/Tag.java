package com.lyq.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {

    private Long id;

    @NotBlank(message = "标签名称不能为空")
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
