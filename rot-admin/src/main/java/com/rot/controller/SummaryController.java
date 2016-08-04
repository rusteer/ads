package com.rot.controller;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.rot.entity.AdsEntity;
import com.rot.service.AdsService;
import com.rot.service.AdsStatService;
import com.rot.service.FormatUtil;

@Controller
@RequestMapping("/" + SummaryController.cmpName)
public class SummaryController extends AbstractController {
    public static final String cmpName = "summary";
    @Autowired
    AdsStatService statService;
    @Autowired
    AdsService adsService;
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        long adsId = WebUtil.getLongParameter(request, "adsId");
        String toDate = request.getParameter("toDate");
        if (StringUtils.isBlank(toDate)) {
            toDate = FormatUtil.format(new Date());
        }
        request.setAttribute("toDate", toDate);
        String fromDate = request.getParameter("fromDate");
        if (StringUtils.isBlank(fromDate)) {
            fromDate = FormatUtil.format(new Date(System.currentTimeMillis() - 1000L * 3600L * 24 * 20));
        }
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("adsList", adsService.getAll());
        request.setAttribute("list", statService.getSummary(fromDate, toDate, adsId));
        return render(request, "list");
    }
    public void loadData(HttpServletRequest request, AdsEntity entity) {
        request.setAttribute("entity", entity);
    }
     
}
