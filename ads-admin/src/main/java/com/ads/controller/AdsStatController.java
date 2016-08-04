package com.ads.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ads.entity.AdsEntity;
import com.ads.entity.AdsStatEntity;
import com.ads.entity.AdvertiserEntity;
import com.ads.entity.AppEntity;
import com.ads.entity.CpChannelEntity;
import com.ads.entity.CpEntity;
import com.ads.service.AdsService;
import com.ads.service.AdsStatService;
import com.ads.service.AdvertiserService;
import com.ads.service.AppService;
import com.ads.service.CpChannelService;
import com.ads.service.CpService;
import com.ads.service.FormatUtil;

@Controller
@RequestMapping("/" + AdsStatController.cmpName)
public class AdsStatController extends AbstractController {
    public static final String cmpName = "adsStat";
    @Autowired
    private AppService appService;
    @Autowired
    private CpService cpService;
    @Autowired
    private AdvertiserService advertiserService;
    @Autowired
    private AdsService adsService;
    @Autowired
    private CpChannelService cpChannelService;
    @Autowired
    private AdsStatService adsStatService;
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        long cpId = WebUtil.getLongParameter(request, "cpId");
        long advertiserId = WebUtil.getLongParameter(request, "advertiserId");
        long appId = WebUtil.getLongParameter(request, "appId");
        long adsId = WebUtil.getLongParameter(request, "adsId");
        int channelId = WebUtil.getIntParameter(request, "channelId");
        String toDate = request.getParameter("toDate");
        String groupName = request.getParameter("groupName");
        if(StringUtils.isBlank(groupName)){
            groupName="date";
        }
        if (StringUtils.isBlank(toDate)) {
            toDate = FormatUtil.format(new Date());
        }
        String fromDate = request.getParameter("fromDate");
        if (StringUtils.isBlank(fromDate)) {
            fromDate = FormatUtil.format(new Date(System.currentTimeMillis() - 1000L * 3600L * 24 * 20));
        }
        List<AdvertiserEntity> advertiserList = this.advertiserService.getAll();
        List<CpEntity> cpList = this.cpService.getAll();
        List<AppEntity> appList = this.appService.getAll();
        List<AdsEntity> adsList = this.adsService.getAll();
        List<CpChannelEntity> channelList = cpId > 0 ? this.cpChannelService.findByCpId(cpId) : new ArrayList<CpChannelEntity>();
        request.setAttribute("advertiserList", advertiserList);
        request.setAttribute("cpList", cpList);
        request.setAttribute("appList", appList);
        request.setAttribute("adsList", adsList);
        request.setAttribute("channelList", channelList);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("groupName", groupName);
        List<AdsStatEntity> list = this.adsStatService.getDateStat(groupName, fromDate, toDate, advertiserId, adsId, cpId, channelId, appId);
        if (list != null) {
            Collections.sort(list, new Comparator<AdsStatEntity>() {
                @Override
                public int compare(AdsStatEntity o1, AdsStatEntity o2) {
                    int result = o2.getStatDate().compareTo(o1.getStatDate());
                    return result;
                }
            });
            request.setAttribute("list", list);
        }
        return render(request, "list");
    }
}
