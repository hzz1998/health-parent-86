package com.itheima.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;

import com.itheima.pojo.Member;
import com.itheima.pojo.ReportDataVo;
import com.itheima.tool.GetAgeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;
    @Autowired
    MemberDao memberDao;
    @Autowired
    OrderDao orderDao;


    @Override

    public List<Integer> getMemberReport(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            Integer count = reportDao.getMemberReport(month);
            memberCount.add(count);
        }
        return memberCount;
    }

    @Override
    public List<Map> getSetmealReport() {
        return reportDao.getSetmealReport();
    }

    @Override
    public ReportDataVo getBusinessReportData() {
        DateTime now = DateTime.now();
        String format = "yyyy-MM-dd";
        String nowStr = now.toString(format);
        //本周第一天
        String beginOfWeek = DateUtil.beginOfWeek(now).toString(format);
//        本月第一天
        String beginOfMonth = DateUtil.beginOfMonth(now).toString(format);
        //本周最后一天
        String endOfWeek = DateUtil.endOfWeek(now).toString(format);
        //本月最后一天
        String endOfMonth = DateUtil.endOfMonth(now).toString(format);

        return ReportDataVo.builder()
                .reportDate(nowStr)
                .todayNewMember(memberDao.findMemberCountByDate(nowStr))
                .totalMember(memberDao.findMemberTotalCount())
                .thisWeekNewMember(memberDao.findMemberCountAfterDate(beginOfWeek))
                .thisMonthNewMember(memberDao.findMemberCountAfterDate(beginOfMonth))
                .todayOrderNumber(orderDao.findOrderCountByDate(nowStr))
                .todayVisitsNumber(orderDao.findVisitsCountByDate(nowStr))
                .thisWeekOrderNumber(orderDao.findOrderCountBetween(beginOfWeek, endOfWeek))
                .thisWeekVisitsNumber(orderDao.findVisitsOrderCountBetween(beginOfWeek, endOfWeek))
                .thisMonthOrderNumber(orderDao.findOrderCountBetween(beginOfMonth, endOfMonth))
                .thisMonthVisitsNumber(orderDao.findVisitsOrderCountBetween(beginOfMonth, endOfMonth))
                .hotSetmeal(orderDao.findHotSetmeal())
                .build();
    }

    @Override
    public List<Map> getSex() {
        Integer nan = memberDao.findNancount();
        Integer nv = memberDao.findNvcount();

        Map map1 = new HashMap();
        Map map2 = new HashMap();
        map1.put("value", nan);
        map1.put("name", "男");
        map2.put("value", nv);
        map2.put("name", "女");
        List<Map> maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);
        return maps;
    }

    @Override
    public List<Map> getAge() throws Exception {

        Integer yiba = 0;
        Integer sanlin = 0;
        Integer siwu = 0;
        Integer siwushang = 0;

        List<Member> members = memberDao.getMember();
        for (Member member : members) {
            Date regTime = member.getBirthday();
            int age = getAge(regTime);
            if (age >= 0 && age <= 18) {
                yiba = yiba + 1;
            } else if (age >= 18 && age <= 30) {
                sanlin = sanlin + 1;
            } else if (age >= 30 && age <= 45) {
                siwu = siwu + 1;
            } else if (age >= 45) {
                siwushang = siwushang + 1;
            }

        }

        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();


        map1.put("value", yiba);
        map1.put("name", "0-18");
        map2.put("value", sanlin);
        map2.put("name", "18-30");
        map3.put("value", siwu);
        map3.put("name", "30-45");
        map4.put("value", siwushang);
        map4.put("name", "45~");

        List<Map> maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        return maps;
    }

    @Override
    public List<Integer> queryCountByMonth(List<String> monthList) {
        List<Integer> list=new ArrayList<>();
        for(String month:monthList){
            Integer count = memberDao.queryCountByMonth(month);
                    list.add(count);
        }
        return list;
    }


    public static void main(String[] args) {
//        DateTime now = DateTime.now();
//        //本周第一天
//        DateTime beginOfWeek = DateUtil.beginOfWeek(now);
////        本月第一天
//        DateTime beginOfMonth = DateUtil.beginOfMonth(now);
//        //本周最后一天
//        DateTime endOfWeek = DateUtil.endOfWeek(now);
//        //本月最后一天
//        DateTime endOfMonth = DateUtil.endOfMonth(now);

//        Integer a = new Integer(129);
//        int b  = 129;
//        System.out.println(a == b);

        Integer n1 = 123;
        Integer n2 = 123;
        Integer n3 = 128;
        Integer n4 = 128;
        Integer n5 = -129;
        Integer n6 = -129;

        System.out.println(n1 == n2);
        System.out.println(n3 == n4);
        System.out.println(n5 == n6);
    }


    public  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }
}
