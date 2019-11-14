package com.itheima.wechat;

import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.JedisUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;
    @Autowired
    JedisUtil jedisUtil;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        List<Setmeal> setmeals = null;
        //我就想知道数据有没有查询到，一定要返回
        if (null != jedisUtil.get("setmealsList")) {
            String s = jedisUtil.get("setmealsList");
            setmeals = JSON.parseArray(s, Setmeal.class);
            return Result.success("", setmeals);
        }
        setmeals = setmealService.getSetmeal();
        jedisUtil.setex("setmealsList",60 * 9999999, JSON.toJSONString(setmeals));
        return Result.success("", setmeals);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Setmeal setmeal = null;

        if (null != jedisUtil.get("SetmealsDetails")) {
            String s = jedisUtil.get("SetmealsDetails");
            setmeal = JSON.parseObject(s, Setmeal.class);
            return Result.success("", setmeal);
        }
        setmeal = setmealService.findByIdBatch58(id);
        String s = JSON.toJSONString(setmeal);
        jedisUtil.setex("SetmealsDetails",60 * 9999999, JSON.toJSONString(setmeal));

      return Result.success("", setmeal);


    }

    @RequestMapping("/findDetailById")
    public Result findDetailById(Integer id){
        Setmeal setmeal = setmealService.findDetailById(id);
        return Result.success("",setmeal);
    }

    @RequestMapping("/getToken")
    public Result getToken(){
        String token = UUID.randomUUID().toString();
        jedisUtil.setex(token,60 * 30,token);
        return Result.success("",token);
    }

    public static void main(String[] args) {
        String url = "http://localhost:84/pages/setmeal_detail.html?id=12&age=19&name=jack";
        System.out.println(getParam(url,"id"));
        System.out.println(getParam(url,"name"));
        System.out.println(getParam(url,"age"));
    }

    private static String getParam(String url,String name){
        //根据？拆分  ["http://localhost:84/pages/setmeal_detail.html","id=12&age=19&name=jack"]
        String[] urlArray = url.split("\\?");

        String paramStr = urlArray[1];//"id=12&age=19&name=jack"
        //根据& 拆分 ["age=19","id=12","name=jack"]
        String[] paramArray = paramStr.split("&");

        for (String param : paramArray) {
            String key = param.split("=")[0];
            String value = param.split("=")[1];
            if(key.equals(name)){
                return value;
            }
        }
        return "";
    }

}
