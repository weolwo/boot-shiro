package com.poplar.mapper;

import com.poplar.po.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User findByName(String name);

    User findById(String id);
}