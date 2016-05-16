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
 */
public class PolyToPolyViewWithShadow extends View {

	private Bitmap mBitmap;
	private Matrix mMatrix;
	
	//阴影
	private Paint mShadowPaint;
	private LinearGradient mShadowGradient;
	private Matrix mShadowMatrix;
	
	public PolyToPolyViewWithShadow(Context context) {
		super(context);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
		
		mMatrix = new Matrix();
		
		mShadowPaint = new Paint();
		mShadowPaint.setStyle(Style.FILL);
		
		mShadowGradient = new LinearGradient(0.0f, 0.0f, 0.5f, 0.0f, 
				Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
		
		mShadowPaint.setShader(mShadowGradient);
		
		mShadowMatrix = new Matrix();
		mShadowMatrix.setScale(mBitmap.getWidth(), 1);
		
		mShadowGradient.setLocalMatrix(mShadowMatrix);
		
		mShadowPaint.setAlpha((int) (0.9*255));
		
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		float[] src = {
				0,0,	//左上
				mBitmap.getWidth(), 0,	//右上
				mBitmap.getWidth(), mBitmap.getHeight(),	//右下
				0, mBitmap.getHeight()	//左下
		};
		float[] dst = {
				0,0,	//左上
				mBitmap.getWidth(), 50,	//右上
				mBitmap.getWidth(), mBitmap.getHeight()-50,	//右下
				0, mBitmap.getHeight()	//左下
		};
		mMatrix.setPolyToPoly(src, 0, dst, 0, src.length>>1);
		canvas.concat(mMatrix);
		canvas.drawBitmap(mBitmap, 0, 0, null);
		
		//画阴影
		canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mShadowPaint);
		
		canvas.restore();
	}

}
