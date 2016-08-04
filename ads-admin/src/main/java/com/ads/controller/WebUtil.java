package com.ads.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.ads.service.FormatUtil;

public class WebUtil {
    public static long getBusinessSecondsBetween(Date time1, Date time2, long startHour, long endHour) {
        long freeHours = 24 - (endHour - startHour + 1);
        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime(time1);
        int passedDays = getDaysBetween(lastTime, Calendar.getInstance());
        return (time2.getTime() - lastTime.getTimeInMillis() - passedDays * freeHours * 3600 * 1000) / 1000;
    }
    public static int getDaysBetween(Calendar d1, Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }
    public static int getIntParameter(HttpServletRequest request, String name) {
        int result = 0;
        try {
            result = Integer.valueOf(request.getParameter(name));
        } catch (Exception e) {}
        return result;
    }
    public static int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        int result = defaultValue;
        String value = request.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            try {
                result = Integer.valueOf(request.getParameter(name));
            } catch (Exception e) {}
        }
        return result;
    }
    public static long getLongParameter(HttpServletRequest request, String name) {
        long result = 0;
        try {
            result = Long.valueOf(request.getParameter(name));
        } catch (Exception e) {}
        return result;
    }
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static Map<String, Object> getStatParmas(HttpServletRequest request) {
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        long bizId = getLongParameter(request, "bizId");
        long provinceId = getLongParameter(request, "provinceId");
        int channelId = getIntParameter(request, "channelId");
        long cpId = getLongParameter(request, "cpId");
        long appId = getLongParameter(request, "appId");
        long spId = getLongParameter(request, "spId");
        int carrierOperator = getIntParameter(request, "carrierOperator");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(fromDate)) {
            fromDate = FormatUtil.format(new Date(System.currentTimeMillis() - 10L * 3600 * 24*1000));
        }
        map.put("fromDate", fromDate);
        request.setAttribute("fromDate", fromDate);
        if (StringUtils.isBlank(toDate)) {
            toDate = FormatUtil.format(new Date());
        }
        map.put("toDate", toDate);
        request.setAttribute("toDate", toDate);
        if (bizId > 0) map.put("bizId", bizId);
        if (provinceId > 0) map.put("provinceId", provinceId);
        if (channelId > 0) map.put("channelId", channelId);
        if (appId > 0) map.put("appId", appId);
        if (cpId > 0) map.put("cpId", cpId);
        if (spId > 0) map.put("spId", spId);
        if (carrierOperator > 0) map.put("carrierOperator", carrierOperator);
        return map;
    }
    public static void write(HttpServletRequest request, HttpServletResponse response, Object obj2) {
        if (obj2 != null) {
            String s = obj2.toString();
            PrintWriter out = null;
            response.setHeader("Content-Encoding", "gzip");
            try {
                out = new PrintWriter(new GZIPOutputStream(response.getOutputStream()));
                out.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) out.close();
            }
        }
    }
    public static void write(HttpServletResponse response, Object obj) {
        if (obj != null) {
            PrintWriter out = null;
            try {
                out = new PrintWriter(response.getOutputStream());
                String content = obj.toString();
                out.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) out.close();
            }
        }
    }
}
