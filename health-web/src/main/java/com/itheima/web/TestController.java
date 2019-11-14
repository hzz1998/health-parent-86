package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Test;
import com.itheima.service.TestService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Reference
    TestService testService;

    @RequestMapping("/findAll")
    public Result findAll(){
//        return testService.findAll();
        return null;
    }

    @RequestMapping("/getAllMenu")
    public Result getAllMenu(){
        List<Menu> menus = testService.getAllMenu();
        return Result.success("",menus);
    }

    @RequestMapping("/addMenu")
    public Result addMenu(@RequestBody Menu menu){
        testService.addMenu(menu);
        return Result.success("");
    }
}
