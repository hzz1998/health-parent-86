package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void setNumberByDate(OrderSetting orderSetting);

    void setNumberByDateBatch(List<OrderSetting> orderSettings);

    List<Map> findByMonth(String month);
}
