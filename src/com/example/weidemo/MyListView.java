package com.example.weidemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class MyListView extends ListView {

	private OnYScrollListener onScrollListener;
	
	public MyListView(Context context) {
		this(context, null);
	}
	
	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	/**
	 * 设置滚动接口
	 */
	public void setOnScrollListener(OnYScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		int fp = getFirstVisiblePosition();
		int y = 0;
		if (fp == 0) {
			View v= getChildAt(0);
			y = Math.abs((int)v.getY());
		} else {
			y = Integer.MAX_VALUE;
		}
		
		System.out.println(" MyListView. onScrollChanged "+l+"  "+t+"  "+oldl+"  "+oldt + "     -- "+y);
		if(onScrollListener != null) {
			onScrollListener.onYScroll(y);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	/**
	 * 滚动的回调接口
	 */
	public interface OnYScrollListener{
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * @param scrollY
		 */
		public void onYScroll(int scrollY);
	}

}
