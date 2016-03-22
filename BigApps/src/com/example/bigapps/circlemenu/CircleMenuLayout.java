/**
 * 
 */
package com.example.bigapps.circlemenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigapps.R;

/**
 * @author YC
 * @time 2016-3-21 ����11:19:11
 */
@SuppressLint("NewApi")
public class CircleMenuLayout extends ViewGroup {

	private static final String tag = "CircleMenuLayout";
	
	/**
	 *  ֱ��
	 */
	private int mRadius;
	
	/**
	 * �ϡ���ƫ��(��Ҫ������)
	 */
	private int mTopPadding;
	private int mLeftPadding;
	/**
	 *  ����menu item�ĸ���������Ƕ�
	 */
	private	float mPerItemAngle;
	
	/**
	 * ���������ڱ߾�,����padding���ԣ�����߾����øñ���
	 */
	private float mPadding;

	/**
	 * ��������child item��Ĭ�ϳߴ�
	 */
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	/**
	 * �˵�������child��Ĭ�ϳߴ�
	 */
	private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
	/**
	 * ���������ڱ߾�,����padding���ԣ�����߾����øñ��������ⲿ�ľ���
	 */
	private static final float RADIO_PADDING_LAYOUT = 0 / 12f;
	/**
	 * ���ڼ��㿪ʼ�Ƕ�
	 */
	private double mStartAngle = 0;
	
	/**
	 * ������ʾ�Ŀ�ʼ�Ƕ�
	 */
	private double mStartAngleIndex = 0;

	/**
	 * ����ʾ�Ƕ�
	 */
	private double mTotalAngle = 0;
	
	/**
	 * ��С������ֵ
	 */
	private static final float MIN_SCALE = 0.6f;
	/**
	 * �˵��ĸ���
	 */
	private int mMenuItemCount;

	/**
	 * �˵�����ı�
	 */
	private String[] mItemTexts;
	/**
	 * �˵����ͼ��
	 */
	private int[] mItemImgs;

	
	/**
	 * @param context
	 * @param attrs
	 */
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//����xml�е�padding�Ĳ���
		setPadding(0, 0, 0, 0);
	}

	/**
	 * ���ò˵�����
	 * ���ò˵���Ŀ��ͼ����ı�
	 * @param resIds ͼ��
	 * @param texts	�ı�
	 * @param startAngle ��ʼ�Ƕȣ�����ֵΪ��-90��90������Խ�ӽ�90Ч��Խ���ã�
	 * @param radio	�뾶 ��Ҫ����startAngle����
	 */
	public void setMenuAttr(int[] resIds, String[] texts, float startAngle, int radio) {
		mItemImgs = resIds;
		mItemTexts = texts;
		mRadius = radio;
		mStartAngleIndex = startAngle;
		mStartAngle = mStartAngleIndex;
		mTotalAngle = 180 - 2*startAngle;
		// �������
		if (resIds == null && texts == null) {
			throw new IllegalArgumentException("�˵����ı���ͼƬ����������һ");
		}

		// ��ʼ��mMenuCount
		mMenuItemCount = resIds == null ? texts.length : resIds.length;

		if (resIds != null && texts != null) {
			mMenuItemCount = Math.min(resIds.length, texts.length);
		}

		mPerItemAngle = (float) (mTotalAngle/(mMenuItemCount-1));
		addMenuItems();

	}

	/**
	 * ��Ӳ˵���
	 */
	private void addMenuItems() {
		LayoutInflater mInflater = LayoutInflater.from(getContext());

		/**
		 * �����û����õĲ�������ʼ��view
		 */
		for (int i = 0; i < mMenuItemCount; i++) {
			final int j = i;
			View view = mInflater.inflate(R.layout.circle_menu_item, this,
					false);
			ImageView iv = (ImageView) view
					.findViewById(R.id.id_circle_menu_item_image);
			TextView tv = (TextView) view
					.findViewById(R.id.id_circle_menu_item_text);

			if (iv != null) {
				iv.setVisibility(View.VISIBLE);
				iv.setImageResource(mItemImgs[i]);
				iv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						if (mOnMenuItemClickListener != null) {
							mOnMenuItemClickListener.itemClick(v, j);
						}
					}
				});
			}
			if (tv != null) {
				tv.setVisibility(View.VISIBLE);
				tv.setText(mItemTexts[i]);
			}

			// ���view��������
			addView(view);
		}
	}

	/**
	 * MenuItem�ĵ���¼��ӿ�
	 * 
	 */
	public interface OnMenuItemClickListener {
		void itemClick(View view, int pos);
	}

	/**
	 * MenuItem�ĵ���¼��ӿ�
	 */
	private OnMenuItemClickListener mOnMenuItemClickListener;

	/**
	 * ����MenuItem�ĵ���¼��ӿ�
	 * 
	 * @param mOnMenuItemClickListener
	 */
	public void setOnMenuItemClickListener(
			OnMenuItemClickListener mOnMenuItemClickListener) {
		this.mOnMenuItemClickListener = mOnMenuItemClickListener;
	}

	/**
	 * ���Ĭ�ϸ�layout�ĳߴ�
	 * 
	 * @return
	 */
	private int getDefaultWidth() {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
	}

