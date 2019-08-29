package com.poplar.po;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author poplar
 * @description 用户类
 */
@Data
public class User implements Serializable {
    private String id;
    private String name;
    private String passWord;
    private String passWordSalt;
    private Set<Role> roles = new HashSet<>();
}
