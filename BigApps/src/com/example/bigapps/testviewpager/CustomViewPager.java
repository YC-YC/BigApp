package com.example.bigapps.testviewpager;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 *@Author Administrator
 *@Time 2016-3-20 ����11:22:48
 *
 *1����ȡ��������View
 *
 */
@SuppressLint("NewApi")
public class CustomViewPager extends ViewPager {

	private static final String tag = "CustomViewPager";
	private static final float MIN_SCALE = 0.5f;
	private View mLeft,mRight;
	
	private float mTrans, mScale, mAlpha;
	
	private Map<Integer, View> mChildren = new HashMap<Integer, View>();
	
	public CustomViewPager(Context context) {
		super(context);
	}
	
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * �ṩ�������ⲿ���
	 * @param view
	 * @param position
	 */
	public void setViewForPosition(View view, int position)
	{
		mChildren.put(position, view);
	}
	
	public void removeViewFromPosition(Integer position)
	{
		mChildren.remove(position);
	}

	//���������¼�
	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {
		// TODO Auto-generated method stub
		Log.i(tag, "position = " + position + ", offset = " + offset + ", offsetPixels = " + offsetPixels);
		/**
		 * 0~1: position = 0; offset = 0~1
		 * 1~0: position = 0; offset = 1~0
		 * 
		 * offsetPixelsΪoffset��Ӧ����Ļ����
		 * position = 0~pagesize-1
		 * 
		 * position -->left
		 * position+1 -->right
		 * getCurrnetItem��getChild�޷���ȡ��Ӧ��View
		 * ͨ��һ��Map���洢
		 */
		
		mLeft = mChildren.get(position);
		mRight = mChildren.get(position+1);
		
		animStack(mLeft, mRight, offset, offsetPixels);
		
		
		super.onPageScrolled(position, offset, offsetPixels);
	}

	private void animStack(View left, View right, float offset,
			int offsetPixels) {
		if (right != null)
		{
			//offset:0~1  -> mScale:MIN_SCALE~1
//			mScale = offset*MIN_SCALE + MIN_SCALE;
			mScale = offset*(1-MIN_SCALE) + MIN_SCALE;
			mTrans = -getWidth() - getPageMargin() + offsetPixels;//ƫ��
			
			mAlpha = offset;
			
//			right.setPivotX(right.getWidth()/2);//�������ŵ�Ϊ��Ļ����
//			right.setPivotY(right.getHeight()/2);
			
			right.setScaleX(mScale);
			right.setScaleY(mScale);
			
			right.setTranslationX(mTrans);
//			right.setTranslationY(mTrans);
			
			right.setAlpha(mAlpha);
			
		}
		
		if (left != null)//����ƶ�ǰ��
		{
			left.bringToFront();
		}
	}
	
	

}
