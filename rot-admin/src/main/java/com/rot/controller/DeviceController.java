package com.rot.controller;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.rot.entity.ActivateCount;
import com.rot.entity.AdsEntity;
import com.rot.service.DeviceService;
import com.rot.service.FormatUtil;

@Controller
@RequestMapping("/" + DeviceController.cmpName)
public class DeviceController extends AbstractController {
    public static final String cmpName = "device";
    @Autowired
    DeviceService service;
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        String toDate = request.getParameter("toDate");
        if (StringUtils.isBlank(toDate)) {
            toDate = FormatUtil.format(new Date());
        }
        request.setAttribute("toDate", toDate);
        String fromDate = request.getParameter("fromDate");
        if (StringUtils.isBlank(fromDate)) {
            fromDate = FormatUtil.format(new Date(System.currentTimeMillis() - 1000L * 3600L * 24 * 20));
        }
        int systemApp=WebUtil.getIntParameter(request, "systemApp");
        List<ActivateCount> stat = this.service.getStat(fromDate, toDate,systemApp);
        request.setAttribute("list", stat);
        request.setAttribute("fromDate", fromDate);
        return render(request, "list");
    }
    public void loadData(HttpServletRequest request, AdsEntity entity) {
        request.setAttribute("entity", entity);
    }
}