/*	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int resWidth = 0;
		int resHeight = 0;
		Log.i(tag, "onMeasure");
		*//**
		 * ���ݴ���Ĳ������ֱ��ȡ����ģʽ�Ͳ���ֵ
		 *//*
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		*//**
		 * �������߸ߵĲ���ģʽ�Ǿ�ȷֵ
		 *//*
		if (widthMode != MeasureSpec.EXACTLY
				|| heightMode != MeasureSpec.EXACTLY) {
			// ��Ҫ����Ϊ����ͼ�ĸ߶�
			resWidth = getSuggestedMinimumWidth();
			// ���δ���ñ���ͼƬ��������Ϊ��Ļ��ߵ�Ĭ��ֵ
			resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

			resHeight = getSuggestedMinimumHeight();
			// ���δ���ñ���ͼƬ��������Ϊ��Ļ��ߵ�Ĭ��ֵ
			resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
			resWidth = (int) (Math.cos(mStartAngleIndex)*2);
			resHeight = (int) Math.sin(mStartAngleIndex);
		} else {
			// ���������Ϊ��ȷֵ����ֱ��ȡСֵ��
//			resWidth = resHeight = Math.min(width, height);
			resWidth = width;
			resHeight = height;
		}

		// ����view��w,h
		setMeasuredDimension(resWidth, resHeight);

		// ��ð뾶
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
		// menu item����
		final int count = getChildCount();
		// menu item�ߴ�
		int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// menu item����ģʽ
		int childMode = MeasureSpec.EXACTLY;

		// ��������
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child.getVisibility() == GONE) {
				continue;
			}

			// ����menu item�ĳߴ磻�Լ������úõ�ģʽ��ȥ��item���в���
			int makeMeasureSpec = -1;

			if (child.getId() == R.id.id_circle_menu_item_center) {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
						childMode);
			} else {
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
						childMode);
			}
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}

		mPadding = RADIO_PADDING_LAYOUT * mRadius;
	}*/

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int resWidth = 0;
		int resHeight = 0;
//		Log.i(tag, "onMeasure");
		/**
		 * ���ݴ���Ĳ������ֱ��ȡ����ģʽ�Ͳ���ֵ
		 */
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//		Log.i(tag, "onMeasure width = " +width +", widthMode = " +widthMode + ",height = " + height + ", heightMode = " + heightMode);
		/**
		 * �������߸ߵĲ���ģʽ�Ǿ�ȷֵ
		 */
		if (widthMode != MeasureSpec.EXACTLY
				|| heightMode != MeasureSpec.EXACTLY) 
		{
			int childWidth = (int) (mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
			mLeftPadding = childWidth/2;
			if (mStartAngleIndex < 0)
			{
				resWidth = 2*mRadius;
			}
			else
			{
				resWidth = (int) (mRadius*Math.cos(mStartAngleIndex*Math.PI/180)*2);
			}
			//������Ҹ����а��childview����
			resWidth += 2*mLeftPadding;
			//�߶Ƚ���mPerItemAngle�߶ȺͰ��childview����
			mTopPadding = Math.abs((int) (mRadius*Math.sin(mPerItemAngle*Math.PI/180))) + childWidth/2;
			resHeight = (int) (mRadius - mRadius*Math.sin(mStartAngleIndex*Math.PI/180));
			resHeight += mTopPadding;
		} else 
		{
			resWidth = width;
			resHeight = height;
		}
//		Log.i(tag, "onMeasure resWidth = " +resWidth +", resHeight = " +resHeight);

		// ����view��w,h
		setMeasuredDimension(resWidth, resHeight);

		// menu item����
		final int count = getChildCount();
		// menu item�ߴ�
		int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// menu item����ģʽ
		int childMode = MeasureSpec.EXACTLY;

		// ��������
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child.getVisibility() == GONE) {
				continue;
			}

			// ����menu item�ĳߴ磻�Լ������úõ�ģʽ��ȥ��item���в���
			int makeMeasureSpec = -1;

			if (child.getId() == R.id.id_circle_menu_item_center) 
			{
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (mRadius * RADIO_DEFAULT_CENTERITEM_DIMENSION),
						childMode);
			} else 
			{
				makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
						childMode);
			}
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}

		mPadding = RADIO_PADDING_LAYOUT * mRadius;
	}
	
