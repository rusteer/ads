package com.rot.service;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
    public static String format(Date date) {
        return dateFormat.format(date);
    }
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
}
