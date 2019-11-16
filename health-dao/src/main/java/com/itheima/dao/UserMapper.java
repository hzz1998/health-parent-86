package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User selectByUserName(@Param("username") String username);

    List<User> findPage(@Param("queryString") String queryString);

    void add( User user);

    Integer findCountById(@Param("id") Integer id);

    void delete(Integer id);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(User user);

    void deleteAssociation(Integer id);
}
