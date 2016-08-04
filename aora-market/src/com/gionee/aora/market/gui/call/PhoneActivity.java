// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.gionee.aora.market.gui.main.MainViewPagerAdapter;
import com.gionee.aora.market.gui.search.SearchActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.gui.view.MyViewPager;

// Referenced classes of package com.gionee.aora.market.gui.call:
//            DialPhoneFragment, ContactsFragment, CallRecordsFragment
public class PhoneActivity extends FragmentActivity implements android.widget.RadioGroup.OnCheckedChangeListener, android.support.v4.view.ViewPager.OnPageChangeListener {
    public static MyViewPager mViewPager;
    private MainViewPagerAdapter fragmentAdapter;
    private List fragmentList;
    public RadioGroup phonerg;
    public RadioButton rb[];
    public ChildTitleView title;
    public PhoneActivity() {
        rb = new RadioButton[3];
    }
    @Override
    public void onCheckedChanged(RadioGroup radiogroup, int i) {
        switch (i) {
            default:
                mViewPager.setCurrentItem(0);
                return;
            case biz.AR.id.phone_radio0:
                mViewPager.setCurrentItem(0);
                return;
            case biz.AR.id.phone_radio1:
                mViewPager.setCurrentItem(1);
                return;
            case biz.AR.id.phone_radio2:
                mViewPager.setCurrentItem(2);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.phone_main_layout);
        fragmentList = new ArrayList();
        DialPhoneFragment dialphonefragment = new DialPhoneFragment();
        ContactsFragment contactsfragment = new ContactsFragment();
        CallRecordsFragment callrecordsfragment = new CallRecordsFragment();
        fragmentList.add(dialphonefragment);
        fragmentList.add(contactsfragment);
        fragmentList.add(callrecordsfragment);
        title = (ChildTitleView) findViewById(biz.AR.id.phone_title);
        mViewPager = (MyViewPager) findViewById(biz.AR.id.phone_viewpager);
        phonerg = (RadioGroup) findViewById(biz.AR.id.phone_tabGroup);
        rb[0] = (RadioButton) findViewById(biz.AR.id.phone_radio0);
        rb[1] = (RadioButton) findViewById(biz.AR.id.phone_radio1);
        rb[2] = (RadioButton) findViewById(biz.AR.id.phone_radio2);
        title.setRightViewVisibility();
        title.setTitle("\u62E8\u6253\u7535\u8BDD");
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setOnPageChangeListener(this);
        phonerg.setOnCheckedChangeListener(this);
        fragmentAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(fragmentAdapter);
    }
    @Override
    public void onPageScrolled(int i, float f, int j) {}
    @Override
    public void onPageScrollStateChanged(int i) {}
    @Override
    public void onPageSelected(int i) {
        phonerg.check(rb[i].getId());
        SearchActivity.hideInputMethodManagerKeyStore(this);
    }
}
