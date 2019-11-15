package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao menuDao;

    @Override
    public List<Menu> findAllMenu() {

        return menuDao.findAllMenu();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件(告诉分页拦截器现在要分页)
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        List<Menu> menus = menuDao.findPage(queryPageBean.getQueryString());
        for (Menu menu : menus) {
            Integer id = menu.getId();
            System.out.println(id);
        }
        return new PageResult(page.getTotal(),menus);
    }

    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }

    @Override
    public void delete(Integer id) {
        //根据检查项的id查询当前项目是否被引用
        Integer count = menuDao.findCountById(id);
        if(null != count && count > 0){
            //如果有引用就提示用户
            throw new RuntimeException("存在引用不能删除");
        } else {
            //没有引用直接删除
            menuDao.delete(id);
        }
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }
}
