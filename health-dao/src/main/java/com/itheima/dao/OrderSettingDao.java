package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {

    Integer findCountByDate(@Param("orderDate") Date orderDate);

    void edit(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByMonth(@Param("start")String start,@Param("end")String end);

    OrderSetting findByDate(@Param("orderDate")Date orderDate);

    void updateReservations(@Param("id") Integer id);

    int updateReservationsVersion(@Param("id") Integer id, @Param("version") int version);
}
