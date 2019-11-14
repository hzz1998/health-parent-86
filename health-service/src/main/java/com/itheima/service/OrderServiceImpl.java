package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderInfoVo;
import com.itheima.pojo.OrderSetting;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderSettingDao orderSettingDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public  Result submit(OrderInfoVo orderInfoVo) {
        Date orderDate = orderInfoVo.getOrderDate();
        String telephone = orderInfoVo.getTelephone();
        String name = orderInfoVo.getName();
        Integer sex = orderInfoVo.getSex();
        String idCard = orderInfoVo.getIdCard();
        Integer setmealId = orderInfoVo.getSetmealId();
        String orderType = orderInfoVo.getOrderType();

        //根据用户选择的预约日期查询是否存在预约设置
        OrderSetting orderSetting = orderSettingDao.findByDate(orderDate);
        //223 222 线程1 version=0
        //223 222 线程2 version=0
        //223 222 线程3 version=0

        if(null == orderSetting){
            return Result.error("没有档期");//没有档期 文案需要产品提供
        }
        //判断预约是否已满
        int number = orderSetting.getNumber();//总预约人数
        int reservations = orderSetting.getReservations();//已预约人数
        if(reservations >= number){
            return Result.error("预约已满");
        }
        //根据手机号码查询是否是我们的会员
        Member member = memberDao.findByTelephone(telephone);
        //如果不是会员，自动注册
        if(null == member){
            member = new Member();//要new
            member.setPhoneNumber(telephone);
            member.setName(name);
            member.setSex(String.valueOf(sex));
            member.setIdCard(idCard);
            member.setRegTime(new Date());
            //如果要想得到会员id，sql文件就得写主键回显
            memberDao.add(member);
        }
        Integer memberId = member.getId();
        //根据会员id，套餐id，预约日期查询是否已经存在预约
        Order query = new Order();
        query.setSetmealId(setmealId);
        query.setMemberId(memberId);
        query.setOrderDate(orderDate);
        List<Order> orders = orderDao.findByCondition(query);
        if(CollectionUtil.isNotEmpty(orders)){
            return Result.error("不要重复预约");
        }
        //创建预约订单
        Order order = new Order();
        order.setOrderDate(orderDate);
        order.setMemberId(memberId);
        order.setSetmealId(setmealId);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(orderType);
        orderDao.add(order);
        //修改已预约人数 + 1
        //把reservations默认值修改为0 （update t_ordersetting set reservations = 0;）
//        orderSettingDao.updateReservations(orderSetting.getId());
        int i = orderSettingDao.updateReservationsVersion(orderSetting.getId(), orderSetting.getVersion());//mysql inodb有行锁
        if(i == 0){
            throw new RuntimeException("");
        }
        //返回预约成功
        return Result.success("",order);
    }

    @Override
    public Map findById(Integer id) {
        return orderDao.findById4Detail(id);
    }
}