/*	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int angle = 180;
		
		int layoutRadius = mRadius;

		// Laying out the child views
		final int childCount = getChildCount();

		int left, top;
		// menu item �ĳߴ�
		int cWidth = (int) (layoutRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// ����menu item�ĸ���������Ƕ�
		mAngleDelay = angle / (getChildCount()-1);
		
		Log.i(tag, "getCount = " + childCount + ", per angle = " + mAngleDelay);
		// ����ȥ����menuitem��λ��
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE) {
				continue;
			}

//			mStartAngle /= 1;
//			if (mStartAngle > angle)
//			{
//				mStartAngle %= angle;
//			}
			mStartAngle %= angle+mAngleDelay;
			Log.i(tag, "mStartAngle = " + mStartAngle);

			// ���㣬���ĵ㵽menu item���ĵľ���
			float tmp = layoutRadius / 2f - cWidth / 2 - mPadding;

			// tmp cosa ��menu item���ĵ�ĺ�����
			left = layoutRadius/ 2
					+ (int) Math.round(tmp
							* Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);
			// tmp sina ��menu item��������
			top = layoutRadius/ 2
					+ (int) Math.round(tmp
							* Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth);

			float level = getLevel(mStartAngle);
			Log.i(tag, "getLevel = " + level);
			child.layout(left, top, left + cWidth, top + cWidth);
			child.setScaleX(level);
			child.setScaleY(level);
			child.setAlpha(level);
			// ���ӳߴ�
			mStartAngle += mAngleDelay;
		}

	}*/
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {


		// Laying out the child views
		final int childCount = getChildCount();

		int left, top;
		// menu item �ĳߴ�
		int cWidth = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
		// ����menu item�ĸ���������Ƕ�
		
		// ����ȥ����menuitem��λ��
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child.getId() == R.id.id_circle_menu_item_center)
				continue;

			if (child.getVisibility() == GONE) {
				continue;
			}

			if (mStartAngle >= mTotalAngle + mStartAngleIndex + mPerItemAngle)
			{
				mStartAngle = mStartAngleIndex+mStartAngle%(mTotalAngle + mStartAngleIndex + mPerItemAngle);
			}
//			Log.i(tag, "onLayout mStartAngle = " + mStartAngle);

			// ���㣬���ĵ㵽menu item���ĵľ���
			float tmp = mRadius - cWidth / 2 - mPadding;

			// tmp cosa ��menu item���ĵ�ĺ�����
			left = mLeftPadding*2 + (int) (mRadius*Math.cos(mStartAngleIndex*Math.PI/180)
					+ (int) Math.round(tmp
							* Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth));
			// tmp sina ��menu item��������
			top = mTopPadding + (int) (-mRadius*Math.sin(mStartAngleIndex*Math.PI/180)
					+(int) Math.round(tmp
							* Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f
							* cWidth));

//			Log.i(tag, "onLayout left = " + left + ", top = " + top + ", cWidth = " + cWidth);
			float level = getLevel(mStartAngle);
