package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.CheckItemMapper;
import com.itheima.dao.RoleMapper;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }
}
