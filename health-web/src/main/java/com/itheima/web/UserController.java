package com.itheima.web;

import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getUserName")
    public Result getUserName(){
        //从spring security上下文中获取用户名
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//这个返回的就是我们在SpringSecurityUserService里面给框架的User对象

        return Result.success("",user.getUsername());
    }
}
