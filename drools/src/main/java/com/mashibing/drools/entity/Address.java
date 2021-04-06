package com.mashibing.drools.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 孙志强
 * @date 2019/9/10 11:25
 */
@Data
@Accessors(chain = true)
public class Address {
    private String postcode;
    private String street;
}
