package com.itheima.wechat;

import com.itheima.entity.Result;
import com.itheima.utils.JedisUtil;
import com.itheima.utils.SmsUtil;
import com.itheima.utils.ValidateCodeUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {


    @Autowired
    JedisUtil jedisUtil;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //调用短信工具
        SmsUtil.sendSmsCode(telephone,String.valueOf(code));
        //把验证码存入redis（等会儿提交预约的时候要和用户输入的比对）
        jedisUtil.setex("001" + telephone,60 * 500,String.valueOf(code));
        return Result.success("发送成功");
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //调用工具发送
//        SmsUtil.sendSmsCode(telephone,String.valueOf(code));
        //把验证码存入redis
        jedisUtil.setex("002" + telephone,60 * 500,String.valueOf(code));
        return Result.success("");
    }

}
