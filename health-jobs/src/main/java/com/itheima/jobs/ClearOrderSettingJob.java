package com.itheima.jobs;



import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.ClearOrderSettingService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ClearOrderSettingJob {

    //调用dubbo服务
    @Reference
    ClearOrderSettingService clearOrderSettingService;

    public void clearOrderSetting() {
        String start = DateUtil.beginOfMonth(new Date()).toString("yyyy-MM-dd");
        String end = DateUtil.endOfMonth(new Date()).toString("yyyy-MM-dd");
        clearOrderSettingService.clearOrderSetting(start,end);
    }
}
