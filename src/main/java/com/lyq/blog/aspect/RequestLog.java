package com.lyq.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }