package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Test;
import com.itheima.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Reference
    TestService testService;
    @RequestMapping("/test")
    public Result test(){
        List<Test> all = testService.findAll();
        return Result.success("",all);
    }

}
