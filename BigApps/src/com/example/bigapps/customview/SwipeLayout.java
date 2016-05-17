/**
 * 
 */
package com.example.bigapps.customview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author YC
 * @time 2016-5-17 下午4:05:19
 */
public class SwipeLayout extends LinearLayout {
private static final String TAG = "SwipeLayout";
	
	private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
	/**��������¼�*/
	private ViewDragHelper viewDragHelper;
	
	/**��������View*/
	private View contentView, actionView;
	
	/**�ƶ�λ��*/
	private int draggedX;
	
	/**�ƶ����λ�ã���ʵ��actionView�Ŀ��*/
	private int dragDistance;
	
	public SwipeLayout(Context context) {
		this(context, null);
	}
	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		viewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
		
	}

	/**���ּ��������*/
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		contentView = getChildAt(0);
		actionView = getChildAt(1);
		actionView.setVisibility(View.GONE);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		dragDistance = actionView.getMeasuredWidth();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (viewDragHelper.shouldInterceptTouchEvent(ev)){
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		viewDragHelper.processTouchEvent(event);
		return true;
	}
	
	 @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
	
	private class DragHelperCallback extends ViewDragHelper.Callback{

		


		/**����ȷ��contentView��actionView�ǿ����϶���*/
		@Override
		public boolean tryCaptureView(View view, int i) {
			return view == contentView || view == actionView;
		}
		
		/**����view��x�����϶�
		 * leftΪ�����ƶ�����λ��
		 * ����ֵΪ����������ƶ�����λ��
		 * PS:�ȵ�����������ٸ��ݷ���ֵ���� onViewPositionChanged*/
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			Log.i(TAG, "clampViewPositionHorizontal left = " + left + ",dx = " + dx);
			/*//���غ����������ұ߽�ֵ
			 * if (mainLeft + dx < 0) { 
                return 0 ;//0
            } else if (mainLeft + dx > range) {
                return range;//
            } else {
                return left;
            }*/
			if (child == contentView){
				final int leftBound = getPaddingLeft();
				final int minLeftBound = leftBound - dragDistance;
				final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
				Log.i(TAG, "onViewPositionChanged contentView newLeft = " + newLeft);
				return newLeft;
			} else {
				 final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.min(Math.max(left, minLeftBound), maxLeftBound);
                Log.i(TAG, "onViewPositionChanged actionView newLeft = " + newLeft);
                return newLeft;
			}
		}

		/**���϶���viewλ�øı��ʱ�����*/
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			Log.i(TAG, "onViewPositionChanged left = " + left + ",dx = " + dx);
			draggedX = left;
			/**contentView�ƶ�ʱ��actionView�����ƶ�*/
			if (changedView == contentView){
				actionView.offsetLeftAndRight(dx);
				Log.i(TAG, "onViewPositionChanged contentView");
			} else if (changedView == actionView){
				contentView.offsetLeftAndRight(dy);
				Log.i(TAG, "onViewPositionChanged actionView");
			}
			if (actionView.getVisibility() == View.GONE){
				actionView.setVisibility(View.VISIBLE);
			}
			invalidate();
		}
		
		
		/**����view��������϶��ķ�Χ*/
		@Override
		public int getViewHorizontalDragRange(View child) {
            Log.i(TAG, "getViewHorizontalDragRange dragDistance = " + dragDistance);
			return dragDistance;
		}

		
		/**���ݻ������Ƶ��ٶ��Լ������ľ�����ȷ���Ƿ���ʾactionView*/
		/**smoothSlideViewTo���������ڻ�������֮��ʵ�ֹ��Ի���Ч��*/
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
            Log.i(TAG, "onViewReleased xvel = " + xvel);
			boolean open = false;
			if (xvel > AUTO_OPEN_SPEED_LIMIT){
				open = false;
			} else if (xvel < -AUTO_OPEN_SPEED_LIMIT){
				open = true;
			} else if (draggedX <= -dragDistance/2){
				open = true;
			}else if (draggedX > -dragDistance/2){
				open = false;
			}
			
			final int settleX = open ? -dragDistance : 0;
			/**���Ի�����ָ��λ��*/
			viewDragHelper.smoothSlideViewTo(contentView, settleX, 0);
			/**����view�� computeScroll() ����*/
			ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
		}
		
	}
}
