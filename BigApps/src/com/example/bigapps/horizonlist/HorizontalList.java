/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.HashMap;
import java.util.Map;

import com.example.bigapps.R;
import com.zh.uitls.Utils;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author YC
 * @time 2016-4-1 ����9:36:07
 */
public class HorizontalList extends ViewGroup {

	private String TAG = getClass().getSimpleName();

	/**Item֮��ļ��*/
	private int mItemPadding = 5;
	/**��ʾ��Item����*/
	private int mShowItemNum = 5;
	/**�м�һ���Ŵ��λ��*/
	private final float mMidScale = 1.5f;
	/**������*/
	private BaseAdapter mAdapter;
	
	/**Item�Ŀ��*/
	private int mItemWidth, mItemHeight;
	
	/**����View�Ŀ��*/
	private int mWidth, mHeight;
	
	/**�м�Ŵ�Item�����������벼���м��ƫ��*/
	private int mMidSelItemIndex = 0, mMidItemOffset = 0;
	
	
	/**����item����View�ı�*/
    private Map<View, Integer> mPosMap = new HashMap<View, Integer>();
	
	
	public HorizontalList(Context context) {
		this(context, null);
	}
	public HorizontalList(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public HorizontalList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//����xml�е�padding�Ĳ���
		setPadding(0, 0, 0, 0);
	}
	
	public interface ItemOnClickListener{
		  void onItemClick(AdapterView<?> parent, View view, int position);
	}
	private ItemOnClickListener mOnItemClickListener;
	/** ����Item�ĵ���¼��ӿ� */
	public void setItemOnClickListener(ItemOnClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}
	
	public interface ItemPlayPauseOnClickListener{
		  void onItemPlayPause(View view, int position);
	}
	private ItemPlayPauseOnClickListener mItemPlayPauseOnClickListener;
	/** ����Item�ĵ���¼��ӿ� */
	public void setItemPlayPauseOnClickListener(ItemPlayPauseOnClickListener itemPlayPauseOnClickListener) {
		mItemPlayPauseOnClickListener = itemPlayPauseOnClickListener;
	}
	
	public interface CurSelItemChangeCallback{
		void onItemChange(int oldItem, int curItem);
	}
	
	private CurSelItemChangeCallback mCurSelItemChangeCallback;
	public void setCurSelItemChangeCallback(CurSelItemChangeCallback callback)
	{
		mCurSelItemChangeCallback = callback;
	}
	
	public void setAdapter(BaseAdapter adapter) {
		//TODO setAdapter
		Log.i(TAG, "setAdapter size = " + adapter.getCount());
		mAdapter = adapter;
		addItems();
//		getItemWH();
		mMidSelItemIndex = getInitMideSelItemIndex();
		mMidItemOffset = 0;
		requestLayout();
	}
	
	/** ���ò�����ͣ״̬������״̬ */
	public void setPlayPauseState(int[] items, boolean[] states)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			boolean bSet = false;
			ImageView playpause = (ImageView) getChildAt(i).findViewById(R.id.ItemPlayPause);
			if (items != null && states != null)
			{
				for (int j = 0; j < items.length; j++)
				{
					if (i == items[j])
					{
						playpause.setVisibility(View.VISIBLE);
	//					playpause.setSelected(states[j]);
						if (states[j])
						{
							playpause.setImageResource(R.drawable.item_play_selector);
						}
						else
						{
							playpause.setImageResource(R.drawable.item_pause_selector);	
						}
						bSet = true;
						break;
					}
				}
			}
			
