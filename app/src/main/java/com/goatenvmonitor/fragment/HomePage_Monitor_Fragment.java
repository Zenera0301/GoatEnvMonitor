package com.goatenvmonitor.fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.utils.Utils;
import com.goatenvmonitor.DBOpenHelper;
import com.goatenvmonitor.HomePage;
import com.goatenvmonitor.R;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goatenvmonitor.StatusBarUtil;
import com.goatenvmonitor.adapter.CitemAdapter;
import com.goatenvmonitor.adapter.HKitem;
import com.goatenvmonitor.adapter.TestStackAdapter;
import com.goatenvmonitor.service.EnvData;
import com.goatenvmonitor.service.GetPostForEnvData;
import com.loopeer.cardstack.CardStackView;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomePage_Monitor_Fragment extends Fragment  implements CardStackView.ItemExpendListener{

    private static CardStackView cardStackView;
    private TestStackAdapter testStackAdapter;
    private ScrollView scrollView;

    /**
     *TestStackAdapter中设定n个序号，以及此处设定n个颜色就显示n个卡片
     */
    private static Integer[] item = new Integer[]{
            R.color.red,
            R.color.orange,
            R.color.yellow,
            R.color.green,
            R.color.cyanblue,
            R.color.blue,
            R.color.purple,
            R.color.cyanblue,
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);  // 初始化布局页面
    }
    /**
     * 初始化布局页面
     * @param view
     */
    private void init(View view) {
        scrollView = view.findViewById(R.id.scrollview);
        cardStackView = view.findViewById(R.id.csk_c);
        testStackAdapter = new TestStackAdapter(getActivity());
        cardStackView.setAdapter(testStackAdapter);
        cardStackView.setItemExpendListener(this);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        testStackAdapter.updateData(Arrays.asList(item));
                    }
                }
                , 200
        );
    }

    /**
     * 当卡片被点击时
     * @param expend
     */
    @SuppressLint("HandlerLeak")
    @Override
    public void onItemExpend(boolean expend) {
        // 获取卡片ID
        int cardPosion = getCardPosition();
        Log.i("main","卡片被点击了,卡片ID："+String.valueOf(cardPosion));
        if (cardPosion == -1){  // 说明卡片被关闭了 onResume()
            // 设置默认滚动到顶部
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }
    }

    public HomePage_Monitor_Fragment() {
        // Required empty public constructor
    }

    public static HomePage_Monitor_Fragment newInstance(String title) {
        HomePage_Monitor_Fragment fragment = new HomePage_Monitor_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_homepage_monitor, container, false);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获得卡片的标号，根据标号进行操作
     * @return
     */
    public static int getCardPosition(){
        int cardPosion = cardStackView.getSelectPosition();
        return cardPosion;
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
    }

}

