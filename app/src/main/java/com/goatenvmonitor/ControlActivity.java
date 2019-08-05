package com.goatenvmonitor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.goatenvmonitor.fragment.HomePage_Monitor_Fragment;
import com.goatenvmonitor.service.EnvData;
import com.zcw.togglebutton.ToggleButton;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ControlActivity extends AppCompatActivity {
    private String goatHouseIDitemStr;
    private String goatHouseIDitem_toshowStr;
    private int goatHouseIDitem;
    private int goatHouseIDitem_toshow;
    private ToggleButton fanbtn,nozzlebtn,wetcurtainbtn,scraperboardbtn3,by4btn,by5btn,by6btn;
    private String temperature="",humidity="",AmmoniaConcentration="",airFlowRate="";  // 定义并初始化温度、湿度、氨气浓度、空气流速；
    private EnvData envData;
    private TextView temperaturetv,humiditytv,AmmoniaConcentrationtv,airFlowRatetv;
    private TextView goatHouseIDtv;


    private String mResponse;
    private String mResponse2;
    private String mResponse3 ;
    private String massage; //用于接收服务器返回的信息
    private static String PATH = "http://172.20.10.2:8888/myApps";  // 需要改成服务器地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        StatusBarUtil.setImmersiveStatusBar(this,true);

        // 获得羊舍号
        Intent intent = this.getIntent();  // 获取已有的intent对象
        Bundle bundle = intent.getExtras();  // 获取intent里面的bundle对象
        goatHouseIDitemStr = bundle.getString("goatHouseIDitemStr");  // 获取Bundle里面的字符串

        // 将监控节点加1
        goatHouseIDitem = Integer.valueOf(goatHouseIDitemStr).intValue();
        goatHouseIDitem_toshow = goatHouseIDitem + 1;
        goatHouseIDitemStr = String.valueOf(goatHouseIDitem);
        goatHouseIDitem_toshowStr = String.valueOf(goatHouseIDitem_toshow);

        // 环境参数每隔1s刷新一次
        handler.postDelayed(task, 1000);  // 第一次调用,延迟1秒执行task

        // 初始化环境参数显示tv
        temperaturetv = findViewById(R.id.temperature);
        humiditytv = findViewById(R.id.humidity);
        AmmoniaConcentrationtv = findViewById(R.id.AmmoniaConcentration);
        airFlowRatetv = findViewById(R.id.airFlowRate);

        // 初始化显示羊舍编号
        goatHouseIDtv = findViewById(R.id.goatHouseIDtv);
        goatHouseIDtv.setText("羊舍监测节点编号： "+goatHouseIDitem_toshowStr);

        // 滑动按钮
        fanbtn = findViewById(R.id.fanbtn);  // 风机控制按钮
        nozzlebtn=findViewById(R.id.nozzlebtn);  // 喷头
        wetcurtainbtn = findViewById(R.id.wetcurtainbtn); // 湿帘
        scraperboardbtn3=findViewById(R.id.scraperboardbtn3);  // 刮粪板
        by4btn=findViewById(R.id.by4btn);
        by5btn=findViewById(R.id.by5btn);
        by6btn=findViewById(R.id.by6btn);

        // 手动开关切换事件
        fanbtn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {

                if (String.valueOf(on) == "false")
                    new Thread() {
                        @Override
                        public void run() {

                            mResponse = GetPostUtil.sendPost(
                                    PATH, "zc@023830373038033041");  // 先关
                            Log.i("main", "手动关闭，没有问题");
                        }
                    }.start();

                if (String.valueOf(on) == "true")
                    new Thread() {
                        @Override
                        public void run() {
                            mResponse2 = GetPostUtil.sendPost(
                                    PATH, "zc@023730373038033039");  // 后开，下同
                            Log.i("main", "手动启动,没有问题");
                        }
                    }.start();
            }
        });

        // 备用1开关切换事件
        nozzlebtn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (String.valueOf(on) == "false") {
                    sendControlMessage("zc@023830303038033033");
                    Log.i("main", "by1关闭，没有问题");
                }
                else {
                    sendControlMessage("zc@023730303038033032");
                    Log.i("main", "by1启动,没有问题");
                }
            }
        });

        // 备用2开关切换事件
        wetcurtainbtn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (String.valueOf(on) == "false")
                    sendControlMessage("zc@023830313038033034");
                else
                    sendControlMessage("zc@023730313038033033");
                Toast.makeText(ControlActivity.this, "手自动切换", Toast.LENGTH_SHORT).show();
            }
        });

        // 设备3开关切换事件 是风机
        scraperboardbtn3.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (String.valueOf(on) == "false")
                    sendControlMessage("zc@023830323038033035");
                else
                    sendControlMessage("zc@023730323038033034");
                Toast.makeText(ControlActivity.this, "手自动切换", Toast.LENGTH_SHORT).show();
            }
        });

        //备用4开关切换事件
        by4btn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (String.valueOf(on) == "false")
                    sendControlMessage("zc@023830333038033036");
                else
                    sendControlMessage("zc@023730333038033035");
                Toast.makeText(ControlActivity.this, "手自动切换", Toast.LENGTH_SHORT).show();
            }
        });

        //备用5开关切换事件
        by5btn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (String.valueOf(on) == "false")
                    sendControlMessage("zc@023830343038033037");
                else
                    sendControlMessage("zc@023730343038033036");
                Toast.makeText(ControlActivity.this, "手自动切换", Toast.LENGTH_SHORT).show();
            }
        });

        // 备用6开关切换事件
        by6btn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
            @Override
            public void onToggle(boolean on) {
                if (String.valueOf(on) == "false")
                    sendControlMessage("zc@023830353038033038");
                else
                    sendControlMessage("zc@023730353038033037");
                Toast.makeText(ControlActivity.this, "手自动切换", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 定时发出执行命令
     */
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            /**
             * TODO 此处执行任务 此处即使界面返回也会一直后台运行
             * */
            Log.i("main", "控制界面，向服务器发送请求：" + goatHouseIDitemStr);
            getMethodServer(goatHouseIDitemStr);//指定羊舍ID，从服务器获取其环境信息
            handler.postDelayed(this, 5 * 1000);//延迟5秒,再次执行task本身,实现了循环的效果
        }
    };

    /**
     * 获取服务器返回的信息
     */
    public String getMethodServer(final String goatHouseID){
        new Thread() {
            @Override
            public void run() {
                mResponse3 = GetPostUtil.sendPost(
                        PATH, "zcdata@"+goatHouseID);
                // Log.i("main", mResponse3+"已经请求，没有问题");
                //handler
                if (mResponse3 != null && mResponse3 != "") {
                    Message msg = handler.obtainMessage();
                    msg.what = 0x66;  // 0x66表示成功
                    msg.obj = mResponse3;
                    handler.sendMessage(msg);
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = 0x44;  // 失败
                    msg.obj = "控制界面，服务器返回的数据是空字符串，可能没连上服务器";
                    handler.sendMessage(msg);
                }
            }
        }.start();
        return mResponse3;
    }


    /**
     * 接收、分析、执行模块
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg)
        {
            if (msg.what == 0x66)
            {
                massage = (String) msg.obj;
                if (massage!=null && mResponse3 != ""){
                    Log.i("main","控制界面，定时更新参数："+massage);

                    //给各个环境参数赋值
                    String[] str = massage.split("@");
                    temperature = str[1];
                    humidity = str[3];
                    AmmoniaConcentration =str[5];
                    airFlowRate = str[7];

                    //将得到的值显示在面板上
                    temperaturetv.setText("温   度： "+temperature+" ℃");
                    humiditytv.setText("湿   度： "+humidity+" %");
                    AmmoniaConcentrationtv.setText("氨    气：  "+AmmoniaConcentration+"  mg/m3");
                    airFlowRatetv.setText("硫 化 氢： "+airFlowRate+" mg/m3");
                    temperature="";humidity="";AmmoniaConcentration="";airFlowRate="";
                    massage = null;
                }else{
                    Log.i("main","控制 服务器返回的数据是空字符串，可能没连上服务器");
                }
            }
            else if (msg.what == 0x44)
            {
                String string = (String) msg.obj;
                Log.i("main",string) ;
            }
            else if (msg.what == 0x11)
            {
                Log.i("main","控制界面不继续访问服务器了") ;
            }
            else
            {
            }
        }
    };


    /**
     * 发送控制命令
     */
    public void sendControlMessage(final String plcCode){
        new Thread(){
            public void run(){

                // 进行非空检查
                if(plcCode == null){
                    Log.i("main","控制  输入的信息包含空值，检查一下吧。");
                    return;
                }

                // 当全都不为空的时候才进行下面的处理
                String controlMSG = plcCode;
                try{
                    byte[] data = controlMSG.getBytes("utf-8");
                    URL url = new URL(PATH);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    OutputStream out = conn.getOutputStream();
                    out.write(data);
                    out.flush();
                    if (conn.getResponseCode()!=200){
                        Log.i("main","连接服务端失败");
                        return;
                    }
                    Log.i("main","连接服务端成功");
                    ObjectMapper om = new ObjectMapper();
                    String msg = om.readValue(conn.getInputStream(),String.class);
                    Log.i("main",msg);
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Message msg = handler.obtainMessage();
        msg.what = 0x11;  // 0x11表示停止连续更新
    }


    @Override
    protected void onStop() {
        super.onStop();
        Message msg = handler.obtainMessage();
        msg.what = 0x11;  // 0x11表示停止连续更新
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Message msg = handler.obtainMessage();
        msg.what = 0x11;  // 0x11表示停止连续更新
    }
}