//			Log.i(tag, "getLevel = " + level);
			child.layout(left, top, left + cWidth, top + cWidth);
			child.setScaleX(level);
			child.setScaleY(level);
			child.setAlpha(level);
			// ���ӳߴ�
			mStartAngle += mPerItemAngle;
		}

	}

	
	/**
	 * ͨ���Ƕ�����ȡ���Ż�Alpha����
	 * @param startAngle
	 * @return
	 */
	private float getLevel(double startAngle) {
		float result = 0.0f;
		if (startAngle < mStartAngleIndex)//[-, mStartAngleIndex]
		{
//			result = MIN_SCALE;
			result = 0;	//0Ϊ���ɼ�
		}
		else if (startAngle <= mStartAngleIndex + mTotalAngle/2)//[mStartAngleIndex, mStartAngleIndex + mTotalAngle/2]
		{
			result =  (float) (MIN_SCALE + (startAngle-mStartAngleIndex)/(mTotalAngle/2)*(1-MIN_SCALE));
		}else if (startAngle <= mStartAngleIndex + mTotalAngle)//[mStartAngleIndex + mTotalAngle/2, mStartAngleIndex + mTotalAngle]
		{
			result =  1.0f - (float)((startAngle-(mStartAngleIndex + mTotalAngle/2))/(mTotalAngle/2))*(1-MIN_SCALE);
		}
		else
		{
			result = 0;
		}
		return result;
	}

	/**
	 * ��ⰴ�µ�̧��ʱ��ת�ĽǶ�
	 */
	private float mTmpAngle;
	/**
	 * ��ⰴ�µ�̧��ʱʹ�õ�ʱ��
	 */
	private long mDownTime;
	/**
	 * ��¼��һ�ε�x��y����
	 */
	private float mLastX;
	private float mLastY;
	/**
	 * �ж��Ƿ������Զ�����
	 */
	private boolean isFling;
	/**
	 * �Զ�������Runnable
	 */
	private AutoFlingRunnable mFlingRunnable;
	
	/**
	 * ���˴�����ֹֹͣʱ����̫��
	 */
	private boolean isMoveBack;
	private MoveBackRunnable mMoveBackRunnable;
	
	/**
	 * ��ÿ���ƶ��Ƕȴﵽ��ֵʱ����Ϊ�ǿ����ƶ�
	 */
	private static final int FLINGABLE_VALUE = 100;
	/**
	 * ��ÿ���ƶ��Ƕȴﵽ��ֵʱ����Ϊ�ǿ����ƶ�
	 */
	private int mFlingableValue = FLINGABLE_VALUE;
	/**
	 * ����ƶ��Ƕȴﵽ��ֵ�������ε��
	 */
	private static final int NOCLICK_VALUE = 3;
	/**
	 * ���ݴ�����λ�ã�����Ƕ�
	 * 
	 * @param xTouch
	 * @param yTouch
	 * @return
	 */
	private float getAngle(float xTouch, float yTouch)
	{
		double x = xTouch - (mRadius / 2d);
		double y = yTouch - (mRadius / 2d);
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}
	
	/**
	 * ���ݵ�ǰλ�ü�������
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int getQuadrant(float x, float y)
	{
		int tmpX = (int) (x - mRadius / 2);
		int tmpY = (int) (y - mRadius / 2);
		if (tmpX >= 0)
		{
			return tmpY >= 0 ? 4 : 1;
		} else
		{
			return tmpY >= 0 ? 3 : 2;
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN://����
			mLastX = x;
			mLastY = y;
			mDownTime = SystemClock.elapsedRealtime();
			mTmpAngle = 0;
			if (isMoveBack)// �����ǰ�Ѿ��ڻ���  
			{
				removeCallbacks(mMoveBackRunnable);
				isMoveBack = false;
			}
			if (isFling) // �����ǰ�Ѿ��ڹ���  
			{
				removeCallbacks(mFlingRunnable);
				isFling = false;
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			/*float start = getAngle(mLastX, mLastY);//��ʼʱ�Ƕ�
			float end = getAngle(x, y);	//��ǰ�Ƕ�
	         Log.e(tag, "start = " + start + " , end =" + end);  
            // �����һ�������ޣ���ֱ��end-start���Ƕ�ֵ������ֵ  
            if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4)  
            {  
                mStartAngle += end - start;  
                mTmpAngle += end - start;  
            } else  
            // ���������ޣ�ɫ�Ƕ�ֵ�Ǹ�ֵ  
            {  
                mStartAngle += start - end;  
                mTmpAngle += start - end;  
            }  */
			float angle = (float) ((mLastX - x)/(2*mRadius*Math.cos(mStartAngleIndex*Math.PI/180))*180);
			mStartAngle += angle;  
            mTmpAngle += angle; 
