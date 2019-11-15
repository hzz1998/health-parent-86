package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findAllMenu();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Menu menu);

    void edit(Menu menu);

    void delete(Integer id);

    Menu findById(Integer id);

}
