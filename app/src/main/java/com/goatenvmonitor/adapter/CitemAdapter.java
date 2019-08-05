package com.goatenvmonitor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goatenvmonitor.ControlActivity;
import com.goatenvmonitor.GetPostUtil;
import com.goatenvmonitor.HomePage;
import com.goatenvmonitor.MainActivity;
import com.goatenvmonitor.R;
import com.goatenvmonitor.WebviewActivity;
import com.goatenvmonitor.fragment.HomePage_Monitor_Fragment;
import com.goatenvmonitor.service.EnvData;
import com.goatenvmonitor.service.GetPostForEnvData;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import static java.lang.Thread.sleep;

public class CitemAdapter extends RecyclerView.Adapter<CitemAdapter.ViewHolder>{

	private Context mContext;
	private List<HKitem> mCitemList;
	private String temperature="",humidity="",AmmoniaConcentration="",airFlowRate="";  // 定义并初始化温度、湿度、氨气浓度、空气流速；
	private String mResponse3 ;
	private String massage;  // 用于接收服务器返回的信息
    private static String PATH = "http://172.20.10.2:8888/myApps";  // 需要改成服务器地址
    private int goatHouseID;

	class ViewHolder extends RecyclerView.ViewHolder{
		CardView cardView;  // 用来获取每个卡片的布局
		TextView tv_key;
		TextView tv_value;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			cardView = (CardView)itemView;
			tv_key = (TextView) itemView.findViewById(R.id.tv_c_key);
			tv_value = (TextView)itemView.findViewById(R.id.tv_c_value);
			//TODO 参数每隔一段时间变化 成功
			handler.postDelayed(task, 1000);  // 第一次调用,延迟1秒执行task
		}
	}


	/**
	 * 定时发出执行命令
	 */
	private Runnable task = new Runnable() {
		@Override
		public void run() {
			goatHouseID = HomePage_Monitor_Fragment.getCardPosition();  // 通过HomePage_Monitor_Fragment中编写的函数获得卡片ID，即羊舍ID
			Log.i("main","定时更新参数，命令:"+String.valueOf(goatHouseID));
			getMethodServer(String.valueOf(goatHouseID));  // 指定羊舍ID，从服务器获取其环境信息
			handler.postDelayed(this, 10 * 1000);  // 延迟10秒,再次执行task本身,实现了循环的效果
		}
	};


	/**
	 * 从数据库获取羊舍环境信息
	 */
	public void getMethodServer(final String ID){
		new Thread() {
			@Override
			public void run() {
				mResponse3 = GetPostUtil.sendPost(
						PATH, "zcdata@"+ID);
				Log.i("main", mResponse3+"已经请求，没有问题");

				// handler
				if (mResponse3 != null && mResponse3 != "") {
					Message msg = handler.obtainMessage();
					msg.what = 0x66;  // 成功
					msg.obj = mResponse3;
					handler.sendMessage(msg);
				} else {
					Message msg = handler.obtainMessage();
					msg.what = 0x44;  // 失败
					msg.obj = "服务器返回的数据是空字符串，可能没连上服务器";
					handler.sendMessage(msg);
				}
			}
		}.start();
	}


	/**
	 * 接收、分析、执行模块
	 */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (msg.what == 0x66)
			{
				massage = (String) msg.obj;
				if (massage!=null && massage != ""){
					Log.i("main","定时更新参数成功，结果："+ massage);
					// 给各个环境参数赋值
					String[] str = massage.split("@");
					temperature = str[1];
					humidity = str[3];
					AmmoniaConcentration =str[5];
					airFlowRate = str[7];
					notifyItemChanged(0,temperature+" ℃");
					notifyItemChanged(1,humidity+" %");
					notifyItemChanged(2,AmmoniaConcentration+" mg/m3");
					notifyItemChanged(3,airFlowRate+" mg/m3");
					temperature="";humidity="";AmmoniaConcentration="";airFlowRate="";
					massage = null;
				}else{
					Log.i("main","定时更新参数:  massage=null");
				}
			}
			else if (msg.what == 0x44)
			{
				String string = (String) msg.obj;
				Log.i("main",string);
			}
			else
			{
			}
		}
	};


	public CitemAdapter(List<HKitem> hkList) {
		mCitemList = hkList;
	}


	@NonNull
	@Override
	public CitemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mContext == null){
			mContext = parent.getContext();
		}
		View view = LayoutInflater.from(mContext).inflate(R.layout.c_item,parent,false);
		final CitemAdapter.ViewHolder holder = new CitemAdapter.ViewHolder(view);
		holder.tv_key.setOnClickListener(new View.OnClickListener() { //点击文字可以进入控制界面
			@Override
			public void onClick(View v) {
				int position = holder.getAdapterPosition();
				String positionStr= String.valueOf(position);
                int goatHouseIDitem = HomePage_Monitor_Fragment.getCardPosition();//通过HomePage_Monitor_Fragment中编写的函数获得卡片ID，即羊舍ID
                String goatHouseIDitemStr= String.valueOf(goatHouseIDitem);
				Log.i("main","在Card"+goatHouseIDitemStr+"中，我点击了一个cardview标号是："+positionStr);
				// 从这个位置跳转ControlActivity
                Intent intent = new Intent(mContext, ControlActivity.class);
                Bundle bundle = new Bundle();  // 创建Bundle对象
                bundle.putString("goatHouseIDitemStr", goatHouseIDitemStr);  // 装入数据
                intent.putExtras(bundle);  // 把Bundle塞入Intent里面
                mContext.startActivity(intent);
			}
		});
		holder.tv_value.setOnClickListener(new View.OnClickListener() { //点击数据可以进入控制界面
			@Override
			public void onClick(View v) {
				int position = holder.getAdapterPosition();
				String positionStr= String.valueOf(position);
				int goatHouseIDitem = HomePage_Monitor_Fragment.getCardPosition();  // 通过HomePage_Monitor_Fragment中编写的函数获得卡片ID，即羊舍ID
                String goatHouseIDitemStr= String.valueOf(goatHouseIDitem);
				Log.i("main","在Card"+goatHouseIDitemStr+"中，我点击了一个cardview标号是："+positionStr);
				// 从这个位置跳转ControlActivity
				Intent intent = new Intent(mContext, ControlActivity.class);
				Bundle bundle = new Bundle();  // 创建Bundle对象
				bundle.putString("goatHouseIDitemStr", goatHouseIDitemStr);  // 装入数据
				intent.putExtras(bundle);  // 把Bundle塞入Intent里面
				mContext.startActivity(intent);
			}
		});
		return holder;
	}


	@Override
	public void onBindViewHolder(@NonNull CitemAdapter.ViewHolder viewHolder, int position) {
		HKitem hkitem = mCitemList.get(position);
		viewHolder.tv_key.setText(hkitem.getKey());
		viewHolder.tv_value.setText(hkitem.getValue());
	}


	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
		super.onBindViewHolder(holder, position, payloads);
		if (payloads.isEmpty()) {
			// payloads为空，说明是更新整个ViewHolder
			onBindViewHolder(holder, position);
		} else {
			HKitem hkitem = mCitemList.get(position);
			// 将需要刷新的状态保存到 Item 对应的成员变量中
			String str= (String) payloads.get(0);
			holder.tv_value.setText(str);
		}
	}


	@Override
	public int getItemCount() {
		return mCitemList.size();
	}
}
