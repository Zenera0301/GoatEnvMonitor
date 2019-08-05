package com.goatenvmonitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goatenvmonitor.R;
import com.goatenvmonitor.WebviewActivity;

import java.util.List;

public class HKitemAdapter extends RecyclerView.Adapter<HKitemAdapter.ViewHolder> {

	private Context mContext;

	private List<HKitem> mHkitemList;

	static class ViewHolder extends RecyclerView.ViewHolder{
		CardView cardView;
		ImageView imageView;
		TextView tv_title,tv_content,tv_date;

		public ViewHolder(View itemView){
			super(itemView);
			cardView = (CardView)itemView;
			imageView = (ImageView)itemView.findViewById(R.id.iv_a_preview);
			tv_content = (TextView)itemView.findViewById(R.id.tv_a_content);
			tv_title = (TextView)itemView.findViewById(R.id.tv_a_title);
			tv_date = (TextView)itemView.findViewById(R.id.tv_a_date);
		}
	}

	public HKitemAdapter(List<HKitem> hkList) {
		mHkitemList = hkList;
	}

	@NonNull
	@Override
	public HKitemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mContext == null){
			mContext = parent.getContext();
		}
		View view = LayoutInflater.from(mContext).inflate(R.layout.a_item,parent,false);
		final ViewHolder holder = new ViewHolder(view);
		holder.cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = holder.getAdapterPosition();
				HKitem hkitem = mHkitemList.get(position);
				Log.d("onClick","我点击了一个cardview");
				switch (position){
					case 0:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","http://www.yangbbs.com/");  // 手机访问版本，养羊论坛
						break;
					case 1:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","https://tieba.baidu.com/f?kw=%D1%F8%D1%F2");  // 手机访问版本，养羊吧
						break;
					case 2:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","http://readmodel.m.sogou.com/read?url=http%3A%2F%2Fm.1nongjing.com%2Fa%2F201811%2F239934.html&type=5PRuAPXTonw=&pattern=C5vkYQ2RTF6RTTNx1Kk2DK+yE39u09FtD3ZEphlFPDRN0wd1g3Lgv1EFGQ/bHA9btIRFa+1KM7o=&securl=bSmfnZ5VfGA9W0nSfUK%2Bz3dDMxYaDOI3f%2B8IXzidS8EVLRQE68tjhBHJY85VugjA&sec=XYiZmDPJqSNMRmldoMODvg%3D%3D");  // 手机访问版本，"养羊的成本与利润"
						break;
					case 3:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","http://m.chinafarming.com/farmHome.htm");  // 手机访问版本，中国畜牧网
						break;
					case 4:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","http://m.1nongjing.com/");  // 手机访问版本，第一农经网
						break;
					case 5:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","https://tieba.baidu.com/f?kw=%C9%BD%D1%F2");  // "手机访问版本，山羊吧"
						break;
					case 6:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","https://m.cnhnb.com/xt/article-55176.html");  // "手机访问版本，羊腹泻诊治要点"
						break;
					case 7:
						intentBundle(mContext,WebviewActivity.class,"URLMessage","https://wenwen.sogou.com/z/q225901420.htm");  // "手机访问版本，山羊养殖技术普及"
						break;
				}
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull HKitemAdapter.ViewHolder viewHolder, int position) {
		HKitem hkitem = mHkitemList.get(position);
		viewHolder.tv_date.setText(hkitem.getDate());
		viewHolder.tv_content.setText(hkitem.getContent());
		viewHolder.tv_title.setText(hkitem.getTitle());
		Glide.with(mContext).load(hkitem.getImgID()).into(viewHolder.imageView);
	}

	@Override
	public int getItemCount() {
		return mHkitemList.size();
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
