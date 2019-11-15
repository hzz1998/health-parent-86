package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("/getUserName")
    public Result getUserName(){
        //从spring security上下文中获取用户名
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//这个返回的就是我们在SpringSecurityUserService里面给框架的User对象

        return Result.success("",user.getUsername());
    }

    @RequestMapping("/getMenusAndPermissions")
    public Result getMenusAndPermissions(){
        Map map = userService.getMenusAndPermissions();
        return Result.success("",map);
    }
}
