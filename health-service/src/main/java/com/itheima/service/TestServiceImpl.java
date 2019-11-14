package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.TestMapper;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TestService.class)
@Transactional
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public List<Test> findAll() {
        return testMapper.findAll();
    }

    @Override
    public List<Menu> getAllMenu() {
        //先查所有的一级菜单
        List<Menu> menus = testMapper.find1Menu();
        //循环一级菜单查询下面的子菜单
        geta(menus);
        return menus;

    }

    private void geta(List<Menu> menus){
        if(CollectionUtil.isNotEmpty(menus)){
            for (Menu menu : menus) {
                Integer id = menu.getId();
                List<Menu> menuByParentId = testMapper.findMenuByParentId(id);
                menu.setChildren(menuByParentId);
                geta(menuByParentId);
            }
        }
    }

    @Override
    public void addMenu(Menu menu) {
        testMapper.addMenu(menu);
    }
}
