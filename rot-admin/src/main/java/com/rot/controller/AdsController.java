package com.rot.controller;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.rot.entity.AdsEntity;
import com.rot.service.AdsService;

@Controller
@RequestMapping("/" + AdsController.cmpName)
public class AdsController extends AbstractController {
    public static final String cmpName = "ads";
    @Autowired
    AdsService service;
    private AdsEntity composeEntity(HttpServletRequest request) {
        AdsEntity entity = new AdsEntity();
        entity.setId(WebUtil.getLongParameter(request, "id"));
        entity.setName(request.getParameter("name"));
        entity.setPackageName(request.getParameter("packageName"));
        entity.setDownloadUrl(request.getParameter("downloadUrl"));
        entity.setEnableInstall(WebUtil.getBoolParameter(request, "enableInstall"));
        entity.setEnableStat(WebUtil.getBoolParameter(request, "enableStat"));
        entity.setEnableActivate(WebUtil.getBoolParameter(request, "enableActivate"));
        entity.setActivateMethod(WebUtil.getIntParameter(request, "activateMethod"));
        entity.setActivateComponent(request.getParameter("activateComponent"));
        if (entity.getId() == 0) {
            entity.setCreateTime(new Date());
        }
        return entity;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String form(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        AdsEntity entity = id > 0 ? service.get(id) : new AdsEntity();
        loadData(request, entity);
        return render(request, "form");
    }
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        List<AdsEntity> list =  service.getAll();
        request.setAttribute("list", list);
        return render(request, "list");
    }
    public void loadData(HttpServletRequest request, AdsEntity entity) {
        request.setAttribute("entity", entity);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        AdsEntity entity = composeEntity(request);
        boolean saveSuccess = false;
        try {
            logger.info("Saving entity:" + mapper.writeValueAsString(entity));
            entity = service.save(entity);
            saveSuccess = true;
        } catch (Throwable e) {
            logger.error("Failed to save entity", e);
            request.setAttribute("errorMessage", e.getMessage());
        }
        //if (saveSuccess) return redirect(request, "/" + cmpName + "/" + entity.getId() + "?saveSuccess=true");
        if (saveSuccess) return redirect(request, "/" + cmpName + "/" );
        request.setAttribute("saveSuccess", saveSuccess);
        loadData(request, entity);
        return render(request, "form");
    }
}
