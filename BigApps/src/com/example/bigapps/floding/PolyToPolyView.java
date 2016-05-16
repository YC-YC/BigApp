package com.example.bigapps.floding;

import com.example.bigapps.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

/**
 *@Author Administrator
 *@Time 2016-5-16 下午10:59:13
 *PolyToPoly测试
 */
public class PolyToPolyView extends View {

	private Bitmap mBitmap;
	private Matrix mMatrix;
	
	public PolyToPolyView(Context context) {
		super(context);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
		
		mMatrix = new Matrix();
		
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
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mBitmap, mMatrix, null);
	}

}
