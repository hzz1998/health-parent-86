package com.itheima.dao;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {


    Set<Role> selectByUserId(@Param("userId") Integer userId);

    List<Role> findPage(String queryString);

    Role findById(Integer id);

    List<Integer> findpermisssionsIdsByRoleId(Integer id);

    void edit(Role role);

    void add(Role role);

    void delete(Integer id);
}
