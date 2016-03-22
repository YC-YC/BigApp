package com.example.bigapps.slidedel;

import java.util.zip.Inflater;

import com.example.bigapps.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * @Author Administrator
 * @Time 2016-3-22 ����10:55:51
 */
public class DelListView extends ListView {

	private LayoutInflater mInflater;

	private Button mDelBtn;
	private PopupWindow mPopupWindow;

	private int mPopupWinWidth;
	private int mPopupHeight;
	
	/**
	 * ����ʱ��x,y
	 */
	private int mLastX;
	private int mLastY;
	
	/**
	 * ��ǰ��ָ���µ�λ��
	 */
	private int mCurPos;
	private View mCurView;
	
	/**
	 * ������С����
	 */
	private int mTouchSlop;
	
	private boolean mIsSliding;

	private OnDelListener mListener;
	
	public void setOnDelListener(OnDelListener listener)
	{
		mListener = listener;
	}
	
	public interface OnDelListener{
		public void onDel(int position);
	}
	
	public DelListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mInflater = LayoutInflater.from(context);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		
		View view = mInflater.inflate(R.layout.delete_btn, null);
		mDelBtn = (Button) view.findViewById(R.id.id_item_btn);

		mPopupWindow = new PopupWindow(view,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// �ȵ�����measure,�����ò�����͸�
		mPopupWindow.getContentView().measure(0, 0);
		mPopupWinWidth = mPopupWindow.getContentView().getMeasuredWidth();
		mPopupHeight = mPopupWindow.getContentView().getMeasuredHeight();

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		int action = ev.getAction();
		int x = (int) ev.getX();
		int y = (int) ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			/** 
             * �����ǰpopupWindow��ʾ����ֱ�����أ�Ȼ������ListView��touch�¼����´� 
             */
			if (mPopupWindow.isShowing())
			{
				dismissPopWin();
				return false;
			}
			else
			{
				mCurPos = pointToPosition(x, y);
				mCurView = getChildAt(mCurPos - getFirstVisiblePosition());
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = x - mLastX;
			int dy = y - mLastY;
			/** 
             * �ж��Ƿ��Ǵ��ҵ���Ļ��� 
             */
			if ( x < mLastX && Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop)
			{
				mIsSliding = true;
			}
			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();

		if (mIsSliding)
		{
			switch (action) {
			case MotionEvent.ACTION_MOVE:
				int[] location = new int[2];
				// ��õ�ǰitem��λ��x��y 
				mCurView.getLocationOnScreen(location);
				
				// ����popupWindow�Ķ���  
				mPopupWindow.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
				mPopupWindow.update();
				Log.i("tag", "loc[0] = " + location[0] + ", loc[1] = " + location[1]);
				Log.i("tag", "getWidth() = " + mCurView.getWidth() + ", getHeight() = " + mCurView.getHeight());
				mPopupWindow.showAtLocation(mCurView, 
						Gravity.LEFT | Gravity.TOP, 
						location[0] + mCurView.getWidth(),  
						location[1] + mCurView.getHeight() / 2 - mPopupHeight / 2);
			
				mDelBtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (mListener != null)
						{
							mListener.onDel(mCurPos);
						}
						mPopupWindow.dismiss();
					}
				});
			case MotionEvent.ACTION_UP:
				mIsSliding = false;
				break;
			default:
				break;
			}
		}
		return super.onTouchEvent(ev);
	}
	
	private void dismissPopWin() {
		if (mPopupWindow != null && mPopupWindow.isShowing())  
        {  
            mPopupWindow.dismiss();  
        }  
	}

}
