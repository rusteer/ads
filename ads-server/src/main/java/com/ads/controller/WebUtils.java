package com.ads.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class WebUtils {
    public static String getRemoteAddr(HttpServletRequest request) {
        String result = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(result)) {
            result = request.getRemoteAddr();
        }
        return result;
    }
}
