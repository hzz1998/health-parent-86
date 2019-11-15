package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<Role> roles = roleMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),roles);

    }
}
