package com.itheima.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReportDao {
    Integer getMemberReport(@Param("month") String month);

    List<Map> getSetmealReport();

}
