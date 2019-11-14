package com.itheima.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/setNumberByDate")
    public Result setNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.setNumberByDate(orderSetting);
        return Result.success("");
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            //使用excel工具读取文件
            List<String[]> datas = POIUtils.readExcel(excelFile);

            //把datas转成List<OrderSetting>
            List<OrderSetting> orderSettings = new ArrayList<>();

            if(CollectionUtil.isNotEmpty(datas)){
                for (String[] data : datas) {
                    //ArrayIndexOutOfBoundsException
                    if(data.length !=2 ){
                        continue;
                    }
                     String dateStr = data[0];
                     String numberStr =  data[1];

                    DateTime orderDate = null;
                    Integer number = null;
                    try {
                        //防止小护士乱写
                        number = Integer.valueOf(numberStr);
                        orderDate = DateUtil.parse(dateStr, "yyyy/MM/dd");
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    OrderSetting orderSetting = new OrderSetting(orderDate,number);
                    orderSettings.add(orderSetting);
                }
            }

            //调用dubbo服务批量导入预约设置信息
            orderSettingService.setNumberByDateBatch(orderSettings);

            return Result.success("");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("");
        }
    }


    @RequestMapping("/findByMonth")
    public Result findByMonth(String month){
        //{"date":22,"mouth":10,"number":300,"reservations":300}
        List<Map> maps = orderSettingService.findByMonth(month);
        return Result.success("",maps);
    }

}
