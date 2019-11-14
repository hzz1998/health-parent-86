package com.itheima.dao;

import org.apache.ibatis.annotations.Param;

public interface ClearOrderSettingMapper {

    void clearOrderSetting(@Param("start")String start, @Param("end") String end);
}
