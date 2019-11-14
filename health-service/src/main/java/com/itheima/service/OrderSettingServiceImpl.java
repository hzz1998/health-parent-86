package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;


    @Override
    public void setNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据预约设置日期查询是否已经存在数据
        Integer count = orderSettingDao.findCountByDate(orderDate);
        if(null != count && count > 0){
            //存在就更新
            orderSettingDao.edit(orderSetting);
        } else {
            //不存在就插入
            orderSettingDao.add(orderSetting);
        }

    }


    @Override
    public void setNumberByDateBatch(List<OrderSetting> orderSettings) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);


        //使用多线程导入，把orderSettings拆分成小的集合，创建多个线程分摊人数
//        100
        List<List<OrderSetting>> partition = Lists.partition(orderSettings, 30);

//        for (List<OrderSetting> settings : partition) {
//            Runnable task = new Runnable() {
//                @Override
//                public void run() {
//                    for (OrderSetting orderSetting : settings) {
//                        setNumberByDate(orderSetting);
//                    }
//                }
//            };
//            executorService.submit(task);
//        }

        List<Future<Integer>> futures = new ArrayList<>();

        for (List<OrderSetting> settings : partition) {
            Callable task = new Callable<Integer>(){
                @Override
                public Integer call() throws Exception {
                    for (OrderSetting orderSetting : settings) {
                        setNumberByDate(orderSetting);
                    }
                    return settings.size();
                }
            };


            Future<Integer> future = executorService.submit(task);
            futures.add(future);
        }


        //我想得线程干完活之后给我一个结果?

    }

    @Override
    public List<Map> findByMonth(String month) {
        List<Map> result = new ArrayList<>();
        //month2019-11
        String start = month + "-01";
        String end = month + "-31";
        List<OrderSetting> orderSettings = orderSettingDao.findByMonth(start,end);

        //{"date":22,"mouth":10,"number":300,"reservations":300}
        if(CollectionUtil.isNotEmpty(orderSettings)){
            for (OrderSetting orderSetting : orderSettings) {
                Date orderDate = orderSetting.getOrderDate();
                Map map = new HashMap();
                map.put("date",orderDate.getDate());
                map.put("mouth",orderDate.getMonth());
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                result.add(map);
            }
        }
        return result;
    }
}
