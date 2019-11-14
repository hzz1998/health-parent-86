package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfoVo;
import com.itheima.service.OrderService;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    JedisUtil jedisUtil;

    @Reference
    OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody OrderInfoVo orderInfoVo){
        String telephone = orderInfoVo.getTelephone();
        String validateCode = orderInfoVo.getValidateCode();
        String token = orderInfoVo.getToken();

        if(null == token){
            return Result.error("非法请求");
        }

        Long del = jedisUtil.del(token);//redis单线程
        if(del == 0){
            return Result.error("非法请求");
        }

        //从redis获取验证码
        String redisCode = jedisUtil.get("001" + telephone);
        //比对用户输入的验证码
        if(null == redisCode || !redisCode.equals(validateCode)){
            return Result.error("验证码错误");
        }
        //把预约类型设置为 微信预约
        orderInfoVo.setOrderType(Order.ORDERTYPE_WEIXIN);
        //调用预约dubbo接口
        return orderService.submit(orderInfoVo);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map = orderService.findById(id);
        return Result.success("",map);
    }

}
