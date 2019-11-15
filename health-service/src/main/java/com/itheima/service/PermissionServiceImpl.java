package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public void add(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void delete(Integer id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void edit(Permission permission) {

        permissionMapper.edit(permission);
    }

    @Override
    public List<Permission> findAll() {

        return permissionMapper.findAll();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        //使用分页插件(告诉分页拦截器现在要分页)
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        List<Permission> permissionList = permissionMapper.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),permissionList);
    }


    @Override
    public Permission findById(Integer id) {
        return permissionMapper.findById(id);
    }
}
