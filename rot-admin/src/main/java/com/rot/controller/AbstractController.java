package com.rot.controller;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rot.entity.UserEntity;
import com.rot.service.framework.AccountService;

public abstract class AbstractController {
    @Autowired
    private AccountService accountService;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected ObjectMapper mapper = new ObjectMapper();
    protected abstract String getCmpName();
    protected String redirect(HttpServletRequest request, String url) {
        request.setAttribute("url", url);
        return "redirect";
    }
    protected boolean isAdmin() {
        UserEntity user = this.accountService.getCurrentUser();
        return user != null && "admin".equals(user.getRoles());
    }
    protected String render(HttpServletRequest request, String name) {
        boolean isAdmin = this.isAdmin();
        request.setAttribute("isAdmin", isAdmin);
        String cmpName = this.getCmpName();
        request.setAttribute("cmpName", cmpName);
        if (isAdmin || "device".equals(cmpName)) { //如果不是管理员,只显示设备数据
            return getCmpName() + "/" + name;
        }
        return redirect(request, "/device/?systemApp=-1");
    }
}
