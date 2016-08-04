package com.rot.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String format(Date date) {
        return dateFormat.format(date);
    }
    public static Date parse(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static void main(String[] args){
        Date d1=parse("2015-05-01");
        Date d2=parse("2015-05-05");
        long duration= (d2.getTime()-d1.getTime())/1000/3600/24;
        System.out.println(duration);
    }
}
