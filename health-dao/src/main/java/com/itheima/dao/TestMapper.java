package com.itheima.dao;

import com.itheima.pojo.Menu;
import com.itheima.pojo.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestMapper {
    List<Test> findAll();

    List<Menu> find1Menu();

    List<Menu> findMenuByParentId(@Param("id") Integer id);

    void addMenu(Menu menu);
}
