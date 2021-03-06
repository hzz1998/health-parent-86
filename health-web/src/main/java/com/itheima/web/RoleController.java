package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * 页面传的是json数据，后端使用map 或者 pojo时 需要加@RequestBody
     * 基本类型 & 数组 & MultipartFile 只要保持页面的参数名称和controller方法形参一致就不用加@RequestParam
     * List 不管名字一不一样 必须加@RequestParam
     *
     * @return
     */

    @Reference
    RoleService roleService;


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return roleService.findPage(queryPageBean);

    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role) {
        roleService.edit(role);
        return Result.success("");
    }

    @RequestMapping("/findById4Update")
    public Result findById4Update(Integer id) {
        Map map = roleService.findById4Update(id);
        return Result.success("", map);
    }

    @RequestMapping("/add")
    public Result add(Role role) {
        try {
            roleService.add(role);
            return Result.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            roleService.delete(id);
            return Result.success("");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("");
        }


    }
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Role> roles = roleService.findAll();
        //查询出来一定要返回，不要就放着了
        return Result.success("",roles);
    }

}