package com.kenny.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义view实现步骤
 * <p/>
 * 1、自定义View的属性
 * 2、在View的构造方法中获得我们自定义的属性(必须要三个参数的构造方法中才能获取到)。
 * 布局中的view默认只会调用一个参数和二个参数的构造方法，在布局中的view使用到自定义属性时才会调用三个参数的构造方法。
 * [ 3、重写onMesure ]
 * 4、重写onDraw
 */
public class MyView extends View {

    /**
     * 自定义view总宽
     */
    private int mWidth;
    /**
     * 自定义view总高
     */
    private int mHeight;
    /**
     * 自定义view中的图片
     */
    private Bitmap mImage;
    /**
     * 自定义view中的图片的参数
     */
    private int mImageScale;
    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;
    /**
     * 自定义view中的文本
     */
    private String mTitle;
    /**
     * 自定义view中的文本的颜色
     */
    private int mTextColor;
    /**
     * 自定义view中的文本颜色的大小
     */
    private int mTextSize;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 文本的范围(矩形)
     */
    private Rect mTextBound;
    /**
     * 自定义view的范围(矩形)
     */
    private Rect rect;
	/**view被创建时调用*/
    public MyView(Context context) {
        this(context, null);
    }
	/**布局中的控件使用了自定义属性时调用*/
    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
	/**布局中使用了自定义view并且使用了自定义属性时会调用*/
    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //得到自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyView, defStyle, 0);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.MyView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));//得到自定义view中的图片
                    break;
                case R.styleable.MyView_imageScaleType://得到自定义view中的图片的参数
                    mImageScale = a.getInt(attr, 0);
                    break;
                case R.styleable.MyView_titleText://得到自定义view中的文本
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.MyView_titleTextColor://得到自定义view中的文本的颜色
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyView_titleTextSize://得到自定义view中的文本的字体大小
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;

            }
        }
        //释放资源
        a.recycle();
        //创建自定义view矩形范围
        rect = new Rect();
        //创建画笔
        mPaint = new Paint();
        //创建自定义view中文本范围
        mTextBound = new Rect();
        //设置笔画大小为自定义view中文本的字体大小
        mPaint.setTextSize(mTextSize);
        //得到绘制后文本范围大小
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    /**
     * 重新测量自定义view尺寸参数
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //得到自定义view宽的specMode类型
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        //如果自定义view宽的specMode类型为match_parent/accurate，则自定义view的宽直接使用充满全屏
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            Log.e("xxx", "EXACTLY");
            mWidth = specSize;

        }
        //否则重新测量宽度尺寸
        else {
            // 得到图片占的度
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 得到文本占的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, specSize);
                Log.e("xxx", "AT_MOST");
            }
        }

        //得到自定义view高的specMode类型
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
        {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }
        //重新设置自定义view的宽高
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        /**先绘制自定义view边框left,top,right,bottom*/
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        //得到自定义view左位置点
        rect.left = getPaddingLeft();
        //得到自定义view右位置点
        rect.right = mWidth - getPaddingRight();
        //得到自定义view上位置点
        rect.top = getPaddingTop();
        //得到自定义view下位置点
        rect.bottom = mHeight - getPaddingBottom();

        /**开始绘制文本及图片*/
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        //当前设置的宽度小于字体需要的宽度，将字体改为xxx...
        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else {
            //正常情况，将字体居中
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        //去掉自定义view中被文本使用掉的块
        rect.bottom -= mTextBound.height();

        if (mImageScale == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            //计算居中的矩形范围（使图片能居中）
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }

    }
}
