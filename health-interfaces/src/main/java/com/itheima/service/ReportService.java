package com.itheima.service;

import cn.hutool.core.date.DateTime;
import com.itheima.pojo.ReportDataVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Integer> getMemberReport(List<String> months);

    List<Map> getSetmealReport();

    ReportDataVo getBusinessReportData();

    List<Map> getSex();

    List<Map> getAge() throws Exception;


    List<Integer> queryCountByMonth(List<String> monthList);
}
