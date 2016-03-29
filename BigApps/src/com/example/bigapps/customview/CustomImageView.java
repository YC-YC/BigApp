/**
 * 
 */
package com.example.bigapps.customview;

import com.example.bigapps.R;
import com.example.bigapps.utils.ImgUtils;
import com.example.bigapps.utils.SizeUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * @author YC
 * @time 2016-3-29 下午7:29:57
 */
public class CustomImageView extends ImageView {

	private static final String TAG = "CustomImageView";
	
	private static final int TYPE_CIRCLE = 0;
	private static final int TYPE_ROUND = 1;
	
	/**类型*/
	private int mType;
	
	private int mWidth;
	private int mHeight;
	
	private int mRadius;
	
	private Bitmap mSrc;
	
	public CustomImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomImageView(Context context) {
		this(context, null);
	}
	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray a = context.obtainStyledAttributes(attrs, 
				R.styleable.CustomImageView, 
				defStyle, 0);
		
		int count = a.getIndexCount();
		for (int i = 0; i < count; i ++)
		{
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomImageView_boredRadius:
				mRadius = a.getDimensionPixelSize(attr, 
						SizeUtils.getStandarSize(context, TypedValue.COMPLEX_UNIT_PX, 10));
				break;
			case R.styleable.CustomImageView_type:
				mType = a.getInt(attr, 0);
				break;
			case R.styleable.CustomImageView_src:
				mSrc = BitmapFactory.decodeResource(getResources(), 
						a.getResourceId(attr, 0));

				break;
			}
		}
		a.recycle();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		 int mode = MeasureSpec.getMode(widthMeasureSpec);  
	     int size = MeasureSpec.getSize(widthMeasureSpec);  
		
	     if (mode == MeasureSpec.EXACTLY)
	     {
	    	 mWidth = size;
	     }
	     else
	     {
	    	// 由图片决定的宽 
	    	 int desireByImg = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
	    	 if (mode == MeasureSpec.AT_MOST)
		     {
		    	 mWidth = Math.min(size, desireByImg);
		     }
		     else
		     {
		    	 mWidth = desireByImg;
		     }
	     }
	     mode = MeasureSpec.getMode(heightMeasureSpec);  
	     size = MeasureSpec.getSize(heightMeasureSpec);  
		
	     if (mode == MeasureSpec.EXACTLY)
	     {
	    	 mHeight = size;
	     }
	     else
	     {
	    	// 由图片决定的宽 
	    	 int desireByImg = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
	    	 if (mode == MeasureSpec.AT_MOST)
		     {
	    		 mHeight = Math.min(size, desireByImg);
		     }
		     else
		     {
		    	 mHeight = desireByImg;
		     }
	     }
	     setMeasuredDimension(mWidth, mHeight);
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		switch (mType) {
		case TYPE_CIRCLE:
			int min = Math.min(mWidth, mHeight);
			mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
			canvas.drawBitmap(ImgUtils.createCircleImage(mSrc, min), 0, 0, null);
			break;
		case TYPE_ROUND:
			canvas.drawBitmap(ImgUtils.getRoundedCornerBitmap(mSrc, mRadius), 0, 0, null);
			break;

		default:
			break;
		}
	     Log.i(TAG, "onDraw");
	}
	
}
