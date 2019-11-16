package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.CheckItemException;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    UserService userService;

    @RequestMapping("/getUserName")
    public Result getUserName() {
        //从spring security上下文中获取用户名
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//这个返回的就是我们在SpringSecurityUserService里面给框架的User对象

        return Result.success("", user.getUsername());
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return userService.findPage(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody com.itheima.pojo.User user){
        userService.add(user);
        return Result.success("添加用户成功");
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            userService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        return Result.success("删除用户成功");
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody com.itheima.pojo.User user){
        userService.edit(user);
        return Result.success("");
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map = userService.findById(id);
        return Result.success("",map);
    }
}
