package com.itheima.service;

import com.itheima.pojo.User;

import java.util.Map;

public interface UserService {
    User findByUserName(String username);

    Map getMenusAndPermissions();
}
