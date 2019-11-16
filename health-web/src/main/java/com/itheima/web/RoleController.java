package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {
    @Reference
    RoleService roleService;

    @RequestMapping("/findAll")
    public Result findAll(){
        List<Role> roles = roleService.findAll();
        //查询出来一定要返回，不要就放着了
        return Result.success("",roles);
    }
}
