/**
 * 
 */
package com.example.bigapps.progressbar;

import com.example.bigapps.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * @author YC
 * @time 2016-5-22 上午10:32:44
 */
public class HorizontalProgressbarWithNum extends ProgressBar {
	
	private static final int DEFAULT_TEXT_SIZE = 10;	//sp
	private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;	
	private static final int DEFAULT_TEXT_OFFSET = 10;	//dp
	private static final int DEFAULT_TEXT_VISIBLE = 1;
	private static final int DEFAULT_UNREACH_PROGRESS_BAR_HEIGHT= 2;	//dp
	private static final int DEFAULT_REACH_PROGRESS_BAR_HEIGHT = 2;
	private static final int DEFAULT_UNREACH_PROGRESS_BAR_COLOR = 0xFFD3D6DA;
	private static final int DEFAULT_REACH_PROGRESS_BAR_COLOR = 0xFFD3D6DA;
	
	protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
	protected int mTextColor = DEFAULT_TEXT_COLOR;
	protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);
	protected int mTextVisible = DEFAULT_TEXT_VISIBLE;
	protected int mUnReachProgressbarHeight = dp2px(DEFAULT_UNREACH_PROGRESS_BAR_HEIGHT);
	protected int mReachProgressbarHeight = dp2px(DEFAULT_REACH_PROGRESS_BAR_HEIGHT);
	protected int mUnReachProgressBarColor = DEFAULT_UNREACH_PROGRESS_BAR_COLOR;
	protected int mReachProgressBarColor = DEFAULT_REACH_PROGRESS_BAR_COLOR;
	
	protected Paint mPaint = new Paint();
	
	/**进度条实际宽度*/
	private int mRealWidth;
	
	public HorizontalProgressbarWithNum(Context context) {
		this(context, null);
	}
	
	public HorizontalProgressbarWithNum(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public HorizontalProgressbarWithNum(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		setHorizontalScrollBarEnabled(true);
		
		getStyleAttributes(attrs);
		
		mPaint.setTextSize(mTextSize);
		mPaint.setColor(mTextColor);
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		//主要测量高度
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY){
			float textHeight = mPaint.descent() + mPaint.ascent();
			int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + Math.max(
					Math.max(mReachProgressbarHeight, mUnReachProgressbarHeight),
					Math.abs(textHeight)));
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		canvas.save();
		
		//画布移动到左边
		canvas.translate(getPaddingLeft(), getHeight()/2);
		
		//进度比例
		final float radio = getProgress()*1.0f/getMax();
		//进度值
		int progressX = (int) (mRealWidth * radio);
		final String text = getProgress() + "%";
		
		//文字的宽高
		final int textWidth = (int) mPaint.measureText(text);
		final int textHeight = (int) (mPaint.ascent() + mPaint.descent())/2;
		
		boolean bReachMax = false;
		
		//如果到达最后，则未到达的进度条不需要绘制  
		if (progressX + textWidth + mTextOffset > mRealWidth){
			progressX = mRealWidth - textWidth - mTextOffset;
			bReachMax = true;
		}

		//绘制已到的进度条
		final int endX = progressX;
		if (endX > 0){
			mPaint.setColor(mReachProgressBarColor);
			mPaint.setStrokeWidth(mReachProgressbarHeight);
			canvas.drawLine(0, 0, endX, 0, mPaint);
		}
		
		//绘制文本
		if (mTextVisible > 0){
			mPaint.setColor(mTextColor);
			canvas.drawText(text, progressX + mTextOffset, -textHeight, mPaint);
		}
		
		//绘制未到的进度条
		if (!bReachMax) {
			final int start  = progressX +  2*mTextOffset + textWidth;
			mPaint.setColor(mUnReachProgressBarColor);
			mPaint.setStrokeWidth(mUnReachProgressbarHeight);
			canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
		}
		
		canvas.restore();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		mRealWidth = w - getPaddingLeft() - getPaddingRight();
		
	}
	
	/**
	 * 获取自定义属性
	 */
	private void getStyleAttributes(AttributeSet attrs) {
		final TypedArray ta = getContext().obtainStyledAttributes(attrs, 
				R.styleable.HorizontalProgressbarWithNum);
		
		mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithNum_progress_text_size, 
				mTextSize);
		
		mTextColor = ta.getColor(R.styleable.HorizontalProgressbarWithNum_progress_text_color, 
				mTextColor);
		
		mTextOffset = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithNum_progress_text_offset, 
				mTextOffset);
		
		mTextVisible = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithNum_progress_text_visibility, 
				mTextVisible);
		
		mUnReachProgressBarColor = ta.getColor(R.styleable.HorizontalProgressbarWithNum_progress_unreach_color, 
				mUnReachProgressBarColor);
		
		mReachProgressBarColor = ta.getColor(R.styleable.HorizontalProgressbarWithNum_progress_reach_color, 
				mReachProgressBarColor);
		
		mUnReachProgressbarHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithNum_progress_unreach_bar_height, 
				mUnReachProgressbarHeight);
		
		mReachProgressbarHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithNum_progress_reach_bar_height, 
				mReachProgressbarHeight);
		
		ta.recycle();
	}

	protected int dp2px(int dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
				dpVal, 
				getResources().getDisplayMetrics());
	}
	
	protected int sp2px(int spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 
				spVal, 
				getResources().getDisplayMetrics());
	}

}
