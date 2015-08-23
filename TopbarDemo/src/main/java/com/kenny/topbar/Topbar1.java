package com.kenny.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * description 自定义界面头部
 * Created by hugs on 2015/7/17.
 * version
 */
public class Topbar1 extends RelativeLayout {

    /**左按钮*/
    private Button leftButton;
    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    /**右按钮*/
    private Button rightButton;
    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    /**中间标题*/
    private TextView tvTitle;
    private float titleTextSize;
    private int titleTextColor;
    private String titleText;

    private LayoutParams leftParams,rightParams,titleParams;

    private TopbarClickListener listener;

    public void setTopbarListener(TopbarClickListener listener){
        this.listener = listener;
    }
    public interface TopbarClickListener{
        void leftOnClickListener();
        void rightOnClickListener();
    }

    public Topbar1(Context context){
        this(context, null);
    }

    public Topbar1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Topbar1(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.topbar, defStyle, 0);
        leftTextColor = a.getColor(R.styleable.topbar_leftTextColor, 0);
        leftBackground = a.getDrawable(R.styleable.topbar_leftBackground);
        leftText = a.getString(R.styleable.topbar_leftText);

        rightTextColor = a.getColor(R.styleable.topbar_rightTextColor, 0);
        rightBackground = a.getDrawable(R.styleable.topbar_rightBackground);
        rightText = a.getString(R.styleable.topbar_rightText);

        titleTextColor = a.getColor(R.styleable.topbar_titleTextColor, 0);
        titleTextSize = a.getDimension(R.styleable.topbar_titleTextSize, 0);
        titleText = a.getString(R.styleable.topbar_titleText);

        a.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setText(titleText);
        tvTitle.setGravity(Gravity.CENTER);

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(tvTitle, titleParams);

//        setBackgroundColor(0xFFF59563);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftOnClickListener();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightOnClickListener();
            }
        });
    }
}
