package com.itheima.tool;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.Calendar;
import java.util.Date;
@Component
public class GetAgeUtil {

    public  Integer getAgeNow(String dataOfBirth) {
        try {
            //此处是获得的年龄
            int age = getAge(parse(dataOfBirth));           //由出生日期获得年龄***
            return age;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //出生日期字符串转化成Date对象
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    //由出生日期获得年龄
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
