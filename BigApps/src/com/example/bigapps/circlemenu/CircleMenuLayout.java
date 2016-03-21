/**
 * 
 */
package com.example.bigapps.circlemenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-3-21 上午11:19:11
 */
@SuppressLint("NewApi")
public class CircleMenuLayout extends ViewGroup {

	private static final String tag = "CircleMenuLayout";
	
	/**
	 *  直径
	 */
	private int mRadius;

	/**
	 *  根据menu item的个数，计算角度
	 */
	private	float mAngleDelay;
	
	/**
	 * 该容器的内边距,无视padding属性，如需边距请用该变量
	 */
	private float mPadding;

	/**
	 * 该容器内child item的默认尺寸
	 */
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	/**
	 * 菜单的中心child的默认尺寸
	 */
	private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
	/**
	 * 该容器的内边距,无视padding属性，如需边距请用该变量，到外部的距离
	 */
	private static final float RADIO_PADDING_LAYOUT = 1 / 12f;
	/**
	 * 布局时的开始角度
	 */
	private double mStartAngle = -45;

	/**
	 * 最小的缩放值
	 */
	private static final float MIN_SCALE = 0.5f;
	/**
	 * 菜单的个数
	 */
	private int mMenuItemCount;

	/**
	 * 菜单项的文本
	 */
	private String[] mItemTexts;
	/**
	 * 菜单项的图标
	 */
	private int[] mItemImgs;

	
	/**
	 * @param context
	 * @param attrs
	 */
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//无视xml中的padding的布局
		setPadding(0, 0, 0, 0);
	}

	/**
	 * 设置菜单条目的图标和文本
	 * 
	 * @param resIds
	 */
	public void setMenuItemIconsAndTexts(int[] resIds, String[] texts) {
		mItemImgs = resIds;
		mItemTexts = texts;

		// 参数检查
		if (resIds == null && texts == null) {
			throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
		}

		// 初始化mMenuCount
		mMenuItemCount = resIds == null ? texts.length : resIds.length;

		if (resIds != null && texts != null) {
			mMenuItemCount = Math.min(resIds.length, texts.length);
		}

		addMenuItems();

	}

	/**
	 * 添加菜单项
	 */
	private void addMenuItems() {
		LayoutInflater mInflater = LayoutInflater.from(getContext());

		/**
		 * 根据用户设置的参数，初始化view
		 */
		for (int i = 0; i < mMenuItemCount; i++) {
			final int j = i;
			View view = mInflater.inflate(R.layout.circle_menu_item, this,
					false);
			ImageView iv = (ImageView) view
					.findViewById(R.id.id_circle_menu_item_image);
			TextView tv = (TextView) view
					.findViewById(R.id.id_circle_menu_item_text);

			if (iv != null) {
				iv.setVisibility(View.VISIBLE);
				iv.setImageResource(mItemImgs[i]);
				iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						if (mOnMenuItemClickListener != null) {
							mOnMenuItemClickListener.itemClick(v, j);
						}
					}
				});
			}
			if (tv != null) {
				tv.setVisibility(View.VISIBLE);
				tv.setText(mItemTexts[i]);
			}

			// 添加view到容器中
			addView(view);
		}
	}

	/**
	 * MenuItem的点击事件接口
	 * 
	 */
	public interface OnMenuItemClickListener {
		void itemClick(View view, int pos);
	}

	/**
	 * MenuItem的点击事件接口
	 */
	private OnMenuItemClickListener mOnMenuItemClickListener;

	/**
	 * 设置MenuItem的点击事件接口
	 * 
	 * @param mOnMenuItemClickListener
	 */
	public void setOnMenuItemClickListener(
			OnMenuItemClickListener mOnMenuItemClickListener) {
		this.mOnMenuItemClickListener = mOnMenuItemClickListener;
	}

	/**
	 * 获得默认该layout的尺寸
	 * 
	 * @return
	 */
	private int getDefaultWidth() {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int resWidth = 0;
		int resHeight = 0;
		Log.i(tag, "onMeasure");
		/**
		 * 根据传入的参数，分别获取测量模式和测量值
		 */
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		/**
		 * 如果宽或者高的测量模式非精确值
		 */
		if (widthMode != MeasureSpec.EXACTLY
				|| heightMode != MeasureSpec.EXACTLY) {
			// 主要设置为背景图的高度
			resWidth = getSuggestedMinimumWidth();
			// 如果未设置背景图片，则设置为屏幕宽高的默认值
			resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

			resHeight = getSuggestedMinimumHeight();
			// 如果未设置背景图片，则设置为屏幕宽高的默认值
			resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
		} else {
			// 如果都设置为精确值，则直接取小值；
			resWidth = resHeight = Math.min(width, height);
		}

		// 设置view的w,h
		setMeasuredDimension(resWidth, resHeight);

		// 获得半径
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
		// menu item数量
		final int count = getChildCount();
		// menu item尺寸
		int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// menu item测量模式
		int childMode = MeasureSpec.EXACTLY;

		// 迭代测量
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child.getVisibility() == GONE) {
				continue;
			}

			// 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
			int makeMeasureSpec = -1;

			if (child.getId() == R.id.id_circle_menu_item_center) {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
						childMode);
			} else {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
						childMode);
			}
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}

		mPadding = RADIO_PADDING_LAYOUT * mRadius;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int angle = 180;
		
		int layoutRadius = mRadius;

		// Laying out the child views
		final int childCount = getChildCount();

		int left, top;
		// menu item 的尺寸
		int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// 根据menu item的个数，计算角度
		mAngleDelay = angle / (getChildCount()-1);
		
		Log.i(tag, "getCount = " + childCount + ", per angle = " + mAngleDelay);
		// 遍历去设置menuitem的位置
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE) {
				continue;
			}

