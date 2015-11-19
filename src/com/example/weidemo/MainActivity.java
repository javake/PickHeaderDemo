package com.example.weidemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weidemo.MyListView.OnYScrollListener;

public class MainActivity extends Activity implements OnYScrollListener {
	/**
	 * 自定义的MyScrollView
	 */
	private MyListView myListV;
	/**
	 * 在MyScrollView里面的布局
	 */
	private View mBuyLayout;
	/**
	 * 位于顶部的布局
	 */
	private View mTopBuyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myListV = (MyListView) findViewById(R.id.mylistv);
		myListV.setOnScrollListener(this);
		
		View headV = LayoutInflater.from(this).inflate(R.layout.head_layout, null);
		
		mBuyLayout = headV.findViewById(R.id.head_pick);
		myListV.addHeaderView(headV);
		
		mTopBuyLayout = (LinearLayout) findViewById(R.id.top_buy_layout);
		
		
		MyAdapter ada = new MyAdapter(buildData(30, "List item info. "));
		myListV.setAdapter(ada);
		
		((TextView)mTopBuyLayout.findViewById(R.id.in2)).setText("hehe , Hold top title view.");


		// 当布局的状态或者控件的可见性发生改变回调的接口
		findViewById(R.id.parent_layout).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						onYScroll(myListV.getScrollY());
						System.out.println(myListV.getScrollY());
					}
				});
	}

	@Override
	public void onYScroll(int scrollY) {
		System.out.println("   mainactivity - onYScroll = "+scrollY+"  -- "+mBuyLayout.getTop());
		// 方案一 、 下面的view 会始终被 挡着 
		int mtop = mBuyLayout.getTop() - scrollY;
		mtop = mtop > 0 ? mtop : 0;
		mTopBuyLayout.layout(0, mtop, mTopBuyLayout.getWidth(),
				mtop + mTopBuyLayout.getHeight());
		
		// 方案二、滑倒顶  就把 上面的view 展示出来
//		mTopBuyLayout.setVisibility(scrollY >= mBuyLayout.getTop() ? View.VISIBLE:View.GONE);
	}

	class MyAdapter extends BaseAdapter {

		private List<String> list;
		
		public MyAdapter(List<String> list) {
			this.list = list;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater li = LayoutInflater.from(parent.getContext());
			View rv = li.inflate(R.layout.item, null);
			TextView tv = (TextView) rv.findViewById(R.id.val);
			tv.setText(list.get(position));
			return rv;
		}
		
		public void updateDataList(List<String> ndList) {
			this.list = ndList;
			notifyDataSetChanged();
		}
		
	}
	
	private List<String> buildData(int size, String preStr) {
		List<String> dlist = new ArrayList<String>();
		
		for (int i = 0; i < size; i++) {
			String item = preStr + " - item "+i;
			dlist.add(item);
		}
		return dlist;
	}
	
}
