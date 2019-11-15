package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionMapper;
import com.itheima.dao.RoleMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;




    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<Role> roles = roleMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),roles);

    }

    @Override
    public Map findById4Update(Integer id) {

        //根据角色id查询角色详情
        Role role =  roleMapper.findById(id);
        //查询角色所有的权限
        List<Permission> permissions = permissionMapper.findAll();
        //根据权限的id查询角色下面的权限id集合
        List<Integer> permissionIds = roleMapper.findpermisssionsIdsByRoleId(id);


        Map map = new HashMap();
        map.put("role",role);
        map.put("permissions",permissions);
        map.put("permissionIds",permissionIds);

        return map;
    }

    @Override
    public void edit(Role role) {
        roleMapper.edit(role);
    }

    @Override
    public void add(Role role) {
        roleMapper.add(role);
    }

    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
    }
}
