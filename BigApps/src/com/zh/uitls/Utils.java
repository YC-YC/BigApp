/**
 * 
 */
package com.zh.uitls;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.StatFs;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author zhonghong.chenli date:2015-6-12����10:07:45 <br/>
 */
public class Utils {
	private static final String tag = "Utils";
	private Utils() {
	}

	private static Utils mUtils;
	HashMap<String, Long> timeRecord;

	public static Utils getInstance() {
		if (mUtils == null) {
			mUtils = new Utils();
		}
		return mUtils;
	}

	public void startTime(String paramString) {
		if (this.timeRecord == null){
			this.timeRecord = new HashMap<String, Long>();
		}
		this.timeRecord.put(paramString, Long.valueOf(SystemClock.uptimeMillis()));
	}

	public void endUseTime(String paramString) {
		if ((this.timeRecord == null) || (!this.timeRecord.containsKey(paramString))) {
			L.e("Utils", paramString + " ���� �� δ������ʼʱ�䣡");
			return;
		}
		long l = SystemClock.uptimeMillis() - ((Long) this.timeRecord.get(paramString)).longValue();
		this.timeRecord.remove(paramString);
		L.i("Utils", paramString + " ����ʱ�� �� " + l);
	}
	
	
	/***
	 * ��ʽ��ʱ��
	 */
	@SuppressLint("SimpleDateFormat")
	public String formatTime(long time){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�ȼ���
		 return sdf.format(time);
	}
	
	
	/**
	 * ��ʽ����С
	 * @param paramLong
	 * @return
	 */
	public static String formatFileSize(long paramLong) {
		DecimalFormat localDecimalFormat = new DecimalFormat("#.00");
		if (paramLong == 0L)
			return "0B";
		if (paramLong < 1024L)
			return "0 KB";
		if (paramLong < 1048576L)
			return localDecimalFormat.format(paramLong / 1024.0D) + "KB";
		if (paramLong < 1073741824L)
			return localDecimalFormat.format(paramLong / 1048576.0D) + "MB";
		return localDecimalFormat.format(paramLong / 1073741824.0D) + "GB";
	}
	
	/**
	 * ȡ�ļ�·�����һ��,������/��
	 */
	public String getFilePathLastSub(String filePath){
		return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
	}
	
	/**
	 * ȡ�ļ������ļ���
	 */
	public String getDirPathFromfile(String filePath){
		return filePath.substring(0, filePath.lastIndexOf("/"));
	}
	
	/** 
	 * ���ַ���ƴ������ĸԭ�����أ���ת��ΪСд����ĸ
	 * @param input
	 * @return
	 */
	/*public String getPinYin(String input) {
		String result = cn2FirstSpell(input);
		if(result == null || "".equals(result.trim())){
			if(input != null && input.length() > 0){
				result =  input.substring(0, 1);
			} 
		}
		char firstA = result.charAt(0);
		if(!(firstA >= 'A' && firstA <= 'Z') && !(firstA >= 'a'  && firstA <= 'z')){
			return "~" + result;
		}
		return result.toUpperCase();
	}*/
	
	/* *//** 
     * ��ȡ���ִ�ƴ������ĸ��Ӣ���ַ����� 
     * 
     * @param chinese ���ִ� 
     * @return ����ƴ������ĸ 
     *//* 
    public static String cn2FirstSpell(String chinese) { 
            StringBuffer pybf = new StringBuffer(); 
            char[] arr = chinese.toCharArray(); 
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
            for (int i = 0; i < arr.length; i++) { 
                    if (arr[i] > 128) { 
                            try { 
                                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat); 
                                    if (_t != null) { 
                                            pybf.append(_t[0].charAt(0)); 
                                    } 
                            } catch (BadHanyuPinyinOutputFormatCombination e) { 
                                    e.printStackTrace(); 
                            } 
                    } else { 
                            pybf.append(arr[i]); 
                    } 
            } 
            return pybf.toString().replaceAll("\\W", "").trim(); 
    } 
*/
    /** 
     * ��ȡ���ִ�ƴ����Ӣ���ַ����� 
     * 
     * @param chinese ���ִ� 
     * @return ����ƴ�� 
     */ 
 /*   public static String cn2Spell(String chinese) { 
            StringBuffer pybf = new StringBuffer(); 
            char[] arr = chinese.toCharArray(); 
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat(); 
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); 
            for (int i = 0; i < arr.length; i++) { 
                    if (arr[i] > 128) { 
                            try { 
                                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]); 
                            } catch (BadHanyuPinyinOutputFormatCombination e) { 
                                    e.printStackTrace(); 
                            } 
                    } else { 
                            pybf.append(arr[i]); 
                    }  
            } 
            return pybf.toString(); 
    }   */

    
    /**
	 * �õ� ȫƴ
	 * 
	 * @param src
	 * @return
	 */
	/*public String getPinYinAll(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// �ж��Ƿ�Ϊ�����ַ�
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}*/
	
