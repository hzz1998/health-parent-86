package com.itheima.dao;

import com.itheima.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {
    List<Menu> findAllMenu();

    List<Menu> findPage(@Param("queryString") String queryString);

    void add(Menu menu);

    void edit(Menu menu);

    Integer findCountById(@Param("id") Integer id);

    void delete(@Param("id") Integer id);

    Menu findById(@Param("id") Integer id);
}
