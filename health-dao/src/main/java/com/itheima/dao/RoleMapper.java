package com.itheima.dao;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleMapper {


    Set<Role> selectByUserId(@Param("userId") Integer userId);
}
