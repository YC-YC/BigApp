/**
 * 
 */
package com.example.bigapps.verticalseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * @author YC
 * @time 2016-5-26 下午4:17:00
 * 竖向SeekBar
 */
public class VerticalSeekBar extends SeekBar {

	private static final String TAG = "VerticalSeekBar";

	public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalSeekBar(Context context) {
		super(context);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldh, oldw);
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
		Log.i(TAG, "onMeasure getMeasuredHeight() = " +getMeasuredHeight() + ", getMeasuredWidth() = " + getMeasuredWidth());
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		
//		//将SeekBar顺时针旋转90度
//		canvas.rotate(-90);
//		//将旋转后的视图移动回来  
//		canvas.translate(-getHeight(), 0);

		//将SeekBar顺时针旋转90度
		canvas.rotate(90);
		//将旋转后的视图移动回来  
		canvas.translate(0, -getWidth());
		super.onDraw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (!isEnabled()){
			return false;
		}
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_MOVE:
			int progress = /*getMax() - */(int) (getMax()*event.getY()/getHeight());
			setProgress(progress);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;

		default:
			break;
		}
		return true;
	}

}
