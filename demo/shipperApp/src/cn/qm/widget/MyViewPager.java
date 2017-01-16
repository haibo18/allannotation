package cn.qm.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager 设置滑动开关
 * 
 * @author QM
 *
 */
public class MyViewPager extends ViewPager {

	/**
	 * 在xml文件中初始化组件的时候使用
	 * 
	 * @param context
	 * @param attrs
	 */
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 在代码中初始化组件的时候使用
	 * 
	 * @param context
	 */
	public MyViewPager(Context context) {
		super(context);
	}

	/**
	 * 设置是否可以左右滑动开关
	 */
	private boolean isCanScroll;

	/**
	 * 设置滑动开关变量
	 * 
	 * @param isCanScroll
	 */
	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	/*
	 * 设置组件是否可以向下分发事件(non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	/*
	 * 设置组件是否拦截触摸事件(non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager#onInterceptTouchEvent(android.view.
	 * MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isCanScroll ? super.onInterceptTouchEvent(ev) : false;
	}

	/*
	 * 设置组件是否消费点击事件(non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return isCanScroll ? super.onTouchEvent(arg0) : false;
	}

	/*
	 * 设置页面 (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager#setOffscreenPageLimit(int)
	 */
	public void setOffscreenPageLimit(int limit) {
		super.setOffscreenPageLimit(1);
	}
}
