package com.itheima.web;

import cn.hutool.db.Page;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    MenuService menuService;

    @RequestMapping("/findAllMenu")
    public Result findAllMenu() {
       List<Menu> menu = menuService.findAllMenu();
       return Result.success("",menu);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return menuService.findPage(queryPageBean);

    }

    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        menuService.edit(menu);
        return new Result(true,"");
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){

        try {
            menuService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.success("",MessageConstant.DELELT);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Menu menu =  menuService.findById(id);
        return new Result(true,"",menu);
    }
}
