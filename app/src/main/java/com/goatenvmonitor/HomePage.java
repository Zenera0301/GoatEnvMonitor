package com.goatenvmonitor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.goatenvmonitor.fragment.HomePage_Home_Fragment;
import com.goatenvmonitor.fragment.HomePage_Mine_Fragment;
import com.goatenvmonitor.fragment.HomePage_Monitor_Fragment;
import com.goatenvmonitor.fragment.HomePage_ThunderSet_Fragment;

import java.util.ArrayList;


public class HomePage extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    /**
     * 准备工作
     */
    private ArrayList<Fragment> fragments;
    private BottomNavigationBar bottomNavigationBar;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment fragment;
    private Toolbar toolbar;
    private HomePage_Home_Fragment homePage_home_Fragment;  // HomePage中的第一个Fragment：Home
    private HomePage_Monitor_Fragment homePage_monitor_Fragment;  // HomePage中的第二个Fragment：Monitor
    private HomePage_ThunderSet_Fragment homePage_thunderset_Fragment;  // HomePage中的第三个Fragment：ThunderSet
    private HomePage_Mine_Fragment homePage_mine_Fragment;  // HomePage中的第四个Fragment：Mine


    private static final String HomePage_Monitor_FRAGMENT_KEY = "DashboardFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // android:theme="@style/Theme.AppCompat.Light.NoActionBar">">
        StatusBarUtil.setImmersiveStatusBar(this,true);  // 沉浸式状态栏

        init();  // 初始化控件
        fragments = getFragments();  // 获得所有fragment
        onTabSelected(0);  // 设置默认
    }


    private ArrayList<Fragment> getFragments() {
        // 实例化Fragment
        homePage_home_Fragment = new HomePage_Home_Fragment();  // HomePage中的第一个Fragment
        homePage_monitor_Fragment = new HomePage_Monitor_Fragment();  // HomePage中的第二个Fragment
        homePage_thunderset_Fragment = new HomePage_ThunderSet_Fragment();  // HomePage中的第三个Fragment
        homePage_mine_Fragment = new HomePage_Mine_Fragment();  // HomePage中的第四个Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(homePage_home_Fragment.newInstance("主页"));
        fragments.add(homePage_monitor_Fragment.newInstance("环境监控"));
        fragments.add(homePage_thunderset_Fragment.newInstance("阈值设定"));
        fragments.add(homePage_mine_Fragment.newInstance("我的"));
        return fragments;
    }


    private void init() {
        bottomNavigationBar  = findViewById(R.id.bottom_Navigation_Bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.homepagegray,"主页").setActiveColorResource(R.color.pigcolor))
                .addItem(new BottomNavigationItem(R.drawable.monitorgray, "环境监控").setActiveColorResource(R.color.pigcolor))
                .addItem(new BottomNavigationItem(R.drawable.thunderset, "阈值设定").setActiveColorResource(R.color.pigcolor))
                .addItem(new BottomNavigationItem(R.drawable.minegray, "我的").setActiveColorResource(R.color.pigcolor))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(int position) {
        if (fragments != null){
            if (position < fragments.size()){
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                fragment = fragments.get(position);
                String str = Integer.toString(position);
                if (fragment.isAdded()||fragment.isHidden()){
                    ft.show(fragment);
                }
                else {
                    ft.add(R.id.fl_base,fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }


    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                fragment = fragments.get(position);
                String str = Integer.toString(position);
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }


    @Override
    public void onTabReselected(int position) {
        if (fragments != null){
            if (position < fragments.size()){
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                fragment = fragments.get(position);
                String str = Integer.toString(position);
                if (fragment.isAdded()||fragment.isHidden()){
                    ft.show(fragment);
                }else {
                }
                ft.commitAllowingStateLoss();
            }
        }
    }


    /**
     * 重新加载布局
     **/
    public void reLoadFragView(){
        // 现将该fragment从fragmentList移除
        if (fragments.contains(homePage_monitor_Fragment)){
            fragments.remove(homePage_monitor_Fragment);
        }
        // 从FragmentManager中移除
        getSupportFragmentManager().beginTransaction().remove(homePage_monitor_Fragment).commit();

        // 重新创建
        homePage_monitor_Fragment=new HomePage_Monitor_Fragment();

        // 添加到fragmentList
        fragments.add(homePage_monitor_Fragment.newInstance("环境监控"));

        // 显示
        showFragment(homePage_monitor_Fragment,HomePage_Monitor_FRAGMENT_KEY);
    }


    /**
     * 显示fragment
     **/
    private void showFragment(Fragment fragment, String tag) {

        // 先判断fragment是否被添加过
        if (!fragment.isAdded()) {
            L.i(fragment+"没有添加过  添加");
            getSupportFragmentManager().beginTransaction().add(R.id.bottom_Navigation_Bar,fragment, tag).commit();
            fragments.add(fragment);
        }

        // 不可见
        if (!fragment.isVisible()) {
            for (Fragment frag : fragments) {
                if (frag != fragment) {
                    // 先隐藏其他fragment
                    L.i("隱藏" + frag);
                    getSupportFragmentManager().beginTransaction().hide(frag).commit();
                }
            }
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }


    }

}
