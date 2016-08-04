// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.aora.base.util.StringUtil;
import com.gionee.aora.download.DownloadInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppInfo
    implements Serializable
{

    public AppInfo()
    {
        name = "";
        packageName = "";
        curVersionName = "";
        updateVersionName = "";
        iconUrl = "";
        downloadUrl = "";
        size = "";
        appStars = 0.0F;
        isGioneeFlag = false;
        proulgateTime = "";
        download_region = "";
        developer = "";
        comment_count = "";
        isSatety = false;
        isFree = false;
        isNotAD = true;
        isPlugin = true;
        isVirus = true;
        description = "";
        softId = "";
        integral = 0;
        comment = "";
        isOfficial = false;
        vId = 0;
        isShowInstalledList = false;
        isSameSign = true;
        iconFlag = 0;
        md5 = "";
        oneScreenShotUrl = "";
        isCommented = false;
        videoUrl = "";
        cpAction = "";
        downloadtrend = 0;
        usetime = 0L;
    }

    public AppInfo(int i, String s, String s1, int j, String s2, String s3, String s4, 
            String s5, int k, int l, String s6, int i1)
    {
        name = "";
        packageName = "";
        curVersionName = "";
        updateVersionName = "";
        iconUrl = "";
        downloadUrl = "";
        size = "";
        appStars = 0.0F;
        isGioneeFlag = false;
        proulgateTime = "";
        download_region = "";
        developer = "";
        comment_count = "";
        isSatety = false;
        isFree = false;
        isNotAD = true;
        isPlugin = true;
        isVirus = true;
        description = "";
        softId = "";
        integral = 0;
        comment = "";
        isOfficial = false;
        vId = 0;
        isShowInstalledList = false;
        isSameSign = true;
        iconFlag = 0;
        md5 = "";
        oneScreenShotUrl = "";
        isCommented = false;
        videoUrl = "";
        cpAction = "";
        downloadtrend = 0;
        usetime = 0L;
        id = i;
        name = s;
        packageName = s1;
        curVersionCode = j;
        curVersionName = s2;
        updateVersionName = s3;
        iconUrl = s4;
        downloadUrl = s5;
        updateSoftSize = k;
        curState = l;
        softId = s6;
        integral = i1;
    }

    public AppInfo(String s, String s1, String s2, float f, String s3, String s4, String s5, 
            String s6, int i)
    {
        name = "";
        packageName = "";
        curVersionName = "";
        updateVersionName = "";
        iconUrl = "";
        downloadUrl = "";
        size = "";
        appStars = 0.0F;
        isGioneeFlag = false;
        proulgateTime = "";
        download_region = "";
        developer = "";
        comment_count = "";
        isSatety = false;
        isFree = false;
        isNotAD = true;
        isPlugin = true;
        isVirus = true;
        description = "";
        softId = "";
        integral = 0;
        comment = "";
        isOfficial = false;
        vId = 0;
        isShowInstalledList = false;
        isSameSign = true;
        iconFlag = 0;
        md5 = "";
        oneScreenShotUrl = "";
        isCommented = false;
        videoUrl = "";
        cpAction = "";
        downloadtrend = 0;
        usetime = 0L;
        softId = s;
        iconUrl = s1;
        name = s2;
        appStars = f;
        packageName = s3;
        size = s4;
        downloadUrl = s5;
        describe = s6;
        integral = i;
    }

    public AppInfo(String s, String s1, String s2, String s3, String s4, long l, 
            String s5, int i, String s6)
    {
        name = "";
        packageName = "";
        curVersionName = "";
        updateVersionName = "";
        iconUrl = "";
        downloadUrl = "";
        size = "";
        appStars = 0.0F;
        isGioneeFlag = false;
        proulgateTime = "";
        download_region = "";
        developer = "";
        comment_count = "";
        isSatety = false;
        isFree = false;
        isNotAD = true;
        isPlugin = true;
        isVirus = true;
        description = "";
        softId = "";
        integral = 0;
        comment = "";
        isOfficial = false;
        vId = 0;
        isShowInstalledList = false;
        isSameSign = true;
        iconFlag = 0;
        md5 = "";
        oneScreenShotUrl = "";
        isCommented = false;
        videoUrl = "";
        cpAction = "";
        downloadtrend = 0;
        usetime = 0L;
        softId = s;
        iconUrl = s1;
        name = s2;
        packageName = s3;
        curVersionName = s5;
        updateSoftSize = (int)l;
        downloadUrl = s4;
        integral = i;
        download_region = s6;
    }

    public boolean equals(Object obj)
    {
        if(this != obj)
        {
            if(obj == null)
                return false;
            if(!(obj instanceof AppInfo))
                return false;
            AppInfo appinfo = (AppInfo)obj;
            if(!getName().equals(appinfo.getName()))
                return false;
            if(getCurState() != appinfo.getCurVersionCode())
                return false;
        }
        return true;
    }

    public float getAppStars()
    {
        return appStars;
    }

    public List<AuthenticateItem> getAuthenticateItems()
    {
        return authenticateItems;
    }

    public int getClassify()
    {
        return classify;
    }

    public int getClassifyId()
    {
        return classifyId;
    }

    public String getClassifyName()
    {
        return classifyName;
    }

    public List<CategoryInfo> getClassifyThree()
    {
        return classifyThree;
    }

    public int getClassifyThreeId()
    {
        return classifyThreeId;
    }

    public String getClassifyThreeName()
    {
        return classifyThreeName;
    }

    public String getComment()
    {
        return comment;
    }

    public String getComment_count()
    {
        return comment_count;
    }

    public String getCpAction()
    {
        return cpAction;
    }

    public int getCurState()
    {
        return curState;
    }

    public int getCurVersionCode()
    {
        return curVersionCode;
    }

    public String getCurVersionName()
    {
        return curVersionName;
    }

    public String getDescribe()
    {
        return describe;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public String getDownload_region()
    {
        return download_region;
    }

    public int getDownloadtrend()
    {
        return downloadtrend;
    }

    public int getIconFlag()
    {
        return iconFlag;
    }

    public int getIconFlagResId(int i)
    {
        switch(i)
        {
        default:
            return 0;

        case 1: // '\001'
            return biz.AR.drawable.special_list_boutique_flag;

        case 2: // '\002'
            return biz.AR.drawable.special_list_unique_flag;

        case 3: // '\003'
            return biz.AR.drawable.special_list_first_flag;

        case 4: // '\004'
            return biz.AR.drawable.special_list_recommend_flag;

        case 5: // '\005'
            return biz.AR.drawable.special_list_hot_flag;

        case 6: // '\006'
            return biz.AR.drawable.special_list_event_flag;

        case 7: // '\007'
            return biz.AR.drawable.special_list_gift_flag;

        case 8: // '\b'
            return biz.AR.drawable.special_list_awards_flag;
        }
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public int getId()
    {
        return id;
    }

    public int getIntegral()
    {
        return integral;
    }

    public long getLongSize()
    {
        return longSize;
    }

    public String getMd5()
    {
        return md5;
    }

    public String getName()
    {
        return name;
    }

    public String getNewUpdateInfos()
    {
        return newUpdateInfos;
    }

    public String getOneScreenShotUrl()
    {
        return oneScreenShotUrl;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public List getPermission()
    {
        return permission;
    }

    public String getPraiseCount()
    {
        return praiseCount;
    }

    public String getPresenter()
    {
        return presenter;
    }

    public String getProulgateTime()
    {
        return proulgateTime;
    }

    public String getRecommendDes()
    {
        return recommendDes;
    }

    public String getRelApkUrl()
    {
        return relApkUrl;
    }

    public ArrayList getScreenShotsUrl()
    {
        return screenShotsUrl;
    }

    public ArrayList getScreenShotsUrlNotHD()
    {
        return screenShotsUrlNotHD;
    }

    public String getSign()
    {
        return sign;
    }

    public String getSize()
    {
        return size;
    }

    public String getSoftId()
    {
        return softId;
    }

    public String getSource()
    {
        return source;
    }

    public String getSpecialSkipUrl()
    {
        return specialSkipUrl;
    }

    public String getSpecialTitle()
    {
        return SpecialTitle;
    }

    public long getUpdateApkSize()
    {
        return updateApkSize;
    }

    public String getUpdatePercent()
    {
        return updatePercent;
    }

    public int getUpdateSoftSize()
    {
        return updateSoftSize;
    }

    public String getUpdateSoftSizeFormatString()
    {
        return StringUtil.getFormatSize(getUpdateSoftSize());
    }

    public String getUpdateVersionName()
    {
        return updateVersionName;
    }

    public Long getUsetime()
    {
        return Long.valueOf(usetime);
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public String getWapUrl()
    {
        return wapUrl;
    }

    public int getvId()
    {
        return vId;
    }

    public boolean isCommented()
    {
        return isCommented;
    }

    public boolean isDifferenceUpgrade()
    {
        return isDifferenceUpgrade;
    }

    public boolean isFree()
    {
        return isFree;
    }

    public boolean isGioneeFlag()
    {
        return isGioneeFlag;
    }

    public boolean isInlay()
    {
        return isInlay;
    }

    public boolean isNotAD()
    {
        return isNotAD;
    }

    public boolean isOfficial()
    {
        return isOfficial;
    }

    public boolean isPlugin()
    {
        return isPlugin;
    }

    public boolean isPraise()
    {
        return isPraise;
    }

    public boolean isPromptUpgreade()
    {
        return isPromptUpgreade;
    }

    public boolean isSameSign()
    {
        return isSameSign;
    }

    public boolean isSatety()
    {
        return isSatety;
    }

    public boolean isShowInstalledList()
    {
        return isShowInstalledList;
    }

    public boolean isVirus()
    {
        return isVirus;
    }

    public void setAppStars(float f)
    {
        appStars = f;
    }

    public void setAuthenticateItems(List list)
    {
        authenticateItems = list;
    }

    public void setClassify(int i)
    {
        classify = i;
    }

    public void setClassifyId(int i)
    {
        classifyId = i;
    }

    public void setClassifyName(String s)
    {
        classifyName = s;
    }

    public void setClassifyThree(List list)
    {
        classifyThree = list;
    }

    public void setClassifyThreeId(int i)
    {
        classifyThreeId = i;
    }

    public void setClassifyThreeName(String s)
    {
        classifyThreeName = s;
    }

    public void setComment(String s)
    {
        comment = s;
    }

    public void setComment_count(String s)
    {
        comment_count = s;
    }

    public void setCommented(boolean flag)
    {
        isCommented = flag;
    }

    public void setCpAction(String s)
    {
        cpAction = s;
    }

    public void setCurState(int i)
    {
        curState = i;
    }

    public void setCurVersionCode(int i)
    {
        curVersionCode = i;
    }

    public void setCurVersionName(String s)
    {
        curVersionName = s;
    }

    public void setDescribe(String s)
    {
        describe = s;
    }

    public void setDescription(String s)
    {
        description = s;
    }

    public void setDeveloper(String s)
    {
        developer = s;
    }

    public void setDifferenceUpgrade(boolean flag)
    {
        isDifferenceUpgrade = flag;
    }

    public void setDownloadUrl(String s)
    {
        downloadUrl = s;
    }

    public void setDownload_region(String s)
    {
        download_region = s;
    }

    public void setDownloadtrend(int i)
    {
        downloadtrend = i;
    }

    public void setFree(boolean flag)
    {
        isFree = flag;
    }

    public void setGioneeFlag(boolean flag)
    {
        isGioneeFlag = flag;
    }

    public void setIconFlag(int i)
    {
        iconFlag = i;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setId(int i)
    {
        id = i;
    }

    public void setInlay(boolean flag)
    {
        isInlay = flag;
    }

    public void setIntegral(int i)
    {
        integral = i;
    }

    public void setLongSize(long l)
    {
        longSize = l;
    }

    public void setMd5(String s)
    {
        md5 = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setNewUpdateInfos(String s)
    {
        newUpdateInfos = s;
    }

    public void setNotAD(boolean flag)
    {
        isNotAD = flag;
    }

    public void setOfficial(boolean flag)
    {
        isOfficial = flag;
    }

    public void setOneScreenShotUrl(String s)
    {
        oneScreenShotUrl = s;
    }

    public void setPackageName(String s)
    {
        packageName = s;
    }

    public void setPermission(List list)
    {
        permission = list;
    }

    public void setPlugin(boolean flag)
    {
        isPlugin = flag;
    }

    public void setPraise(boolean flag)
    {
        isPraise = flag;
    }

    public void setPraiseCount(String s)
    {
        praiseCount = s;
    }

    public void setPresenter(String s)
    {
        presenter = s;
    }

    public void setPromptUpgreade(boolean flag)
    {
        isPromptUpgreade = flag;
    }

    public void setProulgateTime(String s)
    {
        proulgateTime = s;
    }

    public void setRecommendDes(String s)
    {
        recommendDes = s;
    }

    public void setRelApkUrl(String s)
    {
        relApkUrl = s;
    }

    public void setSameSign(boolean flag)
    {
        isSameSign = flag;
    }

    public void setSatety(boolean flag)
    {
        isSatety = flag;
    }

    public void setScreenShotsUrl(ArrayList arraylist)
    {
        screenShotsUrl = arraylist;
    }

    public void setScreenShotsUrlNotHD(ArrayList arraylist)
    {
        screenShotsUrlNotHD = arraylist;
    }

    public void setShowInstalledList(boolean flag)
    {
        isShowInstalledList = flag;
    }

    public void setSign(String s)
    {
        sign = s;
    }

    public void setSize(String s)
    {
        size = s;
    }

    public void setSoftId(String s)
    {
        softId = s;
    }

    public void setSource(String s)
    {
        source = s;
    }

    public void setSpecialSkipUrl(String s)
    {
        specialSkipUrl = s;
    }

    public void setSpecialTitle(String s)
    {
        SpecialTitle = s;
    }

    public void setUpdateApkSize(long l)
    {
        updateApkSize = l;
    }

    public void setUpdatePercent(String s)
    {
        updatePercent = s;
    }

    public void setUpdateSoftSize(int i)
    {
        updateSoftSize = i;
    }

    public void setUpdateVersionName(String s)
    {
        updateVersionName = s;
    }

    public void setUsetime(Long long1)
    {
        usetime = long1.longValue();
    }

    public void setVideoUrl(String s)
    {
        videoUrl = s;
    }

    public void setVirus(boolean flag)
    {
        isVirus = flag;
    }

    public void setWapUrl(String s)
    {
        wapUrl = s;
    }

    public void setvId(int i)
    {
        vId = i;
    }

    public DownloadInfo toDownloadInfo()
    {
        DownloadInfo downloadinfo = new DownloadInfo(getName(), getPackageName(), getDownloadUrl(), getIconUrl(), getUpdateSoftSize(), softId, integral);
        downloadinfo.setRelApkSize(getUpdateApkSize());
        downloadinfo.setRelApkUrl(getRelApkUrl());
        return downloadinfo;
    }

    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("").append("---------------------").append((new StringBuilder()).append("\n id:").append(getId()).toString()).append((new StringBuilder()).append("\n name:").append(getName()).toString()).append((new StringBuilder()).append("\n packageName:").append(getPackageName()).toString()).append((new StringBuilder()).append("\n curVersionCode:").append(getCurVersionCode()).toString()).append((new StringBuilder()).append("\n iconUrl:").append(getIconUrl()).toString()).append((new StringBuilder()).append("\n downloadUrl:").append(getDownloadUrl()).toString()).append((new StringBuilder()).append("\n updateSoftSize:").append(getUpdateSoftSize()).toString()).append((new StringBuilder()).append("\n curState:").append(getCurState()).toString());
        return stringbuffer.toString();
    }

    private static final long serialVersionUID = 0x12845609f0b605e5L;
    private String SpecialTitle;
    private float appStars;
    private List<AuthenticateItem> authenticateItems;
    private int classify;
    private int classifyId;
    private String classifyName;
    private List<CategoryInfo> classifyThree;
    private int classifyThreeId;
    private String classifyThreeName;
    private String comment;
    private String comment_count;
    private String cpAction;
    private int curState;
    private int curVersionCode;
    private String curVersionName;
    private String describe;
    private String description;
    private String developer;
    private String downloadUrl;
    private String download_region;
    private int downloadtrend;
    private int iconFlag;
    private String iconUrl;
    public int id;
    private int integral;
    private boolean isCommented;
    private boolean isDifferenceUpgrade;
    private boolean isFree;
    private boolean isGioneeFlag;
    private boolean isInlay;
    private boolean isNotAD;
    private boolean isOfficial;
    private boolean isPlugin;
    private boolean isPraise;
    private boolean isPromptUpgreade;
    private boolean isSameSign;
    private boolean isSatety;
    private boolean isShowInstalledList;
    private boolean isVirus;
    private long longSize;
    private String md5;
    private String name;
    private String newUpdateInfos;
    private String oneScreenShotUrl;
    private String packageName;
    private List permission;
    private String praiseCount;
    private String presenter;
    private String proulgateTime;
    private String recommendDes;
    private String relApkUrl;
    private ArrayList screenShotsUrl;
    private ArrayList screenShotsUrlNotHD;
    private String sign;
    private String size;
    private String softId;
    private String source;
    private String specialSkipUrl;
    private long updateApkSize;
    private String updatePercent;
    private int updateSoftSize;
    private String updateVersionName;
    private long usetime;
    private int vId;
    private String videoUrl;
    private String wapUrl;
}
