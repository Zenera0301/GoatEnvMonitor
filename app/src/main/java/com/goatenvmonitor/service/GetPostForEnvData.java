package com.goatenvmonitor.service;

import android.os.Message;
import android.util.Log;

import com.goatenvmonitor.DBOpenHelper;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;


public class GetPostForEnvData implements Callable<String> {
    private String goatHouseID;
    public EnvData envData;
    public GetPostForEnvData(String goatHouseID){
        this.goatHouseID=goatHouseID;
    }
    private static String PATH = "http://172.20.10.6:8888/myApps";
    static String temperature="",humidity="",AmmoniaConcentration="",airFlowRate="";//定义并初始化温度、湿度、氨气浓度、空气流速；
    // 当以Get方式登录的时候
    public void run(){

        StringBuilder sb =new StringBuilder(PATH);
        // 进行非空检查
        if(goatHouseID ==null){
            Log.i("main","输入的羊舍ID是空值，检查一下吧。");
        }
        // 当全都不为空的时候才进行下面的处理
        sb.append("?goatHouseID=").append(goatHouseID);
        try {
            URL url = new URL(sb.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode()!=200){
                Log.i("main","连接服务端失败");
            }
            // 如果能到这一步说明连接成功了，在进行下面的代码之前应该先添加服务器端用到的User.java和两个jar包
            ObjectMapper om = new ObjectMapper();
            envData = om.readValue(conn.getInputStream(),EnvData.class);//读取到envData并放入一个envData对象中
            Log.i("main","服务器"+envData.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String call() throws Exception {
        Log.i("main","服111务器"+envData.toString());
        return envData.toString();
    }
}