			if (!bSet)
			{
				playpause.setVisibility(View.GONE);
			}
		}
		Log.i(TAG, "setPlayPauseState");
	}
	
	/** ��ȡĬ�ϵ��м���ʾItem��Index*/
	private int getInitMideSelItemIndex() {
		return mAdapter.getCount() < mShowItemNum/2 ? 0 : mShowItemNum/2;
	}
	
	/** ��ȡItem�Ŀ�� */
	private void getItemWH() {
		if (mItemWidth == 0 || mItemHeight == 0)
		{
			/**��ΪItemΪһ����С������ֻ����һ��*/
			Log.i(TAG , "getChildCount = " + getChildCount());
			if (getChildCount() > 0)
			{
				View view = getChildAt(0);
				mItemWidth = view.getMeasuredWidth();
				mItemHeight = view.getMeasuredHeight();
				Log.i(TAG , "getItem w = " + mItemWidth + ", h = " + mItemHeight);
			}
		}
	}
	/** ���Item */
	private void addItems() {
		removeAllViews();
		for (int i = 0; i < mAdapter.getCount(); i++)
		{
			final int j = i;
			View view = mAdapter.getView(i, null, null);
			addView(view);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mOnItemClickListener != null)
					{
						mOnItemClickListener.onItemClick(null, v, j);
					}
				}
			});
			
			view.findViewById(R.id.ItemPlayPause).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mItemPlayPauseOnClickListener != null)
					{
						mItemPlayPauseOnClickListener.onItemPlayPause(v, j);
					}
				}
			});
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//TODO onMeasure
		Log.i(TAG, "onMeasure");
		Utils.getInstance().startTime("onMeasure");
		measureChild();
		getItemWH();
		
		mWidth = 0;
		mHeight = 0;
		
		mWidth = (int) (mItemWidth * (mShowItemNum - 1) + mItemWidth*mMidScale + mItemPadding * (mShowItemNum+1));
		mHeight = (int) (mItemHeight * mMidScale);
		Log.i(TAG, "onMeasure mWidth = " + mWidth + ", mHeight = " + mHeight);
		setMeasuredDimension(mWidth, mHeight);
	}
	
	/**���������View�������ȡ����wh*/
	private void measureChild() {
		for (int i = 0 ; i < getChildCount() ; i++){
			View childView = getChildAt(i);
			childView.measure(0, 0);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//TODO onLayout
//		Log.i(TAG, "onLayout mMidItemOffset = " + mMidItemOffset);
		
		final float midItemScale = getScaleByOffset(mMidItemOffset);
		/**�����Ԫ�طŴ���*/
		final float leftRightScale = 1.0f + mMidScale - midItemScale;	
//		Log.i(TAG, "onLayout midItemScale = " + midItemScale + ", leftRightScale = " + leftRightScale);
		
		int left = 0;
		final int top = mHeight/2 - mItemHeight/2;
		/**����ѡ����*/
		left = mWidth/2 + mMidItemOffset;
		left -= mItemWidth/2;
		
//		Log.i(TAG, "onLayout mMidSelItemIndex = " + mMidSelItemIndex + ", mMidItemOffset = "  + mMidItemOffset);
		View view = getChildAt(mMidSelItemIndex);
//		Log.i(TAG, "onLayout MidItem left = " + left);
//		Log.i(TAG , "onLayout mItemWidth = " + mItemWidth + ", mItemHeight = " + mItemHeight);
		
		view.layout(left, top, left+mItemWidth, top+mItemHeight);
		view.setScaleX(midItemScale);
		view.setScaleY(midItemScale);
		
		/**���������*/
		int index = mMidSelItemIndex-1;
		left = (int) (left - mItemWidth*(midItemScale - 1)/2);
		if (mMidItemOffset <= 0)
		{
			/**�������ȫ��������ʾ��Item*/
			for (int i = 0; i < mShowItemNum/2  && index >= 0; i++,index--)
			{
				left -= (mItemWidth + mItemPadding);
				view = getChildAt(index);
				view.layout(left, top, left+mItemWidth, top+mItemHeight);
				view.setScaleX(1.0f);
				view.setScaleY(1.0f);
//				Log.i(TAG, "onLayout LeftItem " + i + " left = " + left);
			}
		}
		else
		{
			
			if (index >= 0)
			{
				/**������ߵ�һ���Ŵ��Item*/
				left -= (mItemPadding + mItemWidth*leftRightScale - mItemWidth*(leftRightScale - 1.0f)/2);
				view = getChildAt(index);
				view.layout(left, top, left+mItemWidth, top+mItemHeight);
				view.setScaleX(leftRightScale);
				view.setScaleY(leftRightScale);
//				Log.i(TAG, "onLayout LeftScale 1 left = " + left);
				index--;
				left -= (mItemWidth*(leftRightScale - 1.0f)/2);
				/**������ߵڶ�����ʼ��Item*/
				for (int i = 0; i < mShowItemNum/2 && index >= 0; i++,index--)
				{
					left -= (mItemWidth + mItemPadding);
					view = getChildAt(index);
					view.layout(left, top, left+mItemWidth, top+mItemHeight);
					view.setScaleX(1.0f);
					view.setScaleY(1.0f);
//					Log.i(TAG, "onLayout LeftItem " + i + " left = " + left);
				}
			}
		}
		
		/**�����ұ���*/
		index = mMidSelItemIndex + 1;
		left = mWidth/2 + mMidItemOffset;
		left += (mItemWidth*midItemScale)/2;
		if (mMidItemOffset >= 0)
		{
			/**�����ұ�ȫ��������ʾ��Item*/
			for (int i = 0; i < mShowItemNum/2  && index < getChildCount(); i++,index++)
			{
				left += mItemPadding;
				view = getChildAt(index);
				view.layout(left, top, left+mItemWidth, top+mItemHeight);
				view.setScaleX(1.0f);
				view.setScaleY(1.0f);
				left += mItemWidth;
			}
		}
		else
		{
			if (index < getChildCount())
			{
				/**�����ұߵ�һ���Ŵ��Item*/
				left += mItemPadding + mItemWidth*(leftRightScale-1.0f)/2;
				view = getChildAt(index);
				view.layout(left, top, left+mItemWidth, top+mItemHeight);
				view.setScaleX(leftRightScale);
				view.setScaleY(leftRightScale);
				index++;
				left += mItemWidth + mItemWidth*(leftRightScale - 1.0f)/2;
				/**�����ұߵڶ�����ʼ��Item*/
				for (int i = 0; i < mShowItemNum/2  && index < getChildCount(); i++,index++)
				{
					left += mItemPadding;
					view = getChildAt(index);
					view.layout(left, top, left+mItemWidth, top+mItemHeight);
					view.setScaleX(1.0f);
					view.setScaleY(1.0f);
					left += mItemWidth;
				}
			}
		}
		
		showView();
		
		setItemEvent();
		Utils.getInstance().endUseTime("onMeasure");
	}
	
	/**����Item�¼�*/
	private void setItemEvent() {
		for (int i = 0; i < getChildCount(); i++)
		{
			View view = getChildAt(i);
			if (i == mMidSelItemIndex)
			{
				view.setClickable(true);
				view.setSelected(true);
			}
			else
			{
				view.setClickable(false);
				view.setSelected(false);
			}
		}
	}
	/** ��ʾ������Item�� */
	private void showView() {
		int startShowIndex = 0;
		int endShowIndex = 0;
		
		if (mMidItemOffset >= 0)
		{
			startShowIndex = mMidSelItemIndex - (mShowItemNum/2 + 1);
			if (startShowIndex < 0)
			{
				startShowIndex = 0;
			}
			
			endShowIndex = mMidSelItemIndex + mShowItemNum/2;
			if (endShowIndex > getChildCount() - 1)
			{
				endShowIndex = getChildCount() - 1;
			}
		}
		else
		{
			startShowIndex = mMidSelItemIndex - mShowItemNum/2;
			if (startShowIndex < 0)
			{
				startShowIndex = 0;
			}
			
			endShowIndex = mMidSelItemIndex + (mShowItemNum/2 + 1);
			if (endShowIndex > getChildCount() - 1)
			{
				endShowIndex = getChildCount() - 1;
			}
		}
//		Log.i(TAG, "showView start = " + startShowIndex + ", end = " + endShowIndex);
		for (int i = 0; i < getChildCount(); i++)
		{
			if (i >= startShowIndex && i <= endShowIndex)
			{
				getChildAt(i).setVisibility(View.VISIBLE);
			}
			else
			{
				getChildAt(i).setVisibility(View.GONE);
			}
		}
	}
	/**����ƫ��������Ŵ�ֵ*/
	private float getScaleByOffset(int offset) {
		float scale = 1.0f;
		int totalOffset = (int) (mItemWidth + (mMidScale - 1.0f)*mItemWidth + mItemPadding);
		scale = 1.5f - 0.5f * Math.abs(offset)/totalOffset;
		if (scale > 1.5f)
		{
			scale = 1.5f;
		}
		else if (scale < 1.0f)
		{
			scale = 1.0f;
		}
		return scale;
		
	}
	
	private int mLastX = 0;
	/**	��ⰴ�µ�̧��ʱʹ�õ�ʱ��*/
	private long mDownTime = 0;
	private int mDownX = 0;
	/**��ÿ���ƶ��Ƕȴﵽ��ֵʱ����Ϊ�ǿ����ƶ� */
	private final int FLINGABLE_VALUE = 100;
	/**�Ƿ��ڹ���*/
	private boolean mIsFling;
	/**�Զ�������Runnable*/
	private AutoFlingRunnable mFlingRunnable;
	
	/** ���˴�����ֹֹͣʱ����̫�� */
	private boolean isMoveBack;
	private MoveBackRunnable mMoveBackRunnable;
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		//TODO dispatchTouchEvent
		Log.i(TAG, "dispatchTouchEvent action = " + event.getAction() + ", x = " + event.getX());
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			mLastX = (int) event.getX();
			mDownX = (int) event.getX();
			mDownTime = SystemClock.elapsedRealtime();
			if (isMoveBack)// �����ǰ�Ѿ��ڻ���  
			{
				removeCallbacks(mMoveBackRunnable);
				isMoveBack = false;
			}
			if (mIsFling)
			{
				mIsFling = false;
				removeCallbacks(mFlingRunnable);
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int) event.getX();
			mMidItemOffset += (x - mLastX);
			checkOffset();
			checkBorder();
			mLastX = x;
//			Log.i(TAG, "move mOffset = " + mOffset);
			Utils.getInstance().startTime("dispatchTouchEvent startRequest");
			requestLayout();
			Utils.getInstance().endUseTime("dispatchTouchEvent startRequest");
			break;
		case MotionEvent.ACTION_UP:
			float speed = (event.getX() - mDownX)*1000/(SystemClock.elapsedRealtime() - mDownTime);
			Log.i(TAG, "onTouchEvent speed = " + speed);
			if (Math.abs(speed) > FLINGABLE_VALUE && !mIsFling)
			{
				 post(mFlingRunnable = new AutoFlingRunnable(speed));  
	             return true;  
			}
			else
			{
				if (Math.abs(mMidItemOffset) > 0)
				{
					isMoveBack = true;
					post(mMoveBackRunnable = new MoveBackRunnable(getStopOffset(mMidItemOffset)));
				}
			}
			break;
		}
		super.dispatchTouchEvent(event);
		return true;
	}
	
	/**У��ƫ��ֵ,ÿ���޸�mMidItemOffsetʱ��Ҫ����*/
	private void checkOffset() {
		/**������ʶ*/
		final int flag = mMidItemOffset > 0 ? 1: -1;
		final int itemOffset = (int) (mItemWidth + (mMidScale - 1.0f)*mItemWidth/2 + mItemPadding);
//		Log.i(TAG, "checkOffset ++++++++++ mMidItemOffset = " + mMidItemOffset);
		while ( Math.abs(mMidItemOffset) > itemOffset )
		{
			mMidItemOffset = flag * (Math.abs(mMidItemOffset) - itemOffset);
			final int oldItem = mMidSelItemIndex;
			if (flag > 0)
			{
				//TODO LoadPre
				mMidSelItemIndex--;
				if (mMidSelItemIndex < 0)
				{
					mMidSelItemIndex = 0;
				}
			}
			else
			{
				//TODO LoadNext
				mMidSelItemIndex++;
				if (mMidSelItemIndex > getChildCount() - 1)
				{
					mMidSelItemIndex = getChildCount() - 1;
				}
			}
			if (mCurSelItemChangeCallback != null)
			{
				mCurSelItemChangeCallback.onItemChange(oldItem, mMidSelItemIndex);
			}
		}
//		Log.i(TAG, "checkOffset ---------- mMidItemOffset = " + mMidItemOffset);
	}
	
	/**
	 * ���߽磬�����һ�������һ��
	 */
	private boolean checkBorder()
	{
		if ((mMidSelItemIndex == getChildCount() - 1) && mMidItemOffset < 0)
		{
			mMidItemOffset = 0;
			return true;
		}
		
		if ((mMidSelItemIndex == 0) && mMidItemOffset > 0)
		{
			mMidItemOffset = 0;
			return true;
		}
		return false;
	}

	private class AutoFlingRunnable implements Runnable
	{

		private final int STOP_SPEED = 20;
		private float mSpeed;
		

		public AutoFlingRunnable(float speed)
		{
			this.mSpeed = speed;
		}

		public void run()
		{
			if (Math.abs(mSpeed) < STOP_SPEED)
			{
				mIsFling = false;
				isMoveBack = true;
				post(mMoveBackRunnable = new MoveBackRunnable(getStopOffset(mMidItemOffset)));
				return;
			}
			mIsFling = true;
			// ���ϸı�mMidItemOffset�����������/30Ϊ�˱������̫��
			mMidItemOffset += (mSpeed / 30);
			// �𽥼�С���ֵ
			mSpeed /= 1.0666F;
			checkOffset();
			if (checkBorder())
			{
				mIsFling = false;
			}
			else
			{
				postDelayed(this, 30);
			}
			// ���²���
			requestLayout();
		}
	}
	
	/** ��ȡֹͣʱ��Ҫ������ƫ�� */
	private int getStopOffset(int offset) {
		final int ITEM_OFFSET = (int) (mItemWidth + mItemPadding + (mMidScale - 1.0f)*mItemWidth/2);
		
		int result = 0;
		if (Math.abs(offset) > ITEM_OFFSET/2)
		{
			result = (ITEM_OFFSET - Math.abs(offset)%ITEM_OFFSET)*(offset > 0? 1: -1);	
		}
		else
		{
			result = -1*offset;
		}
		return result;
	}
	
	/**
	 * ��������
	 * @author YC
	 * @time 2016-4-5  14:01:36
	 */
	private class MoveBackRunnable implements Runnable {
		private static final int SPEED_DIFF = 2;
		private int mOffset;
		
		/** @param offset ��Ҫƫ�Ƶ�ֵ */
		public MoveBackRunnable(int offset){
			mOffset = offset;
		}
		public void run() {
//			Log.i(tag, "222222 run mStartAngle = " + mStartAngle + ", mEndAngle = " + mEndAngle + ", mOffsetAngle = " + mOffsetAngle);
			if (Math.abs(mOffset) < SPEED_DIFF)
			{
				upsetToNormalItem();
				isMoveBack = false;
				return;
			}
			
			if (mOffset > 0)
			{
				mMidItemOffset += SPEED_DIFF;
				mOffset -= SPEED_DIFF;
			}
			else
			{
				mMidItemOffset -= SPEED_DIFF;
				mOffset += SPEED_DIFF;
			}
			postDelayed(this, 1);
			checkOffset();
			// ���²���
			requestLayout();
		}
	}

	/**�ָ���������ʾ״̬ */
	public void upsetToNormalItem() {
		final int itemOffset = (int) (mItemWidth + (mMidScale - 1.0f)*mItemWidth/2 + mItemPadding);
		final int DIFF = 10;
		final int oldItem = mMidSelItemIndex;
		if ( mMidItemOffset < -1 * itemOffset + DIFF)
		{
			mMidItemOffset = 0;
			//TODO LoadNext
			mMidSelItemIndex++;
			if (mMidSelItemIndex > getChildCount() - 1)
			{
				mMidSelItemIndex = getChildCount() - 1;
			}
			
			if (mCurSelItemChangeCallback != null)
			{
				mCurSelItemChangeCallback.onItemChange(oldItem, mMidSelItemIndex);
			}
		}
		else if ( mMidItemOffset > itemOffset - DIFF)
		{
			mMidItemOffset = 0;
			//TODO LoadPre
			mMidSelItemIndex--;
			if (mMidSelItemIndex < 0)
			{
				mMidSelItemIndex = 0;
			}
			
			if (mCurSelItemChangeCallback != null)
			{
				mCurSelItemChangeCallback.onItemChange(oldItem, mMidSelItemIndex);
			}
		}
		else
		{
			mMidItemOffset = 0;
		}
		requestLayout();
	}
	
	
}
