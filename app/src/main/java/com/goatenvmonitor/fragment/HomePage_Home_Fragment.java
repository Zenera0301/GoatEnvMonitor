package com.goatenvmonitor.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.goatenvmonitor.GuideActivity;
import com.goatenvmonitor.R;
import com.goatenvmonitor.TeamActivity;
import com.goatenvmonitor.WebviewActivity;
import com.goatenvmonitor.adapter.HKitem;
import com.goatenvmonitor.adapter.HKitemAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomePage_Home_Fragment extends Fragment implements View.OnClickListener{
    private ViewFlipper viewFlipper;
    private List<View> mDotList;
    private final static int NUM = 4;
    private static final int AUTO = 0x01;
    private static final int PREVIOUS = 0x02;
    private static final int NEXT = 0x03;
    private float x,y;
    private int w,h;
    private RecyclerView recyclerView;
    private HKitemAdapter adapter;
    private List<HKitem> hkitemList = new ArrayList<>();
    private TextView tv_41,tv_42,tv_43,tv_44;
    private ImageView iv_41,iv_42,iv_43,iv_44;

    public HomePage_Home_Fragment() {
        // Required empty public constructor
    }

    public static HomePage_Home_Fragment newInstance(String title) {
        HomePage_Home_Fragment fragment = new HomePage_Home_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     * 每隔一段时间切换图片
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUTO:
                    showNext();
                    sendMsg();
                    break;
                case PREVIOUS:
                    mHandler.removeMessages(AUTO);
                    showPre();
                    sendMsg();
                    break;
                case NEXT:
                    mHandler.removeMessages(AUTO);
                    showNext();
                    sendMsg();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homepage_home, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        w = size.x;
        h = size.y;
        init(view);
        return view;//return的是view！！！
    }
    private void init(View view) {
        tv_41 = (TextView)view.findViewById(R.id.tv_btn41);
        tv_42 = (TextView)view.findViewById(R.id.tv_btn42);
        tv_43 = (TextView)view.findViewById(R.id.tv_btn43);
        tv_44 = (TextView)view.findViewById(R.id.tv_btn44);
        iv_41 = (ImageView)view.findViewById(R.id.iv_btn41);
        iv_42 = (ImageView)view.findViewById(R.id.iv_btn42);
        iv_43 = (ImageView)view.findViewById(R.id.iv_btn43);
        iv_44 = (ImageView)view.findViewById(R.id.iv_btn44);
        tv_41.setOnClickListener(this);
        tv_42.setOnClickListener(this);
        tv_43.setOnClickListener(this);
        tv_44.setOnClickListener(this);
        iv_41.setOnClickListener(this);
        iv_42.setOnClickListener(this);
        iv_43.setOnClickListener(this);
        iv_44.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems();
        viewFlipper = (ViewFlipper)view.findViewById(R.id.vf_a);//图片切换窗口
        //recyclerview的初始化
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_a);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new HKitemAdapter(hkitemList);
        recyclerView.setAdapter(adapter);
        //圆点的初始化
        mDotList = new ArrayList<>();
        mDotList.add(view.findViewById(R.id.dot1));
        mDotList.add(view.findViewById(R.id.dot2));
        mDotList.add(view.findViewById(R.id.dot3));
        mDotList.add(view.findViewById(R.id.dot4));
        setEvent();
        sendMsg();
    }


    private void initItems() {
        hkitemList.add(new HKitem("养羊论坛",
                "养羊,最新养羊肉羊养羊技术肉羊行情肉羊价格羊肉价格中国养殖网养羊频道。                              ",
                "2019-06-22",
                R.drawable.logoyang0));
        hkitemList.add(new HKitem("养羊吧",
                "随着肉羊价格的逐步走高,多胎多产的小尾寒羊又重新进入部分养羊人的选择养殖品种,但对纯种小尾寒羊的养殖方法有部分养羊人掌握的还不到位,影响了养殖效益。",
                "2019-01-22",
                R.drawable.logoyang1));
        hkitemList.add(new HKitem("养羊的成本与利润",
                "羊是中国最常见的家畜之一，养羊是我们最常见的养殖项目。这几年来，随着社会的发展，羊肉的市场需求量在不算扩大，因此也带动了养羊行业的不断发展。",
                "2018-11-19",
                R.drawable.logoyang2));
        hkitemList.add(new HKitem("中国畜牧网",
                "兽医兽药第一资讯，畜牧机械推广。面向畜牧、兽医、饲料、奶业、草原等政府机构、行业组织、农牧企业，以及本行业其他从业人员。",
                "2019-4-12",
                R.drawable.logoyang3));
        hkitemList.add(new HKitem("第一农经网",
                "关注农业经济,让农业经济大放异彩，成为我国经济中的一个强有力的抓手。                                                                           ",
                "2019-6-4",
                R.drawable.logoyang4));
        hkitemList.add(new HKitem("山羊吧",
                "讨论山羊的相关知识，相关养殖方法！在这里你可以找到组织，进行深入了解和学习。                                                ",
                "2019-5-23",
                R.drawable.logoyang5));
        hkitemList.add(new HKitem("羊腹泻诊治要点",
                "很多农村都有羊养殖，在养羊过程中养殖户碰到的一个极为常见的问题就是羊腹泻，而绝大多数养殖户对于羊腹泻了解非常少。",
                "2019-1-22",
                R.drawable.logoyang6));
        hkitemList.add(new HKitem("山羊养殖技术普及",
                "山羊养殖过程中的羊舍、饲喂等管理技巧分享。羊场场址选择原则，羊场场址选择要求，羊场的基本设施，羊舍建筑要求。                                                                                   ",
                "2019-6-25",
                R.drawable.logoyang7));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 取消异步任务
    }


    /**
     * 发送内容 并设置延迟切换图片的时间
     */
    private void sendMsg(){
        Message mags = new Message();
        mags.what = AUTO;
        mHandler.sendMessageDelayed(mags,2000);
    }

    // 设置事件 手动翻看图片
    @SuppressLint("ClickableViewAccessibility")
    private void setEvent(){
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        y = event.getX();
                        if (y > x){
                            mHandler.sendEmptyMessage(PREVIOUS);
                        }else if (x == y){

                        }else {
                            mHandler.sendEmptyMessage(NEXT);
                        }
                        break;
                }
                return true;
            }
        });
    }


    /**
     * 显示下一张图
     */
    private void showNext(){
        viewFlipper.showNext();
        int current = viewFlipper.getDisplayedChild();
        if (current == 0){
            mDotList.get(NUM-1).setBackgroundResource(R.drawable.dot_normal);
        }else {
            mDotList.get(current-1).setBackgroundResource(R.drawable.dot_normal);
        }
        mDotList.get(current).setBackgroundResource(R.drawable.dot_focused);
    }


    /**
     * 显示前一张图
     */
    private void showPre(){
        viewFlipper.showPrevious();
        int current = viewFlipper.getDisplayedChild();
        if (current == NUM-1){
            mDotList.get(0).setBackgroundResource(R.drawable.dot_normal);
        }else {
            mDotList.get(current+1).setBackgroundResource(R.drawable.dot_normal);
        }
        mDotList.get(current).setBackgroundResource(R.drawable.dot_focused);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_btn41:  // 软件功能imageview
                Intent intent = new Intent(getContext(),GuideActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.iv_btn42:  // 团队介绍imageview
                Intent intent2 = new Intent(getContext(),TeamActivity.class);
                getContext().startActivity(intent2);
                break;
            case R.id.iv_btn43:  // 知识天地imageview
                intentBundle(getContext(),WebviewActivity.class,"URLMessage","http://www.yangbbs.com/");//养羊论坛
                break;
            case R.id.iv_btn44:  // 羊友问答imageview
                intentBundle(getContext(),WebviewActivity.class,"URLMessage","http://www.myr88.com/");//牧羊人
                break;
            case R.id.tv_btn41:  // 功能简介textview
                Intent intent1 = new Intent(getContext(),GuideActivity.class);
                getContext().startActivity(intent1);
                break;
            case R.id.tv_btn42:  // 团队介绍textview
                Intent intent3 = new Intent(getContext(),TeamActivity.class);
                getContext().startActivity(intent3);
                break;
            case R.id.tv_btn43:  // 知识天地textview
                intentBundle(getContext(),WebviewActivity.class,"URLMessage","http://www.chinabreed.com/sheep/");  // 中国养羊网
                break;
            case R.id.tv_btn44:  // 羊友问答textview
                intentBundle(getContext(),WebviewActivity.class,"URLMessage","http://www.myr88.com/");  // 牧羊人
                break;
        }
    }


    /**
     * 对跳转到ActivityWebView，并携带指定网址的这个intent进行了封装
     * @param context 当前的上下文
     * @param mclass  要跳到的地方
     * @param key   关键字
     * @param urlMessage 指定的网址，要求ActivityWebView显示
     */
    public void intentBundle(Context context,Class mclass,String key,String urlMessage){
        Intent intent = new Intent(context,mclass);
        Bundle bundle = new Bundle();  // 创建Bundle对象
        bundle.putString(key, urlMessage);  // 装入数据
        intent.putExtras(bundle);  // 把Bundle塞入Intent里面
        context.startActivity(intent);
    }
}
