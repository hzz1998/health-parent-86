package com.itheima.wechat;

import cn.hutool.core.lang.Assert;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.itheima.entity.Result;
import com.itheima.utils.JedisUtil;
import com.itheima.utils.SmsUtil;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {


    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    DefaultKaptcha defaultKaptcha;


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

    @RequestMapping("/code/{deviceId}")
    public void creatCode(@PathVariable String deviceId, HttpServletResponse response) throws  Exception{
        Assert.notNull(deviceId, "机器码不能为空");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = defaultKaptcha.createText();
        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        //生成的验证码写入redis
        jedisUtil.setex(deviceId,60*5,text);
        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        //将图片写回浏览器
        ImageIO.write(image, "JPEG", out);
    }

}
