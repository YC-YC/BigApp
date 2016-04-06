/**
 * 
 */
package com.example.bigapps.horizonlist;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.bigapps.utils.SizeUtils;

/**
 * @author YC
 * @time 2016-3-29 ����11:23:52
 */
public class MyHorizontalScollView extends HorizontalScrollView 
implements OnClickListener {

	private HorizontalScrollViewAdapter mAdapter;
    private OnItemClickListener mOnClickListener;
    private CurrentImageChangeListener mListener;
    /**HorizontalListView�е�LinearLayout*/
    private MyLinearLayout mContainer;
    
    /**��Ļ���*/
    private int mScreenWidth;
    
    /**item�Ŀ��*/
    private int mChildWidth;
    /**item�ĸ߶�*/
    private int mChildHeight;
    
    /**ÿ��item����*/
    private int mCountOneScreen;
    
    /**��һ����ʾitem���±�*/
    private int mFirstItemIndex;
    
    /**���һ�ż��ص�item���±�*/
    private int mLastItemIndex;
  
    
    /**����item����View�ı�*/
    private Map<View, Integer> mPosMap = new HashMap<View, Integer>();
	private String TAG = getClass().getSimpleName();
	
    public interface OnItemClickListener  
    {  
    	void onClick(View view, int pos);  
    }  
    
    /** ͼƬ����ʱ�Ļص��ӿ� */  
    public interface CurrentImageChangeListener  
    {  
        void onCurrentImgChanged(int position, View viewIndicator);  
    }
    
	public MyHorizontalScollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public MyHorizontalScollView(Context context) {
		this(context, null);
	}
	public MyHorizontalScollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mScreenWidth = SizeUtils.getScreenMatrics(context).widthPixels;
	}
	
/*	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}*/
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mContainer = (MyLinearLayout) getChildAt(0);
	}
	public void setAdapter(HorizontalScrollViewAdapter adapter){
		mAdapter = adapter;
		mContainer = (MyLinearLayout) getChildAt(0);
		
		//��ӵ�һ��View
		final View view  = adapter.getView(0, null, mContainer);
		mContainer.addView(view);
		
		if (mChildWidth == 0 && mChildHeight == 0)
		{
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			
			view.measure(w, h);
			
			mChildWidth = view.getMeasuredWidth();
			mChildHeight = view.getMeasuredHeight();
			Log.i(TAG , "setAdapter getChileWidth = " + mChildWidth + ", mChildHeight = "  + mChildHeight);
			//���ض������
			mCountOneScreen = mScreenWidth/mChildWidth + 2;
		}
		
		initFirstScreenChildern(mCountOneScreen);
	}
	
	/**
	 * ��ʼ����һ��Ļ��Ԫ��
	 * @param mCountOneScreen2
	 */
	private void initFirstScreenChildern(int countOneScreen) {
		mContainer = (MyLinearLayout) getChildAt(0);  
        mContainer.removeAllViews();  
        
        mPosMap.clear();
        
        for (int i = 0; i < countOneScreen; i++)
        {
        	View view = mAdapter.getView(i, null, mContainer);
        	
        	view.setOnClickListener(this);
        	mContainer.addView(view);
        	mPosMap.put(view, i);
        	mLastItemIndex = i;
        }
        
        //�ص�  
        if (mListener != null)  
        {  
            notifyCurrentImgChanged();  
        }  
        
        requestLayout();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int scrollX = getScrollX();
			if (scrollX >= mChildWidth)  
            {  
                loadNextImg();  
            }  
            // �����ǰscrollX = 0�� ��ǰ����һ�ţ��Ƴ����һ��  
            if (scrollX == 0)  
            {  
                loadPreImg();  
            }  
			break;

		}
		return super.onTouchEvent(ev);
	}
	
	
	
	
	/**
	 * ������һ��ͼƬ 
	 */
	private void loadNextImg() {
		if (mLastItemIndex == mAdapter.getCount() - 1)
		{
			return;
		}
		
		//�Ƴ���һ��ͼƬ���ҽ�ˮƽ����λ����0  
		
		scrollTo(0, 0);
		mPosMap.remove(mContainer.getChildAt(0));
		mContainer.removeViewAt(0);
		
		//��ȡ��һ��ͼƬ����������onclick�¼����Ҽ���������  
        View view = mAdapter.getView(++mLastItemIndex, null, mContainer);  
        view.setOnClickListener(this);  
        mContainer.addView(view);  
        mPosMap.put(view, mLastItemIndex);
        
      //��ǰ��һ��ͼƬС��  
        mFirstItemIndex++; 
        
      //��������˹��������򴥷�  
        if (mListener != null)  
        {  
            notifyCurrentImgChanged();  
        } 
	}
	/**
	 * ����ǰһ��ͼƬ
	 */
	private void loadPreImg() {
		if (mFirstItemIndex == 0)
		{
			return;
		}
		
		//��õ�ǰӦ����ʾ�ĵ�һ��ͼƬ���±�
		int index = mLastItemIndex - mCountOneScreen;
		if (index >= 0)
		{
			// �Ƴ����һ��
			int oldPos = mContainer.getChildCount() - 1;
			mPosMap.remove(mContainer.getChildAt(oldPos));
			mContainer.removeViewAt(oldPos);

			// ����View�����һ��λ��
			View view = mAdapter.getView(index, null, mContainer);
			mPosMap.put(view, index);
			mContainer.addView(view, 0);
			view.setOnClickListener(this);
			// ˮƽ����λ�������ƶ�view�Ŀ�ȸ�����
			scrollTo(mChildWidth, 0);
			// ��ǰλ��--����ǰ��һ����ʾ���±�--
			mLastItemIndex--;
			mFirstItemIndex--;
			
			 //�ص�  
            if (mListener != null)  
            {  
                notifyCurrentImgChanged();  
            }  
		}
		
	}
	
	/** 
     * ����ʱ�Ļص� 
     */  
    @SuppressLint("NewApi")
	public void notifyCurrentImgChanged()  
    {  
        //��������еı���ɫ�����ʱ������Ϊ��ɫ  
        for (int i = 0; i < mContainer.getChildCount(); i++)  
        {  
            mContainer.getChildAt(i).setBackgroundColor(Color.BLACK);
            mContainer.getChildAt(i).setScaleX(1.0f);
            mContainer.getChildAt(i).setScaleY(1.0f);
        }  
        mListener.onCurrentImgChanged(mFirstItemIndex+2, mContainer.getChildAt(0+2));  
  
    }
	
	public void setOnItemClickListener(OnItemClickListener mOnClickListener)  
    {  
        this.mOnClickListener = mOnClickListener;  
    }
	
	public void setCurrentImageChangeListener(CurrentImageChangeListener mListener)  
    {  
        this.mListener = mListener;  
    }  
	
	@Override
	public void onClick(View v) {
		if (mOnClickListener != null)
		{
			for (int i = 0; i < mContainer.getChildCount(); i++)
			{
				mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
			}
			mOnClickListener.onClick(v, mPosMap.get(v));
		}
	}  
	

}
