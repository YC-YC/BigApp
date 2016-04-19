package com.example.bigapps.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * @author YC
 * @time 2016-3-10 ����5:15:19
 */
public class ImgUtils {

	/**
	 * ��SD��·���ϻ�ȡͼ��
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapFromSDCard(String path)
	{
		return BitmapFactory.decodeFile(path);
	}
	
	/**
	 * ����ͼƬ
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale((float)width/w, (float)height/h);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}
	
	/**
	 * drawableת��Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable)
	{
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, 
				drawable.getOpacity() != PixelFormat.OPAQUE ?
						Bitmap.Config.ARGB_8888:
							Bitmap.Config.RGB_565);//͸����
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0 , width, height);
		drawable.draw(canvas);
		return bitmap;
		
	}
	
	
   
   /**
    * ��ô���Ӱ��ͼƬ
    * @param bitmap
    * @return
    */
   public static Bitmap getReflectBitmapWithOrigin(Bitmap bitmap)  {
	   
	   final int reflectionGap = 10;	//�м�����
	   int width = bitmap.getWidth();
	   int height = bitmap.getHeight();
	   
	   Matrix matrix = new Matrix();
	// ��һ������Ϊ1��ʾx��������ԭ����Ϊ׼���ֲ��䣬������ʾ���򲻱䡣     
       // �ڶ�������Ϊ-1��ʾy��������ԭ����Ϊ׼���ֲ��䣬������ʾ����ȡ����
	   matrix.setScale(1, -1);
	   
	   Bitmap reflectImg = Bitmap.createBitmap(bitmap, 0, height*3/4, width, height/4, matrix, false);
	   
	   Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height+height/4, Bitmap.Config.ARGB_8888);
	   
	   //��������
	   Canvas canvas = new Canvas(bitmapWithReflection);
	
	   //��ԭͼ
	   canvas.drawBitmap(bitmap, 0, 0, null);
//	   
	   Paint defalutpaint = new Paint();
//	   //���м�����
	   canvas.drawRect(0, height, width, height+reflectionGap, defalutpaint);
//	   
//	   //�ϳ�һ��ͼƬ
	   canvas.drawBitmap(reflectImg, 0, height+reflectionGap, null);
	   
	   LinearGradient shader = new LinearGradient(
			   0, bitmap.getHeight(), //������ʼ��
			   0, bitmapWithReflection.getHeight()+reflectionGap,	//�����յ�
			   0x70ffffff,  //��ʼ��ɫ
               0x00ffffff, 	//�յ���ɫ
               TileMode.CLAMP) ;//ƽ�̷�ʽ
	   
	   Paint paint = new Paint();
	   paint.setShader(shader);
	   
	   paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));  
       canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()  
               + reflectionGap, paint);  
       return bitmapWithReflection; 
   }
   
   /**  
    * ���Բ��ͼƬ  
    * @param bitmap  
    * @param roundPx  
    * @return  
    */  
  public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {  
      Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap  
              .getHeight(), Config.ARGB_8888);  
      Canvas canvas = new Canvas(output);  
      final Paint paint = new Paint();  
      paint.setAntiAlias(true);
      final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
      final RectF rectF = new RectF(rect);  
      canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
      paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
      canvas.drawBitmap(bitmap, rect, rect, paint);  
      return output;  
  }  
   
   /**
	 * ����Բ��ͼƬ
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		int srcW = bitmap.getWidth();
		int srcH = bitmap.getHeight();
		if(srcW > srcH){
			srcW = srcH;
		}else{
			srcH = srcW;
		}
		Bitmap output = Bitmap.createBitmap(srcW, srcH/*bitmap.getWidth(), bitmap.getHeight()*/, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, srcW, srcH/*bitmap.getWidth(), bitmap.getHeight()*/);
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() / 2;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	 * ����Բͼ
	 * @param source
	 * @param min
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source, int min)  
    {  
        final Paint paint = new Paint();  
        paint.setAntiAlias(true);  
        paint.setFilterBitmap(true);
        Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);  
        /** ����һ��ͬ����С�Ļ���  */  
        Canvas canvas = new Canvas(target);  
        /** ���Ȼ���Բ�� */  
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);  
        /**  ʹ��SRC_IN���ο������˵�� */  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        /**  ����ͼƬ  */  
        canvas.drawBitmap(source, 0, 0, paint);  
        return target;  
    } 
	
	
	/**
	 * ԭͼ����Բͼ
	 * @param source
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source)  
    {  
		if (source == null){
			return null;
		}
		int min =  Math.min(source.getWidth(), source.getHeight());
        final Paint paint = new Paint();  
        paint.setAntiAlias(true);  
        paint.setFilterBitmap(true);
        Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);  
        /** ����һ��ͬ����С�Ļ���  */  
        Canvas canvas = new Canvas(target);  
        /** ���Ȼ���Բ�� */  
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);  
        /**  ʹ��SRC_IN���ο������˵�� */  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        /**  ����ͼƬ  */  
        canvas.drawBitmap(source, 0, 0, paint);  
        return target;  
    } 
	
	
	/**
	 * ��˹ģ��
	 * @param bitmap
	 * @return
	 */
	@SuppressLint("NewApi")
	public static Bitmap blurBitmap(Context context, Bitmap bitmap){  
        
        //Let's create an empty bitmap with the same size of the bitmap we want to blur  
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);  
          
        //Instantiate a new Renderscript  
        RenderScript rs = RenderScript.create(context);  
          
        //Create an Intrinsic Blur Script using the Renderscript  
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));  
          
        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps  
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);  
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);  
          
        //Set the radius of the blur  
        blurScript.setRadius(25.f);  
          
        //Perform the Renderscript  
        blurScript.setInput(allIn);  
        blurScript.forEach(allOut);  
          
        //Copy the final bitmap created by the out Allocation to the outBitmap  
        allOut.copyTo(outBitmap);  
          
        //recycle the original bitmap  
        bitmap.recycle();  
          
        //After finishing everything, we destroy the Renderscript.  
        rs.destroy();  
          
        return outBitmap;  
          
    } 
	
	/*public static Bitmap blurBitmapJni(Bitmap sentBitmap, int radius, boolean canReuseInBitmap)
	{
		 Bitmap bitmap;
	        if (canReuseInBitmap) {
	            bitmap = sentBitmap;
	        } else {
	            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
	        }
	        if (radius < 1) {
	            return (null);
	        }
	        //Jni BitMap
	        ImageBlur.blurBitMap(bitmap, radius);

	        return (bitmap);
		
		
	}*/
}
