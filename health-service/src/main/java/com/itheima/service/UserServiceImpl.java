package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionMapper;
import com.itheima.dao.RoleMapper;
import com.itheima.dao.UserMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.CheckItemException;
import com.itheima.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public User findByUserName(String username) {
        //根据用户名查询用户基本信息
        User user = userMapper.selectByUserName(username);
        if(null != user){
            //根据userid查询用户下面所有的角色
            Set<Role> roles = roleMapper.selectByUserId(user.getId());
            if(CollectionUtil.isNotEmpty(roles)){
                //循环用户所有角色查询角色对应所有的权限
                for (Role role : roles) {
                    Set<Permission> permissions = permissionMapper.selectByRoleId(role.getId());
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<User> users = userMapper.findPage(queryPageBean.getQueryString());
        PageResult pageResult =
                PageResult.builder()
                        .rows(users)
                        .total(page.getTotal());

        return new PageResult(page.getTotal(),users);
    }

    @Override
    public void add(User users) {
        userMapper.add(users);
    }

    @Override
    public void delete(Integer id) throws Exception{
        //根据检查项的id查询当前项目是否被引用
        Integer count = userMapper.findCountById(id);
        if(null != count && count > 0){
            //如果有引用就提示用户
            throw new Exception("存在引用不能删除");
        } else {
            //没有引用直接删除
            userMapper.delete(id);
        }
    }

    @Override
    public void edit(User user) {
        userMapper.edit(user);
        userMapper.deleteAssociation(user.getId());
        //建立新的关系
        //setCheckItemAndCheckGroupRelation(user.getRoles(),user.getId());
    }

    @Override
    public Map findById(Integer id) {
        User user =  userMapper.findById(id);
        List<Role> roles = roleMapper.findAll();
        List<Integer> roleIds = userMapper.findRoleIdsByUserId(id);

        Map map = new HashMap();
        map.put("user",user);
        map.put("roles",roles);
        map.put("roleIds",roleIds);
        return map;
    }
}
