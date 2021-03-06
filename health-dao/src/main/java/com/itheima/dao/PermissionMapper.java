package com.itheima.dao;

import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {

    List<Permission> findAll();

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);


    Set<Permission> selectByRoleId(@Param("roleId") Integer roleId);

    List<Permission> findPage(String queryString);

    List<Menu> getFirstMenus();

    List<Menu> getSecondMenus(Integer id);

    void edit(Permission permission);

    Permission findById(Integer id);
}
