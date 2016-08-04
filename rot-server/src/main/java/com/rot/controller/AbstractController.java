package com.rot.controller;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @RequestMapping(value = "/p/")
    public void execute(HttpServletRequest request, HttpServletResponse response) {}
    protected String getRemoteAddr(HttpServletRequest request) {
        String result = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(result)) {
            result = request.getRemoteAddr();
        }
        return result;
    }
    protected String getRequestPath(HttpServletRequest request) {
        String requestPath = request.getHeader("REQUEST_PATH");
        if (StringUtils.isBlank(requestPath)) {
            String queryString = request.getQueryString();
            StringBuffer buf = javax.servlet.http.HttpUtils.getRequestURL(request);
            if (StringUtils.isNotBlank(queryString)) {
                buf.append("?").append(queryString);
            }
            requestPath = buf.toString();
        }
        return requestPath;
    }
     
    protected void write(HttpServletResponse response, Object obj) {
        if (obj != null) {
            PrintWriter out = null;
            try {
                out = new PrintWriter(response.getOutputStream());
                String content = obj.toString();
                out.write(content);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (out != null) out.close();
            }
        }
    }
    protected ObjectMapper mapper = new ObjectMapper();
}
