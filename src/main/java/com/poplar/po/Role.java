package com.poplar.po;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @ Author poplar
 */
@Data
public class Role implements Serializable {
    private String id;
    private String name;
    private Set<Permission> permissions = new HashSet<>();

}
