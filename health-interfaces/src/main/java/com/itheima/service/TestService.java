package com.itheima.service;

import com.itheima.pojo.Menu;
import com.itheima.pojo.Test;

import java.util.List;

public interface TestService {
    List<Test> findAll();

    List<Menu> getAllMenu();

    void addMenu(Menu menu);
}
