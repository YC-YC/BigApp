/**
 * 
 */
package com.example.bigapps.horizonlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author YC
 * @time 2016-3-30 上午11:50:56
 */
public class MyLinearLayout extends LinearLayout{

	private static final String TAG = "MyLinearLayout";
	private int selectPosition = 2;

	
	private int state = 0;

	int mWidth = 100;
	int mHeight = 100;
	private int mChildWidth;
	private int mChildHeight;
	private final int mShowItemNum = 5; 
	private final int mItemPadding = 10; 
	private final float mMidItemScale = 1.5f; 
	
	private int mOffset = 0;
	
	public MyLinearLayout(Context context) {
		this(context, null);
	}
	
	public MyLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	
	public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//无视xml中的padding的布局
		setPadding(0, 0, 0, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int count = getChildCount();
//		Log.i(TAG ," onMeasure count = " + count);
		
		mWidth = 0;
		mHeight = 0;
		
		int width = 0;
		int height = 0;
		
		for (int i = 0 ; i < count ; i++){
			View childView = getChildAt(i);
			childView.measure(0, 0);
			width = childView.getMeasuredWidth();
			height = childView.getMeasuredHeight();
//			Log.i(TAG ," onMeasure width = " + childView.getMeasuredWidth() + ", height = " + childView.getMeasuredHeight());
			
			mChildWidth = Math.max(mChildWidth, width);
			mChildHeight = Math.max(mChildHeight, height);
			
		}
		mWidth += mChildWidth*mShowItemNum; 
		mWidth += (mShowItemNum - 1) * mItemPadding;
		mWidth += mChildWidth*(mMidItemScale - 1.0f);
		mHeight = (int) (mChildHeight * mMidItemScale);
		
		if (mWidth < 0)
		{
			mWidth = 0;
		}
		
		setMeasuredDimension(mWidth, mHeight);
		
//		Log.i(TAG ," onMeasure width = " + getMeasuredWidth() + ", height = " + getMeasuredHeight());
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		super.onLayout(changed, l, t, r, b);
		int count = getChildCount();
		
		int midIndex = getMidIndex(count);
		int left = 0;
		int paddingLeft = 10;
		int top = (mHeight - mChildHeight)/2;
		
		if (mOffset > mChildWidth + mItemPadding)
		{
			mOffset -= (mChildWidth + mItemPadding);
			Log.i(TAG ," onLayout loadPre");
		}
		
		if (mOffset < -(mChildWidth + mItemPadding))
		{
			mOffset += (mChildWidth + mItemPadding);
			Log.i(TAG ," onLayout loadNext");
		}
		
		left = mWidth/2 - mChildWidth/2 + mOffset;
//		Log.i(TAG ," onLayout mOffset = " + mOffset + ", left = " + left);
		View view;
		view = getChildAt(midIndex);
		view.layout(left, top, left+mChildWidth, top+mChildHeight);
		view.setScaleX(mMidItemScale);
		view.setScaleY(mMidItemScale);

		left -= mChildWidth*(mMidItemScale - 1.0f)/2;
		
		for (int i = midIndex-1; i >=0; i--)
		{
			left -= (mChildWidth + mItemPadding);
			view = getChildAt(i);
			view.layout(left, top, left+mChildWidth, top+mChildHeight);
		}
		
		left = mWidth/2 + mChildWidth/2 + mOffset;
		left += mChildWidth*(mMidItemScale - 1.0f)/2;
		for (int i = midIndex+1; i < count; i++)
		{
			left += mItemPadding;
			view = getChildAt(i);
			view.layout(left, top, left+mChildWidth, top+mChildHeight);
			left += mChildWidth;
		}
		
		
	}
	

	/**
	 * @param count
	 * @return
	 */
	private int getMidIndex(int count) {
		return count == 0 ? 0 :count/2;
	}

	private int mLastX = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		Log.i(TAG, "event action = " + event.getAction() + ", x = " + event.getX());
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			mLastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int) event.getX();
			mOffset += (x - mLastX);
			mLastX = x;
//			Log.i(TAG, "move mOffset = " + mOffset);
			requestLayout();
			break;
		case MotionEvent.ACTION_UP:
			mLastX = 0;
			break;
		default:
			break;
		}
		super.onTouchEvent(event);
		return true;
	}
}
