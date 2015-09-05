package com.kenny.flowlayout.myflow;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup{
	/**view被创建时调用*/
	public FlowLayout(Context context) {
		this(context,null);
	}
	/**布局中的控件使用了自定义属性时调用*/
	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	/**布局中使用了自定义view并且使用了自定义属性时会调用*/
	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		
		int width = 0;
		int height = 0;
		//记录每一行的宽度与高度
		int lineWidth = 0;
		int lineHeight = 0;
		//得到内部元素的个数
		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			//测量子view的宽和高
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			//得到子view的layoutParams
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			//子view占据的宽度
			int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			//子view占据的高度
			int childHeiht = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
			
			//如果此行的宽度+子view的宽度大于viewgroup的宽度了那就换行
			if (lineWidth + childWidth >sizeWidth - getPaddingLeft() - getPaddingRight()) {/**换行*/
				//对比得到最大的宽度
				width = Math.max(width, lineWidth);
				//重置lineWidth
				lineWidth = childWidth;
				//记录行高
				height += lineHeight;
				lineHeight = childHeiht;
			}else {/**未换行*/
				//叠加行宽
				lineWidth += childWidth;
				//得到当前行最大的高度
				lineHeight = Math.max(lineHeight, childHeiht);
			}
			//最后 一个控件特殊处理一下
			if (i==cCount - 1) {
				width = Math.max(lineWidth, width);
				height+= lineHeight;
			}
		}
		
		setMeasuredDimension(
				//如果当前模式是EXACTLY(match_parent/100dp)使用布局指定的宽度或满屏，如果当前模式是AT_MOST则使用我们重新测量出来的宽高
				modeWidth== MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(), 
				modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
	}
	
	/**存储所有的view，但是是一行一行的存储*/
	private List<List<View>> mAllViews = new ArrayList<List<View>>();
	/**每一行的高度*/
	private List<Integer> mLineHeight = new ArrayList<Integer>();
	
	//给子view设置位置
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mAllViews.clear();
		mLineHeight.clear();
		//当前viewgroup的宽度
		int width = getWidth();
		
		int lineWidth = 0;
		int lineHeight = 0;
		
		List<View> lineViews = new ArrayList<View>();
		int cCount = getChildCount();
		
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			//如果需要换行
			if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
				
				//记录LineHeight
				mLineHeight.add(lineHeight);
				//记录行的views
				mAllViews.add(lineViews);
				//重置我们的行宽和行高
				lineWidth = 0;
				lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
				//重置我们的view集合
				lineViews = new ArrayList<View>();
				
			}
			//如果不需要换行
			lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
			lineViews.add(child);
		}
		//处理最后一行
		mLineHeight.add(lineHeight);
		mAllViews.add(lineViews);
		//设置子view的位置
		int left = getPaddingLeft();
		int top = getPaddingTop();
		//行数
		int lineNum = mAllViews.size();
		for (int i = 0; i <lineNum; i++) {
			//当前行的所有的view
			lineViews = mAllViews.get(i);
			lineHeight = mLineHeight.get(i);
			for (int j = 0; j < lineViews.size(); j++) {
				View child = lineViews.get(j);
				//判断child的状态
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
				int lc = left + lp.leftMargin;
				int tc = top + lp.topMargin;
				int rc = lc + child.getMeasuredWidth();
				int bc = tc + child.getMeasuredHeight();
				//为子view进行布局
				child.layout(lc, tc, rc, bc);
				left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			}
			left = getPaddingLeft();
			top += lineHeight;
		}
				
	}

	/**对应本viewgroup的layoutParams*/
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(),attrs);
	}

}
