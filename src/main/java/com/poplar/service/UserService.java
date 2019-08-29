package com.poplar.service;


import com.poplar.po.User;

public interface UserService {

    User findByName(String name);
}
