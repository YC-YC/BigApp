package com.example.bigapps.floding;

import com.example.bigapps.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.view.View;

/**
 *@Author Administrator
 *@Time 2016-5-16 下午10:59:13
 *PolyToPoly测试
 *添加阴影
 *添加固定折叠
 */
public class PolyToPolyViewWithFloding extends View {
	
	/**原图*/
	private Bitmap mBitmap;
	
	/**折叠后的总宽*/
	private int mTranslateDis;
	/**折叠后宽与原宽的比例*/
	private float mFactor = 0.8f;
	/**折叠块的个数*/
	private int mNumOffFlod = 8;
	/**折叠矩阵*/
	private Matrix[] mMatrixs = new Matrix[mNumOffFlod];
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
	
	public PolyToPolyViewWithFloding(Context context) {
		super(context);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
		
		mTranslateDis = (int) (mBitmap.getWidth()*mFactor);
		mPerFoldWith = mBitmap.getWidth()/mNumOffFlod;
		mTranslatePerFoldWidth = mTranslateDis/mNumOffFlod;
		
		mMatrixs = new Matrix[mNumOffFlod];
		for (int i = 0; i < mNumOffFlod; i++) {
			mMatrixs[i] = new Matrix();
		}
		
		//半透
		mSolidPaint = new Paint();
		int alpha = (int) (255*mFactor*0.8f);
		mSolidPaint.setColor(Color.argb(alpha, 0, 0, 0));
		
		
		mShadowPaint = new Paint();
		mShadowPaint.setStyle(Style.FILL);
		
		//阴影
		mShadowGradient = new LinearGradient(0.0f, 0.0f, 0.5f, 0.0f, 
				Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
		
		mShadowPaint.setShader(mShadowGradient);
		mShadowMatrix = new Matrix();
		mShadowMatrix.setScale(mPerFoldWith, 1);
		mShadowGradient.setLocalMatrix(mShadowMatrix);
		mShadowPaint.setAlpha(alpha);
		
		//折叠偏移高度
		int depth = (int) (Math.sqrt(mPerFoldWith*mPerFoldWith - mTranslatePerFoldWidth*mTranslatePerFoldWidth)/2);
		
		//转换点
		float[] src = new float[8];
		float[] des = new float[8];
		
		for (int i = 0; i < mNumOffFlod; i++){
			//左上
			src[0] = i * mPerFoldWith;
			src[1] = 0;
			
			//右上
			src[2] = src[0] + mPerFoldWith;
			src[3] = 0;
			
			//右下
			src[4] = src[2];
			src[5] = mBitmap.getHeight();
			
			//左下
			src[6] = src[0];
			src[7] = src[5];
			
			boolean isEven = (i%2 == 0);
			
			//左上
			des[0] = i * mTranslatePerFoldWidth;
			des[1] = isEven ? 0: depth;
			
			//右上
			des[2] = des[0] + mTranslatePerFoldWidth;
			des[3] = isEven ? depth: 0;
			
			//右下
			des[4] = des[2];
			des[5] = isEven ? mBitmap.getHeight() - depth : mBitmap.getHeight();
			
			//左下
			des[6] = des[0];
			des[7] = isEven ? mBitmap.getHeight() : mBitmap.getHeight() - depth ;
			
			mMatrixs[i].setPolyToPoly(src, 0, des, 0, src.length>>1);
		}
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < mNumOffFlod; i++){
			canvas.save();
			//将matrix应用到canvas  
			canvas.concat(mMatrixs[i]);
			
			//控制显示的大小
			canvas.clipRect(mPerFoldWith*i, 0, mPerFoldWith*i + mPerFoldWith, mBitmap.getHeight());
			
			canvas.drawBitmap(mBitmap, 0, 0, null);
			
			//移动绘制阴影
			canvas.translate(mPerFoldWith*i, 0);
			if (i % 2 == 0){
				//画半透
				canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mSolidPaint);
			} else {
				//画阴影
				canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mShadowPaint);
			}
			
			canvas.restore();
		}
	}

}
