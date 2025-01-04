package com.example.demo.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangtao
 * @since 2024/6/1 0:25
 */
@Data
public class User implements Serializable {
    private long id;
    private String name;
}
