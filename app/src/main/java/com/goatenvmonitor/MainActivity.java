/**
 * 说明
 * 1.需要修改IP的地方：CitemAdapter.java  和 ControlActivity.java   172.20.10.12  10.0.2.2
 * 2.需要修改监测节点个数：TestStackAdapter.java写名字；HomePage_Monitor_Fragment.java写颜色
 * 3.需要修改控制面板：ControlActivity.java
 */


package com.goatenvmonitor;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private RelativeLayout mLaunchLayout;
    private Animation mFadeIn;
    private Animation mFadeInScale;
    private Animation mFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 设置为不带标题栏的格式
        setContentView(R.layout.activity_main);
        mLaunchLayout = (RelativeLayout) findViewById(R.id.launch);
        init();
        setListener();
    }

    private void init() {
        initAnim();
        mLaunchLayout.startAnimation(mFadeIn);
    }

    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.welcome_fade_in);
        mFadeIn.setDuration(500);
        mFadeInScale = AnimationUtils.loadAnimation(MainActivity.this,R.anim.welcome_fade_in_scale);
        mFadeInScale.setDuration(1000);
        mFadeOut = AnimationUtils.loadAnimation(MainActivity.this,R.anim.welcome_fade_out);
        mFadeOut.setDuration(500);
    }

    private void setListener() {
        /**
         * 动画切换原理:开始时是用第一个渐现动画,当第一个动画结束时开始第二个放大动画,当第二个动画结束时调用第三个渐隐动画,
         * 第三个动画结束时修改显示的内容并且重新调用第一个动画,从而达到循环效果
         */
        /**
         * 设置动画监听器
         */
        mFadeIn.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }

            /**
             * 开始动画
             * @param animation
             */
            public void onAnimationEnd(Animation animation) {
//                mLaunchLayout.startAnimation(mFadeInScale);  // 经历放大环节
                // 跳过放大环节，则延时后进行肚子里的处理
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                    //啥也不干
                    }
                };
                timer.schedule(timerTask,1000);
                mLaunchLayout.startAnimation(mFadeOut);//跳过放大环节
            }
        });
        /**
         * 放大环节
         */
        mFadeInScale.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
                mLaunchLayout.startAnimation(mFadeOut);
            }
        });
        /**
         * 退出环节
         */
        mFadeOut.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);//HomePage
                startActivity(intent);
                finish();
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
            }
        });
    }  // setListener()结束

}