//            Log.i(tag, "move angle = " + angle);
            // ���²���  
            requestLayout();  
  
            mLastX = x;  
            mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
			 // ���㣬ÿ���ƶ��ĽǶ�  
            float anglePerSecond = mTmpAngle * 1000/(SystemClock.elapsedRealtime() - mDownTime);  
//            Log.i(tag, "11111anglePerSecond up" + anglePerSecond);
            // ����ﵽ��ֵ��Ϊ�ǿ����ƶ�  
            if (Math.abs(anglePerSecond) > mFlingableValue && !isFling)  
            {  
                // postһ������ȥ�Զ�����  
                post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));  
  
                return true;  
            }  
            else
            {
    			double stopAngle = getStopAngle(mStartAngle);
//				Log.i(tag, "222222getStopAngle = " + stopAngle + ", mStartAngle = " + mStartAngle);
    			isMoveBack = true;
    			post(mMoveBackRunnable = new MoveBackRunnable(stopAngle - mStartAngle, stopAngle));

//				requestLayout();
            }
  
            // �����ǰ��ת�Ƕȳ���NOCLICK_VALUE���ε��  
            if (Math.abs(mTmpAngle) > NOCLICK_VALUE)  
            {  
                return true;  
            }  
            break;
		default:
			break;
		}
		super.dispatchTouchEvent(ev);//ִ��һ�£���Ȼ��View��ȡ��������¼�
		return true;
	}
	
	/**
	 * ͨ��ֹͣʱ��ʼ�ĽǶ����Ӧ��ֹͣ��λ��
	 * @param mStartAngle
	 * @return
	 */
	private double getStopAngle(double startAngle) {
		
		int position = (int) ((startAngle - mStartAngleIndex)/mPerItemAngle);
		int tmp = (int) ((startAngle - mStartAngleIndex)%mPerItemAngle);
		if (tmp > mPerItemAngle/2)
		{
			position++;
		}
		
		return position*mPerItemAngle + mStartAngleIndex;
	}
	
	/**
	 * �Զ�����������
	 */
	private class AutoFlingRunnable implements Runnable
	{

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity)
		{
			this.angelPerSecond = velocity;
		}

		public void run()
		{
			// ���С��20,�����
//			Log.i(tag, "11111anglePerSecond run" + angelPerSecond);
			if ((int) Math.abs(angelPerSecond) < 20)
			{
				isFling = false;
//            	mStartAngle = getStopAngle(mStartAngle);
//            	requestLayout();
				double stopAngle = getStopAngle(mStartAngle);
//				Log.i(tag, "222222getStopAngle = " + stopAngle + ", mStartAngle = " + mStartAngle);
				isMoveBack = true;
				post(mMoveBackRunnable = new MoveBackRunnable(stopAngle - mStartAngle, stopAngle));
				return;
			}
			isFling = true;
			// ���ϸı�mStartAngle�����������/30Ϊ�˱������̫��
			mStartAngle += (angelPerSecond / 30);
			// �𽥼�С���ֵ
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// ���²���
			requestLayout();
		}
	}
	
	/**
	 * ��������
	 * @author YC
	 * @time 2016-3-22 ����6:01:36
	 */
	private class MoveBackRunnable implements Runnable {
		private static final int BACKUP_ANGLE = 1;
		private double mOffsetAngle;
		private double mEndAngle;
		public MoveBackRunnable(double offsetAngle, double endAngle){
			mOffsetAngle = offsetAngle;
			mEndAngle = endAngle;
		}
		public void run() {
//			Log.i(tag, "222222 run mStartAngle = " + mStartAngle + ", mEndAngle = " + mEndAngle + ", mOffsetAngle = " + mOffsetAngle);
			if (Math.abs(mStartAngle - mEndAngle) < BACKUP_ANGLE)
			{
				mStartAngle = mEndAngle;
				requestLayout();
				isMoveBack = false;
				return;
			}
			
			if (mOffsetAngle > 0)//˳ʱ��
			{
				mStartAngle += BACKUP_ANGLE;
				mOffsetAngle -= BACKUP_ANGLE;
			}
			else
			{
				mStartAngle -= BACKUP_ANGLE;
				mOffsetAngle += BACKUP_ANGLE;
			}
			postDelayed(this, 50);
			// ���²���
			requestLayout();
			
		}
	}


}
