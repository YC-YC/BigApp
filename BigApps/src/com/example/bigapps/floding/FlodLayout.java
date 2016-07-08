package com.example.bigapps.floding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;


/**
 *@Author Administrator
 *@Time 2016-5-19 下午11:28:59
 */
public class FlodLayout extends ViewGroup {

	private static final String TAG = "FlodLayout";

	/**原图*/
	private Bitmap mBitmap;
	
	private Canvas mCanvas = new Canvas();
	
	/**折叠后的总宽*/
	private int mTranslateDis;
	/**折叠后宽与原宽的比例*/
	private float mFactor = 0.6f;
	/**折叠块的个数*/
	private int mNumOffFlod = 8;
	/**折叠矩阵*/
	private Matrix[] mMatrixs = new Matrix[mNumOffFlod];;
	/**原图每块宽度*/
	private int mPerFoldWith;
	/**折叠时每块宽度*/
	private int mTranslatePerFoldWidth;
	
	/**折叠块左边半透层*/
	private Paint mSolidPaint;
	/**阴影*/
	private Paint mShadowPaint;
	private LinearGradient mShadowGradient;
	private Matrix mShadowMatrix;

	public FlodLayout(Context context) {
		this(context, null);
	}
	
	/*public FlodLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mMatrixs = new Matrix[mNumOffFlod];
		for (int i = 0; i < mNumOffFlod; i++) {
			mMatrixs[i] = new Matrix();
		}
		
		//半透
		mSolidPaint = new Paint();
		
		mShadowPaint = new Paint();
		mShadowPaint.setStyle(Style.FILL);
		
		//阴影
		mShadowGradient = new LinearGradient(0.0f, 0.0f, 0.5f, 0.0f, 
				Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
		
		mShadowPaint.setShader(mShadowGradient);
		mShadowMatrix = new Matrix();
		
		this.setWillNotDraw(false);
	}*/

	
	public FlodLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mMatrixs = new Matrix[mNumOffFlod];
		for (int i = 0; i < mNumOffFlod; i++)
		{
			mMatrixs[i] = new Matrix();
		}