	/**
	 * �����ʽ��Ϊ00:00
	 * @param l
	 * @return
	 */
	public String formatLongToTimeStr(int l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
 
        second = l / 1000;
 
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute)  + ":"  + getTwoLength(second));
    }
    
    private String getTwoLength(final int data) {
        if(data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    } 
    
    /**
     * ���ݸ���·�����ظ��·��
     */
    public String serchLrc(String songPath) { 
        String lrc = songPath;
        return lrc.substring(0, lrc.lastIndexOf(".")).trim() + ".lrc".trim(); 
    } 
    
    
    /**
	 * �ж��Ƿ����㹻�Ŀռ乩����
	 * 
	 * @param downloadSize
	 * @return
	 */
	@SuppressLint("NewApi")
	public boolean isEnoughForDownload(String dirPath, long downloadSize) {
		StatFs statFs = new StatFs(dirPath);
		Log.e(tag, "���ã�" + (statFs.getFreeBytes() / 1024 / 1024) + "M");
		// sd��������
		int blockCounts = statFs.getBlockCount();
		// Log.e("ray", "blockCounts" + blockCounts);
		// sd�����÷�����
		int avCounts = statFs.getAvailableBlocks();
		// Log.e("ray", "avCounts" + avCounts);
		// һ���������Ĵ�С
		long blockSize = statFs.getBlockSize();
		// Log.e("ray", "blockSize" + blockSize);
		// sd�����ÿռ�
		long spaceLeft = avCounts * blockSize;
		Log.e("ray", "spaceLeft" + spaceLeft);
		Log.e("ray", "downloadSize" + downloadSize);
		if (spaceLeft < downloadSize) {
			return false;
		}
		return true;
	}
	
	/**
	 * ȥ��Ƶ��timeS���֡����
	 * @param videoPath
	 * @param time
	 */
	@SuppressLint("NewApi")
	public Bitmap getBitmapsFromVideo(String videoPath, int timeS) {
		Bitmap result = null;
		try{
			MediaMetadataRetriever retriever = new MediaMetadataRetriever();
			retriever.setDataSource(videoPath);
			// ȡ����Ƶ�ĳ���(��λΪ����)
			String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
			// ȡ����Ƶ�ĳ���(��λΪ��)
			int seconds = Integer.valueOf(time) / 1000;
			// �õ�ÿһ��ʱ�̵�bitmap�����һ��,�ڶ���
			if(timeS > seconds){
				L.w(tag, "��ȡ��ʱ�������Ƶ��ʱ��");
				return null;
			}
			result = retriever.getFrameAtTime(timeS * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result; 
	}
	
	
	/**
	 * @Description ��ȡר������
	 * ʹ�ÿ�Դ���android �ṩ����
	 * android��20ms ��Դ�⻨70hs
	 * @param filePath �ļ�·����like XXX/XXX/XX.mp3
	 * @return ר������bitmap
	 */
	@SuppressLint("NewApi")
	public Bitmap createAlbumArt(final String filePath) {
		Utils.getInstance().startTime("��ȡר������");
//		L.e(tag, "��ȡר�����棺" + filePath);
		//---- ʹ�ÿ�Դ��ȡ
		/*Bitmap bitmap = null;
		Mp3File mp3file;
		try {
			mp3file = new Mp3File(filePath);
			if (mp3file.hasId3v2Tag()) {
				ID3v2 id3v2Tag = mp3file.getId3v2Tag();
				byte[] imageData = id3v2Tag.getAlbumImage();
				if (imageData != null) {
					// String mimeType = id3v2Tag.getAlbumImageMimeType();
					// // Write image to file - can determine appropriate file
					// extension from the mime type
					// RandomAccessFile file = new
					// RandomAccessFile("album-artwork", "rw");
					// file.write(data);
					// file.close();
					bitmap = BitmapFactory.decodeByteArray(imageData, 0,
							imageData.length);
					L.e(tag, "���ר��ͼƬ");
				}
				L.e(tag, "û��ר��ͼƬ");
			} else {
				L.e(tag, "������Id3v2Tag");
			}
		
		//ʹ��android�����
		} catch (Exception e) {
			// TODO Auto-generated catch block
			L.e(tag, "����ר��ͼƬ�쳣");
			e.printStackTrace();
		}*/
		//----
		
	    Bitmap bitmap = null;
	    //�ܹ���ȡ��ý���ļ�Ԫ���ݵ���
	    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	    try {
	        retriever.setDataSource(filePath); //��������Դ
	        byte[] embedPic = retriever.getEmbeddedPicture(); //�õ��ֽ�������
	        if(embedPic == null){
	        	return null;
	        }
	        bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //ת��ΪͼƬ
	    } catch (Exception e) {
	        e.printStackTrace();
	    	L.e(tag, "����ͼ�����쳣��" + filePath);
	    } finally {
	        try {
	            retriever.release();
	        } catch (Exception e2) {
	        	L.e(tag, "����ͼ�����쳣 �ͷ�ʧ�ܣ�" + filePath);
	            e2.printStackTrace();
	        }
	    }
	    Utils.getInstance().endUseTime("��ȡר������");
	    return bitmap;
	}
   
	/**
	 * Drawable �� Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(

		drawable.getIntrinsicWidth(),

		drawable.getIntrinsicHeight(),

		drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		// canvas.setBitmap(bitmap);

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());

		drawable.draw(canvas);

		return bitmap;

	}
	
	/**
	 * ��ø�˹������bitmap���������ͷ�,һ��ͼƬֻ��ȡһ�ţ�Ч����ʹ��alpha����
	 * @param photoScale	ͼƬ��С����			���鷶Χ 30 ~5
	 * @param radius		Բ�뾶��ģ���̶�		���鷶Χ 2 ~ 20
	 */
	/*public Bitmap blurBitmap(Bitmap bit, int photoScale, int radius){
		return BlurUtils.getInstance().getBlurPhoto(bit, radius, radius);
	}*/
	
	public Bitmap small(Bitmap bitmap, int blurScale) {
		Matrix matrix = new Matrix();
		matrix.postScale(1.0f / blurScale, 1.0f / blurScale); // ���Ϳ�Ŵ���С�ı���
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}
	
	/**
	 * ͼƬ��ɫ����
	 */
	public Bitmap getGrayBitmap(Bitmap mBitmap) {
		Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(mGrayBitmap);
		Paint mPaint = new Paint();

		// ������ɫ�任����
		ColorMatrix mColorMatrix = new ColorMatrix();
		// ���ûҶ�Ӱ�췶Χ
		mColorMatrix.setSaturation(0);
		// ������ɫ���˾���
		ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(
				mColorMatrix);
		// ���û��ʵ���ɫ���˾���
		mPaint.setColorFilter(mColorFilter);
		// ʹ�ô����Ļ��ʻ���ͼ��
		mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);
		return mGrayBitmap;
	}
	
	/**
	 * ��ȡ״̬���߶�
	 * @return
	 */
	public int getStatusH(){
		return 30;
	}
	
	/**
	 * ��������߶�Ϊ״̬���߶�
	 */
	public void updateViewHToStatusH(View view){
		
		ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) view.getLayoutParams(); // ȡ�ؼ�mGrid��ǰ�Ĳ��ֲ���
		linearParams.height = Utils.getInstance().getStatusH();// ���ؼ��ĸ�ǿ�����75����
		view.setLayoutParams(linearParams); // ʹ���úõĲ��ֲ���Ӧ�õ��ؼ�mGrid2
	}
	
	/**
	 * �����ַ���Сsingle�ַ���
	 * ����ʱ����Բ���
	 */
	public String getSingleLineStr(TextView textView, String str){
    	if(str == null || str.length() == 0 || textView.getMeasuredWidth() == 0){
    		return str;
    	}
    	int width = textView.getMeasuredWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
    	float textSize = textView.getTextSize();
    	width = (int) (width - textSize * 3);		//Ԥ������
    	int tempW = 0;
    	int index = 0;
    	String result = str;
    	for(index = 0; index < str.length(); index++){
    		tempW += getCharDarwSize(str.substring(index, index + 1), textSize)[0];
    		if(tempW >= width){
    			result = str.substring(0, index) + "...";
    			break;
    		}
    	}
//    	L.e(tag, "index = " + index + " maxwidth = " + width);
    	return result;
	}
	
	Paint pFont;
	Rect rect;
	/**
	 * ���ؿ��
	 * @param text		�ı�
	 * @param textSize	���ִ�С
	 * @return
	 */
	public int[] getCharDarwSize(String text, float textSize){
		if(text == null){
			return new int[]{0, 0};
		}
		if(pFont == null){
			pFont = new Paint();
			rect = new Rect();
		}
    	pFont.setTextSize(textSize);
    	pFont.getTextBounds(text, 0, text.length(), rect);
//    	L.e(tag, "rect.width() = " + rect.width());
    	return new int[]{rect.width(), rect.height()};
	}
	
	
}
