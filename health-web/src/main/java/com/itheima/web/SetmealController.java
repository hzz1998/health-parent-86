package com.itheima.web;

import cn.hutool.core.lang.UUID;
//import cn.hutool.db.PageResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    /**
     * 页面传的是json数据，后端使用map 或者 pojo时 需要加@RequestBody
     * 基本类型 & 数组 & MultipartFile 只要保持页面的参数名称和controller方法形参一致就不用加@RequestParam
     * List 不管名字一不一样 必须加@RequestParam
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            String originalFilename = imgFile.getOriginalFilename();  //原始的文件名
            //需要改名字
            //截取原来的后缀名()
            int index = originalFilename.lastIndexOf(".");
            //生成一个随机的字符串
            String uuid = UUID.randomUUID().toString();
            //把截取的后缀和生成一个随机的字符串拼接
            String suffix = originalFilename.substring(index);
            String newFileName =  uuid + suffix;

            //调用七牛工具类
            QiniuUtil.upload(imgFile.getBytes(),newFileName);
            //返回结果(返回文件名在页面可以用域名拼接然后赋值给img标签src)


            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);

            return Result.success("",newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }


    }


    @RequestMapping("/add")
   public Result add(@RequestBody  Setmeal setmeal){
        setmealService.add(setmeal);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        return Result.success("");
   }
    @RequestMapping("/findPage")
   public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
   }


}
