package com.goatenvmonitor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goatenvmonitor.R;
import com.goatenvmonitor.fragment.HomePage_Home_Fragment;
import com.goatenvmonitor.fragment.HomePage_Monitor_Fragment;
import com.goatenvmonitor.service.EnvData;
import com.goatenvmonitor.service.GetPostForEnvData;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.goatenvmonitor.fragment.HomePage_Monitor_Fragment.getCardPosition;

public class TestStackAdapter extends StackAdapter<Integer> {

	public static Map<String,String> map = new HashMap<String, String>();
	public static RecyclerView recyclerView;
	public static CitemAdapter adapter;
	public static List<HKitem> citemList0 = new ArrayList<>();


	/**
	 * 这个方法是用来创建每一项的的View的，也就是每一个卡片的view，返回一个ViewHolder，
	 * 这个ViewHolder中保存着这个布局中的View,这个ViewHolder也需要我们自定义
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
		View view;
		switch (viewType) {
			case R.layout.list_card_item_larger_header:
				view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
				return new ColorItemLargeHeaderViewHolder(view);
			case R.layout.list_card_item_with_no_header:
				view = getLayoutInflater().inflate(R.layout.list_card_item_with_no_header, parent, false);
				return new ColorItemWithNoHeaderViewHolder(view);
			default:
				view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
				return new ColorItemViewHolder(view);
		}
	}


	/**
	 * viewType这个参数是由方法int getItemViewType(int postion)方法得到的
	 * 传入的参数position代表是第几项，重写该方法，根据position判断类型。
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType(int position) {
		if (position == 90) {
			return R.layout.list_card_item_larger_header;
		} else if (position == 100) {
			return R.layout.list_card_item_with_no_header;
		} else {
            return R.layout.list_card_item;
        }
	}


	/**
	 * 这个方法是在我们创建完VIew之后调用的，得到View后进行一些操作，比如显示数据等操作。
	 * @param data
	 * @param position
	 * @param holder
	 */
	@Override
	public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
		if (holder instanceof ColorItemLargeHeaderViewHolder) {
			ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
			h.onBind(data, position);
		}
		if (holder instanceof ColorItemWithNoHeaderViewHolder) {
			ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
			h.onBind(data, position);
		}
		if (holder instanceof ColorItemViewHolder) {
			ColorItemViewHolder h = (ColorItemViewHolder) holder;
			h.onBind(data, position);
		}
	}


	static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
		View mLayout;
		TextView mTextTitle;

		public ColorItemWithNoHeaderViewHolder(View view) {
			super(view);
			mLayout = view.findViewById(R.id.frame_list_card_item);
			mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
		}

		@Override
		public void onItemExpand(boolean b) {
		}

		public void onBind(Integer data, int position) {
			mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
			mTextTitle.setText(map.get(String.valueOf(position)));
		}

	}

	static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
		View mLayout;
		View mContainerContent;
		TextView mTextTitle;

		public ColorItemLargeHeaderViewHolder(View view) {
			super(view);
			mLayout = view.findViewById(R.id.frame_list_card_item);
			mContainerContent = view.findViewById(R.id.container_list_content);
			mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
		}

		@Override
		public void onItemExpand(boolean b) {
			mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
		}

		@Override
		protected void onAnimationStateChange(int state, boolean willBeSelect) {
			super.onAnimationStateChange(state, willBeSelect);
			if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
				onItemExpand(true);
			}
			if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
				onItemExpand(false);
			}
		}

		public void onBind(Integer data, int position) {
			mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
			mTextTitle.setText(map.get(String.valueOf(position)));

			itemView.findViewById(R.id.rv_c).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
				}
			});
		}

	}


	/**
	 * 正常头，只用到了正常头
	 */
	@SuppressLint("HandlerLeak")
	class ColorItemViewHolder extends CardStackView.ViewHolder {
		View mLayout;
		View mContainerContent;
		TextView mTextTitle;
		public ColorItemViewHolder(View view) {
			super(view);
			mLayout = view.findViewById(R.id.frame_list_card_item);
			mContainerContent = view.findViewById(R.id.container_list_content);
			mTextTitle = view.findViewById(R.id.text_list_card_title);
			recyclerView = view.findViewById(R.id.rv_c);  // 获取容器实例
			LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());  // 实例化一个当前的布局
			recyclerView.setLayoutManager(layoutManager);  // 将布局设置给容器
			recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));  // 布局的格式是纵向布局
			// 初始化citemList
			citemList0.clear();
			citemList0.add(new HKitem("温度："," ℃"));
			citemList0.add(new HKitem("湿度："," %"));
			citemList0.add(new HKitem("氨气："," mg/m3"));
			citemList0.add(new HKitem("硫化氢："," mg/m3"));
			adapter = new CitemAdapter(citemList0);  // 实例化一个recyclerview的适配器
			recyclerView.setAdapter(adapter);  // 将适配器设置给容器
		}


		/**
		 * 该方法是在，其他Item被展开时，自动隐藏和显示的。
		 * @param b
		 */
		@Override
		public void onItemExpand(boolean b) {
			mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
		}


		/**
		 * 该方法是在bindView 调用时被调用的，因为可能有不同的布局，因而有不同的ViewHolder，
		 * 将bindView实现的操作放在了ViewHolder中的onBind方法中，会使代码看来起更简洁，易懂。
		 * @param data
		 * @param position
		 */
		public void onBind(Integer data, int position) {
			mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);//传入的int值，其实是一个颜色，在这里改变头部的颜色
			mTextTitle.setText(map.get(String.valueOf(position)));
		}
	}


	/**
	 * 设定几个序号，以及相同个数的颜色就显示几个卡片
	 * @param context
	 */
	public TestStackAdapter(Context context) {
		super(context);
		map.put("0","一号监测节点");
		map.put("1","二号监测节点");
		map.put("2","三号监测节点");
		map.put("3","四号监测节点");
		map.put("4","五号监测节点");
		map.put("5","六号监测节点");
		map.put("6","七号监测节点");
		map.put("7","八号监测节点");
	}
}
