package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    void add(Permission permission);

    void delete(Integer id);

    void edit(Permission permission);

    List<Permission> findAll();

    PageResult findPage(QueryPageBean queryPageBean);
}
