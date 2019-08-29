package com.poplar.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author poplar
 * @description 权限类
 */
@Data
public class Permission implements Serializable {

    private String id;
    private String name;
    private String url;
}
