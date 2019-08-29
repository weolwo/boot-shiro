package com.poplar.service.impl;

import com.poplar.mapper.UserMapper;
import com.poplar.po.User;
import com.poplar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        // 查询用户是否存在
        User bean = userMapper.findByName(name);
        if (bean != null) {
            // 查询用户信息、角色、权限
            bean = userMapper.findById(bean.getId());
        }
        return bean;
    }
}