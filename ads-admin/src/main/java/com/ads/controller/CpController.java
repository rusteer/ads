package com.ads.controller;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ads.entity.CpEntity;
import com.ads.service.CpService;

@Controller
@RequestMapping("/" + CpController.cmpName)
public class CpController extends AbstractController {
    public static final String cmpName = "cp";
    @Autowired
    CpService service;
    private CpEntity composeEntity(HttpServletRequest request) {
        CpEntity cp = new CpEntity();
        cp.setName(request.getParameter("name"));
        cp.setLoginName(request.getParameter("loginName"));
        cp.setLoginPassword(request.getParameter("loginPassword"));
        cp.setDeviceDailyLimit(WebUtil.getIntParameter(request, "deviceDailyLimit"));
        cp.setDiscountRate(WebUtil.getIntParameter(request, "discountRate"));
        cp.setId(WebUtil.getLongParameter(request, "id"));
        if (cp.getId() == 0) {
            cp.setCreateTime(new Date());
        }
        return cp;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String form(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        CpEntity cp = id > 0 ? service.get(id) : new CpEntity();
        loadData(request, cp);
        return render(request, "form");
    }
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        List<CpEntity> list = service.getAll();
        request.setAttribute("list", list);
        return render(request, "list");
    }
    public void loadData(HttpServletRequest request, CpEntity cp) {
        request.setAttribute("entity", cp);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        CpEntity entity = composeEntity(request);
        boolean saveSuccess = false;
        try {
            logger.info("Saving entity:" + mapper.writeValueAsString(entity));
            entity = service.save(entity);
            saveSuccess = true;
        } catch (Throwable e) {
            logger.error("Failed to save entity", e);
            request.setAttribute("errorMessage", e.getMessage());
        }
        if (saveSuccess) return redirect(request, "/" + cmpName + "/" + entity.getId() + "?saveSuccess=true");
        request.setAttribute("saveSuccess", saveSuccess);
        loadData(request, entity);
        return render(request, "form");
    }
}
