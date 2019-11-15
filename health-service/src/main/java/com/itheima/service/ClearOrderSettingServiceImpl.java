package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.ClearOrderSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = ClearOrderSettingService.class)
public class ClearOrderSettingServiceImpl implements ClearOrderSettingService {
    @Autowired
    ClearOrderSettingMapper clearOrderSettingMapper;
    @Override
    public void clearOrderSetting(String start,String end) {
        clearOrderSettingMapper.clearOrderSetting(start,end);
    }
}
