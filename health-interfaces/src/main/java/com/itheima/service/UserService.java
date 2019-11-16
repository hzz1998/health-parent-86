package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.User;

import java.util.Map;

public interface UserService {
    User findByUserName(String username);

    Map getMenusAndPermissions();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(com.itheima.pojo.User user);

    void delete(Integer id) throws Exception;

    void edit(User user);

    Map findById(Integer id);

}
