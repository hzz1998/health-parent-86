package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectByUserName(@Param("username") String username);
}
