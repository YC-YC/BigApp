/**
 * 
 */
package com.example.bigapps.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-5-22 下午4:14:18
 */
public class RoundProgressbarWithNum extends HorizontalProgressbarWithNum {

	/**半径*/
	private int mRadius = dp2px(30);
	
	public RoundProgressbarWithNum(Context context) {
		this(context, null);
	}

	public RoundProgressbarWithNum(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public RoundProgressbarWithNum(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mReachProgressbarHeight = (int) (mUnReachProgressbarHeight*2.5f);
		
		getStyleAttributes(attrs);
		
		mPaint.setStyle(Style.STROKE);  
        mPaint.setAntiAlias(true);  
        mPaint.setDither(true);  
        mPaint.setStrokeCap(Cap.ROUND);
	}

	/**
	 * 获取自定义属性
	 */
	private void getStyleAttributes(AttributeSet attrs) {
		final TypedArray ta = getContext().obtainStyledAttributes(attrs, 
				R.styleable.RoundProgressbarWithNum);
		
		mRadius = (int) ta.getDimension(R.styleable.RoundProgressbarWithNum_radius, 
				mRadius);
		
		ta.recycle();
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		final int paintWidth = Math.max(mReachProgressbarHeight,
				mUnReachProgressbarHeight);

		if (widthMode != MeasureSpec.EXACTLY) {
			final int exceptWidth = getPaddingLeft() + getPaddingRight() + 2
					* mRadius + paintWidth;
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth,
					MeasureSpec.EXACTLY);
		}
		
		if (heightMode != MeasureSpec.EXACTLY) {
			final int exceptHeight = getPaddingTop() + getPaddingBottom() + 2
					* mRadius + paintWidth;
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,
					MeasureSpec.EXACTLY);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {

		final String text = getProgress() + "%";
		
		//文字的宽高
		final int textWidth = (int) mPaint.measureText(text);
		final int textHeight = (int) (mPaint.ascent() + mPaint.descent())/2;
		
		canvas.save();
		//画布移动到左边
		canvas.translate(getPaddingLeft(), getPaddingTop());
		
		mPaint.setStyle(Style.STROKE);
		
		//未到达进度
		mPaint.setColor(mUnReachProgressBarColor);
		mPaint.setStrokeWidth(mUnReachProgressbarHeight);
		canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
		
		//已走进度
		mPaint.setColor(mReachProgressBarColor);
		mPaint.setStrokeWidth(mReachProgressbarHeight);
		final float sweepAngle = getProgress()*1.0f/getMax()*360;
		canvas.drawArc(new RectF(0, 0, 2*mRadius, 2*mRadius), 
				0, sweepAngle, false, mPaint);
		
		//进度文本
		mPaint.setStyle(Style.FILL);
		canvas.drawText(text, mRadius - textWidth/2, mRadius - textHeight, mPaint);
				
		canvas.restore();
	}
	
}
