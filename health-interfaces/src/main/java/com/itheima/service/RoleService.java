package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.Map;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {

    PageResult findPage(QueryPageBean queryPageBean);

    Map findById4Update(Integer id);

    void edit(Role role);

    void add(Role role);

    void delete(Integer id);

    List<Role> findAll();
}


