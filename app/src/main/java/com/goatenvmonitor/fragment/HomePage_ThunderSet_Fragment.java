package com.goatenvmonitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.goatenvmonitor.GetPostUtil;
import com.goatenvmonitor.LoginActivity;
import com.goatenvmonitor.R;
import com.goatenvmonitor.fragmentmine.AboutProjectActivity;
import com.goatenvmonitor.fragmentmine.ContactUSActivity;
import com.goatenvmonitor.service.EnvData;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomePage_ThunderSet_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage_ThunderSet_Fragment extends Fragment {

    private Button thundersubmit;
    private EditText temperaturelowset,temperaturehighset,humiditylowset,humidityhighset;

    private static String PATH = "http://172.20.10.12:8888/myApps";  // 修改为服务器地址
    private String mResponse3 ;
    private String controlMSG;
    public HomePage_ThunderSet_Fragment() {
    }

    public static HomePage_ThunderSet_Fragment newInstance(String title) {
        HomePage_ThunderSet_Fragment fragment = new HomePage_ThunderSet_Fragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage_thunderset, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        thundersubmit = view.findViewById(R.id.thundersubmit);
        temperaturelowset = view.findViewById(R.id.temperaturelowset);
        temperaturehighset = view.findViewById(R.id.temperaturehighset);
        humiditylowset = view.findViewById(R.id.humiditylowset);
        humidityhighset = view.findViewById(R.id.humidityhighset);

        // 填好内容后将4个信息提交（温度最低最高值、湿度最低最高值）
        thundersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //提交按钮点击后,判断4个数据是否非空，非空则把5个数据传递给中间件
                Integer ifinvalid = 0;
                //判断输入的温湿度阈值是否为空
                String temperaturelowvalue=temperaturelowset.getText().toString().trim();
                String temperaturehighvalue=temperaturehighset.getText().toString().trim();
                String humiditylowvalue=humiditylowset.getText().toString().trim();
                String humidityhighvalue=humidityhighset.getText().toString().trim();
                String showInf = "";
                if(temperaturelowvalue.length()==0){
//                    Toast.makeText(getActivity(), "温度下限已设置为10℃", Toast.LENGTH_SHORT).show();
                    showInf += "温度下限已设置为10℃ ";
                    temperaturelowvalue = "10";
//                    ifinvalid =1;
                }
                if(temperaturehighvalue.length()==0){
//                    Toast.makeText(getActivity(), "温度上限已设置为30℃", Toast.LENGTH_SHORT).show();
                    showInf += "温度上限已设置为30℃";
                    temperaturehighvalue = "30";
//                    ifinvalid =1;
                }
                if(humiditylowvalue.length()==0){
//                    Toast.makeText(getActivity(), "湿度下限已设置为40%", Toast.LENGTH_SHORT).show();
                    showInf += " 湿度下限已设置为40% ";
                    humiditylowvalue = "40";
//                    ifinvalid =1;
                }
                if(humidityhighvalue.length()==0){
//                    Toast.makeText(getActivity(), "湿度上限已设置为80%", Toast.LENGTH_SHORT).show();
                    showInf += " 湿度下限已设置为80% ";
                    humidityhighvalue = "80";
//                    ifinvalid =1;
                }
                Toast.makeText(getActivity(), showInf, Toast.LENGTH_SHORT).show();
                //把数值发给中间件
                sendThunder2Server(temperaturelowvalue,temperaturehighvalue,humiditylowvalue,humidityhighvalue);
            }
        });
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

    /**
     * 将4条控制命令发送到服务端
     */
    public void sendThunder2Server(final String temlow, final String temhigh, final String humlow, final String humhigh ) {
        new Thread() {
            public void run() {
                // 进行非空检查
                if (temhigh == null || temlow == null || humhigh == null || humlow == null) {
                    Log.i("main", "输入的信息包含空值，检查一下吧。");
                    return;
                }
                // 当全都不为空的时候才进行下面的处理
                controlMSG = "temlow@" + temlow + "@temhigh@" + temhigh + "@humlow@" + humlow + "@humhigh@" + humhigh;
                mResponse3 = GetPostUtil.sendPost(
                        PATH, "dj@" + controlMSG);
                Log.i("main", mResponse3 + "阈值设定，已经发送，没有问题");
            }
        }.start();
    }
}
