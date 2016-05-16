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
 * @time 2016-5-4 上午9:21:06
 * 自定义滑动删除View
 */
public class SwipeLayout extends LinearLayout {

	private static final String TAG = "SwipeLayout";
	
	private final double AUTO_OPEN_SPEED_LIMIT = 800.0;
	/**处理滑动事件*/
	private ViewDragHelper viewDragHelper;
	
	/**两个布局View*/
	private View contentView, actionView;
	
	/**移动位置*/
	private int draggedX;
	
	/**移动最大位置，其实是actionView的宽度*/
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

	/**布局加载完调用*/
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

		


		/**用来确定contentView和actionView是可以拖动的*/
		@Override
		public boolean tryCaptureView(View view, int i) {
			return view == contentView || view == actionView;
		}
		
		/**限制view在x轴上拖动
		 * left为即将移动到的位置
		 * 返回值为处理后真正移动到的位置
		 * PS:先调用这个函数再根据返回值调用 onViewPositionChanged*/
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			Log.i(TAG, "clampViewPositionHorizontal left = " + left + ",dx = " + dx);
			/*//返回横向坐标左右边界值
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

		/**被拖动的view位置改变的时候调用*/
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			Log.i(TAG, "onViewPositionChanged left = " + left + ",dx = " + dx);
			draggedX = left;
			/**contentView移动时，actionView跟着移动*/
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
		
		
		/**限制view横向可以拖动的范围*/
		@Override
		public int getViewHorizontalDragRange(View child) {
            Log.i(TAG, "getViewHorizontalDragRange dragDistance = " + dragDistance);
			return dragDistance;
		}

		
		/**根据滑动手势的速度以及滑动的距离来确定是否显示actionView*/
		/**smoothSlideViewTo方法用来在滑动手势之后实现惯性滑动效果*/
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
			/**惯性滑动到指定位置*/
			viewDragHelper.smoothSlideViewTo(contentView, settleX, 0);
			/**调用view的 computeScroll() 方法*/
			ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
		}
		
	}
	

	
}
