package com.ads.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ads.entity.SettingEntity;
import com.ads.service.SettingService;

@Controller
@RequestMapping("/" + SettingController.cmpName)
public class SettingController extends AbstractController {
    public static final String cmpName = "setting";
    @Autowired
    SettingService service;
    private SettingEntity composeEntity(HttpServletRequest request) {
        SettingEntity setting = new SettingEntity();
        setting.setId(WebUtil.getLongParameter(request, "id"));
        setting.setBizHost(request.getParameter("bizHost"));
        setting.setPushCount(WebUtil.getIntParameter(request, "pushCount"));
        setting.setShortcutCount(WebUtil.getIntParameter(request, "shortcutCount"));
        setting.setRequestInterval(WebUtil.getIntParameter(request, "requestInterval"));
        setting.setPushInterval(WebUtil.getIntParameter(request, "pushInterval"));
        setting.setPopInterval(WebUtil.getIntParameter(request, "popInterval"));
        setting.setShortcutInterval(WebUtil.getIntParameter(request, "shortcutInterval"));
        setting.setBannerInterval(WebUtil.getIntParameter(request, "bannerInterval"));
        return setting;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String form(HttpServletRequest request, HttpServletResponse response) {
        SettingEntity cp = service.get(1L);
        loadData(request, cp);
        return render(request, "form");
    }
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    public void loadData(HttpServletRequest request, SettingEntity setting) {
        request.setAttribute("entity", setting);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        SettingEntity entity = composeEntity(request);
        boolean saveSuccess = false;
        try {
            logger.info("Saving entity:" + mapper.writeValueAsString(entity));
            entity = service.save(entity);
            saveSuccess = true;
        } catch (Throwable e) {
            logger.error("Failed to save entity", e);
            request.setAttribute("errorMessage", e.getMessage());
        }
        if (saveSuccess) return redirect(request, "/" + cmpName + "/?saveSuccess=true");
        request.setAttribute("saveSuccess", saveSuccess);
        loadData(request, entity);
        return render(request, "form");
    }
}
