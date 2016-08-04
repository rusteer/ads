package com.ads.controller;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected ObjectMapper mapper = new ObjectMapper();
    protected abstract String getCmpName();
    protected String redirect(HttpServletRequest request, String url) {
        request.setAttribute("url", url);
        return "redirect";
    }
    protected String render(HttpServletRequest request, String name) {
        request.setAttribute("cmpName", getCmpName());
        return getCmpName() + "/" + name;
    }
}
