package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IMChiLe on 2019/4/8 11:28
 */
public class Time {
    public String getTimeymd() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String testDate=df.format(new Date());//格式化当前日期
        return testDate;
    }

    /*public static void main(String[] args) {
        Time time = new Time();
        System.out.println(time.getTimeymd());
    }*/
}