//			mStartAngle /= 1;
//			if (mStartAngle > angle)
//			{
//				mStartAngle %= angle;
//			}
			mStartAngle %= angle+mAngleDelay;
			Log.i(tag, "mStartAngle = " + mStartAngle);

			// 计算，中心点到menu item中心的距离
			float tmp = layoutRadius / 2f - cWidth / 2 - mPadding;

			// tmp cosa 即menu item中心点的横坐标
			left = layoutRadius/ 2
					+ (int) Math.round(tmp
							* Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);
			// tmp sina 即menu item的纵坐标
			top = layoutRadius/ 2
					+ (int) Math.round(tmp
							* Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);

			float level = getLevel(mStartAngle);
			Log.i(tag, "getLevel = " + level);
			child.layout(left, top, left + cWidth, top + cWidth);
			child.setScaleX(level);
			child.setScaleY(level);
			child.setAlpha(level);
			// 叠加尺寸
			mStartAngle += mAngleDelay;
		}

	}

	
	/**
	 * 通过角度来获取缩放或Alpha比例
	 * @param startAngle
	 * @return
	 */
	private float getLevel(double startAngle) {
		float result = 0.0f;
		if (startAngle < 0)
		{
			result = MIN_SCALE;
		}
		else if (startAngle < 90)
		{
			result =  MIN_SCALE + (float)startAngle/90f*(1-MIN_SCALE);
		}else if (startAngle < 180)
		{
			result =  1.0f - (float)((startAngle-90)/90)*(1-MIN_SCALE);
		}
		else
		{
			result = MIN_SCALE;
		}
		return result;
	}

	/**
	 * 检测按下到抬起时旋转的角度
	 */
	private float mTmpAngle;
	/**
	 * 检测按下到抬起时使用的时间
	 */
	private long mDownTime;
	/**
	 * 记录上一次的x，y坐标
	 */
	private float mLastX;
	private float mLastY;
	/**
	 * 判断是否正在自动滚动
	 */
	private boolean isFling;
	/**
	 * 自动滚动的Runnable
	 */
	private AutoFlingRunnable mFlingRunnable;
	/**
	 * 当每秒移动角度达到该值时，认为是快速移动
	 */
	private static final int FLINGABLE_VALUE = 300;
	/**
	 * 当每秒移动角度达到该值时，认为是快速移动
	 */
	private int mFlingableValue = FLINGABLE_VALUE;
	/**
	 * 如果移动角度达到该值，则屏蔽点击
	 */
	private static final int NOCLICK_VALUE = 3;
	/**
	 * 根据触摸的位置，计算角度
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private float getAngle(float xTouch, float yTouch)
	{
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}
	
	/**
	 * 根据当前位置计算象限
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getQuadrant(float x, float y)
	{
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0)
		{
			return tmpY >= 0 ? 4 : 1;
		} else
		{
			return tmpY >= 0 ? 3 : 2;
		}

	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		float x = ev.getX();
		float y = ev.getY();
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN://按下
			mLastX = x;
			mLastY = y;
			mDownTime = SystemClock.elapsedRealtime();
			mTmpAngle = 0;
			if (isFling) // 如果当前已经在滚动  
			{
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			float start = getAngle(mLastX, mLastY);//开始时角度
			float end = getAngle(x, y);	//当前角度
	         Log.e(tag, "start = " + start + " , end =" + end);  
            // 如果是一、四象限，则直接end-start，角度值都是正值  
            if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4)  
            {  
                mStartAngle += end - start;  
                mTmpAngle += end - start;  
            } else  
            // 二、三象限，色角度值是付值  
            {  
                mStartAngle += start - end;  
                mTmpAngle += start - end;  
            }  
            // 重新布局  
            requestLayout();  
  
            mLastX = x;  
            mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
			 // 计算，每秒移动的角度  
            float anglePerSecond = mTmpAngle * 1000/(SystemClock.elapsedRealtime() - mDownTime);  
            // 如果达到该值认为是快速移动  
            if (Math.abs(anglePerSecond) > mFlingableValue && !isFling)  
            {  
                // post一个任务，去自动滚动  
                post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));  
  
                return true;  
            }  
            else
            {
            	mStartAngle = getStopAngle(mStartAngle);
				requestLayout();
            }
  
            // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击  
            if (Math.abs(mTmpAngle) > NOCLICK_VALUE)  
            {  
                return true;  
            }  
            break;
		default:
			break;
		}
		
		return super.dispatchTouchEvent(ev);
	}
	
	/**
	 * @param mStartAngle
	 * @return
	 */
	private double getStopAngle(double startAngle) {
		
		int position = (int) (startAngle/mAngleDelay);
		int tmp = (int) (startAngle%mAngleDelay);
		if (tmp > mAngleDelay/2)
		{
			position++;
		}
		
		return position*mAngleDelay;
	}
	
	/**
	 * 自动滚动的任务
	 */
	private class AutoFlingRunnable implements Runnable
	{

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity)
		{
			this.angelPerSecond = velocity;
		}

		public void run()
		{
			// 如果小于20,则停止
			if ((int) Math.abs(angelPerSecond) < 20)
			{
				isFling = false;
				mStartAngle = getStopAngle(mStartAngle);
				requestLayout();
				return;
			}
			isFling = true;
			// 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
			mStartAngle += (angelPerSecond / 30);
			// 逐渐减小这个值
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// 重新布局
			requestLayout();
		}

		
	}


}