		mSolidPaint = new Paint();
		mShadowPaint = new Paint();
		mShadowPaint.setStyle(Style.FILL);
		mShadowGradient = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK,
				Color.TRANSPARENT, TileMode.CLAMP);
		mShadowPaint.setShader(mShadowGradient);
		mShadowMatrix = new Matrix();
		this.setWillNotDraw(false);

	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		View child = getChildAt(0);
		measureChild(child, widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(child.getMeasuredWidth(),
				child.getMeasuredHeight());
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		View child = getChildAt(0);
		child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas.setBitmap(mBitmap);
		
		updateFlod();
		
	}

	private void updateFlod() {

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		Log.i(TAG, "updateFlod width = " + width + ", height = " + height);
		
		mTranslateDis = (int) (width*mFactor);
		mPerFoldWith = width/mNumOffFlod;
		mTranslatePerFoldWidth = mTranslateDis/mNumOffFlod;
		
		int alpha = (int) (255*(1 - mFactor));
		mSolidPaint.setColor(Color.argb((int) (alpha*0.8f), 0, 0, 0));
		
		Log.i(TAG, "updateFlod mTranslateDis = " + mTranslateDis + ", mPerFoldWith = " + mPerFoldWith);
		Log.i(TAG, "updateFlod mTranslatePerFoldWidth = " + mTranslatePerFoldWidth + ", alpha = " + alpha);
		
		
		mShadowMatrix.setScale(mPerFoldWith, 1);
		mShadowGradient.setLocalMatrix(mShadowMatrix);
		mShadowPaint.setAlpha(alpha);
		
		//折叠偏移高度
		int depth = (int) (Math.sqrt(mPerFoldWith*mPerFoldWith - mTranslatePerFoldWidth*mTranslatePerFoldWidth)/2);
		
		//转换点
		float[] src = new float[8];
		float[] des = new float[8];
		for (int i = 0; i < mNumOffFlod; i++){
			mMatrixs[i].reset();
			/*//左上
			src[0] = i * mPerFoldWith;
			src[1] = 0;
			
			//右上
			src[2] = src[0] + mPerFoldWith;
			src[3] = 0;
			
			//右下
			src[4] = src[2];
			src[5] = height;
			
			//左下
			src[6] = src[0];
			src[7] = src[5];*/
			
			src[0] = i * mPerFoldWith;
			src[1] = 0;
			src[2] = src[0] + mPerFoldWith;
			src[3] = 0;
			src[4] = src[2];
			src[5] = height;
			src[6] = src[0];
			src[7] = src[5];	
			
			boolean isEven = (i%2 == 0);
			
			/*//左上
			des[0] = i * mTranslatePerFoldWidth;
			des[1] = isEven ? 0: depth;
			
			//右上
			des[2] = des[0] + mTranslatePerFoldWidth;
			des[3] = isEven ? depth: 0;
			
			//右下
			des[4] = des[2];
			des[5] = isEven ? height - depth : height;
			
			//左下
			des[6] = des[0];
			des[7] = isEven ? height : height - depth ;*/
			
			des[0] = i * mTranslatePerFoldWidth;
			des[1] = isEven ? 0 : depth;

			des[2] = des[0] + mTranslatePerFoldWidth;
//			// 引入anchor
//			dst[0] = (anchorPoint > i * mFlodWidth) ? anchorPoint
//					+ (i - midFold) * mTranslateDisPerFlod : anchorPoint
//					- (midFold - i) * mTranslateDisPerFlod;
//			dst[2] = (anchorPoint > (i + 1) * mFlodWidth) ? anchorPoint
//					+ (i + 1 - midFold) * mTranslateDisPerFlod : anchorPoint
//					- (midFold - i - 1) * mTranslateDisPerFlod;

			des[3] = isEven ? depth : 0;
			des[4] = des[2];
			des[5] = isEven ? height - depth : height;
			des[6] = des[0];
			des[7] = isEven ? height : height - depth;
			
			for (int y = 0; y < 8; y++)  
            {  
				des[y] = Math.round(des[y]);  
//				Log.i(TAG, "updateFlod des[" + y + "] = " + des[y]);
            }
			
			mMatrixs[i].setPolyToPoly(src, 0, des, 0, src.length>>1);
		}
	}
	
	private boolean mIsReady;
	//绘制子元素
	@Override
	protected void dispatchDraw(Canvas canvas) {
		
		Log.i(TAG, "dispatchDraw mFactor = " + mFactor + ", mNumOffFlod = " + mNumOffFlod);
		
		if (mFactor == 0){
			return;
		}
		
		//不折叠时
		if (mFactor == 1){
			super.dispatchDraw(canvas);
			return;
		}
		
		for (int i = 0; i < mNumOffFlod; i++){
			canvas.save();
			//将matrix应用到canvas  
			canvas.concat(mMatrixs[i]);
			
			//控制显示的大小
			canvas.clipRect(mPerFoldWith*i, 0, mPerFoldWith*i + mPerFoldWith, getHeight());
			
//			if (!mIsReady){
//				mIsReady = true;
//				this.dispatchDraw(mCanvas);
//			}
//			
//			canvas.drawBitmap(mBitmap, 0, 0, null);
			
			if (mIsReady)  
            {  
                canvas.drawBitmap(mBitmap, 0, 0, null);  
            } else  
            {  
            	Log.i(TAG, "dispatchDraw first in");
//            	super.dispatchDraw(canvas);  
              super.dispatchDraw(mCanvas);  
                canvas.drawBitmap(mBitmap, 0, 0, null);  
                mIsReady = true;  
                
            }  
			//移动绘制阴影
			/*canvas.translate(mPerFoldWith*i, 0);
			if (i % 2 == 0){
				//画半透
				canvas.drawRect(0, 0, mPerFoldWith, getHeight(), mSolidPaint);
			} else {
				//画阴影
				canvas.drawRect(0, 0, mPerFoldWith, getHeight(), mShadowPaint);
			}*/
			
			Log.i(TAG, "dispatchDraw mMatrixs[i] = " + mMatrixs[i].toString());
			
			
			canvas.translate(mPerFoldWith * i, 0);
			if (i % 2 == 0)
			{
				canvas.drawRect(0, 0, mPerFoldWith, getHeight(), mSolidPaint);
			} else
			{
				canvas.drawRect(0, 0, mPerFoldWith, getHeight(), mShadowPaint);
			}
			
			canvas.restore();
		}
	}
}
