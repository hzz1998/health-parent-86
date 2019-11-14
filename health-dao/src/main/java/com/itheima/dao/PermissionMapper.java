package com.itheima.dao;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PermissionMapper {

    Set<Permission> selectByRoleId(@Param("roleId") Integer roleId);
}
