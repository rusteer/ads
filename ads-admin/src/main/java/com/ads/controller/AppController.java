package com.ads.controller;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ads.entity.AppEntity;
import com.ads.service.AppService;
import com.ads.service.CpService;

@Controller
@RequestMapping("/" + AppController.cmpName)
public class AppController extends AbstractController {
    public static final String cmpName = "app";
    @Autowired
    private AppService service;
    @Autowired
    private CpService cpService;
    private AppEntity composeEntity(HttpServletRequest request) {
        AppEntity entity = new AppEntity();
        entity.setId(WebUtil.getLongParameter(request, "id"));
        entity.setName(request.getParameter("name"));
        entity.setDescription(request.getParameter("description"));
        entity.setCpId(WebUtil.getLongParameter(request, "cpId"));
        entity.setEnabled("1".equals(request.getParameter("enabled")));
        if (entity.getId() == 0) {
            entity.setCreateTime(new Date());
        }
        return entity;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String form(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        long cpId = WebUtil.getLongParameter(request, "cpId");
        AppEntity entity = id > 0 ? service.get(id) : new AppEntity();
        if (entity.getCpId() == null) {
            entity.setCpId(cpId);
        }
        loadData(request, entity);
        return render(request, "form");
    }
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("cpList", cpService.getAll());
        long cpId = WebUtil.getLongParameter(request, "cpId");
        List<AppEntity> list = cpId > 0 ? service.findByCpId(cpId) : service.getAll();
        Collections.sort(list, new Comparator<AppEntity>() {
            @Override
            public int compare(AppEntity o1, AppEntity o2) {
                int result = Boolean.valueOf(o2.isEnabled()).compareTo(o1.isEnabled());
                if (result == 0) {
                    result = o1.getCpId().compareTo(o2.getCpId());
                }
                return result;
            }
        });
        request.setAttribute("list", list);
        return render(request, "list");
    }
    public void loadData(HttpServletRequest request, AppEntity entity) {
        request.setAttribute("entity", entity);
        request.setAttribute("cpList", cpService.getAll());
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        AppEntity entity = composeEntity(request);
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
