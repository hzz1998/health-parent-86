package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.CheckItemException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    /**
     * 页面传的是json数据，后端使用map 或者 pojo时 需要加@RequestBody
     * 基本类型 & 数组 & MultipartFile 只要保持页面的参数名称和controller方法形参一致就不用加@RequestParam
     * List 不管名字一不一样 必须加@RequestParam
     * @return
     */

    @Reference
    PermissionService permissionService;

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        permissionService.add(permission);
        return Result.success("");
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.delete(id);
        } catch (CheckItemException e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        return Result.success("");
    }
    @RequestMapping("/edit")
    public Result edit(Permission permission){
        permissionService.edit(permission);
        return Result.success("");
    }
    @RequestMapping("/findAll")
    public Result findAll(){
       List<Permission> permissionList = permissionService.findAll();
       return Result.success("",permissionList);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return  permissionService.findPage(queryPageBean);
    }
}
