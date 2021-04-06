package com.mashibing.drools.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 孙志强
 * @date 2019/9/10 11:58
 */
@Data
@Accessors(chain = true)
public class User {
    //姓名
    private String name;
    //用户级别
    private int level;
}