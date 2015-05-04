package com.general.comtop.signature.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.general.comtop.signature.R;
import com.general.comtop.signature.utility.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 生成数字签名图片控件
 * 
 * @author shiyaofang
 * 
 */
public class SignatureView extends View {
	/** 默认签名图片大小 */
	private int mStandardBitmapWidth = 300;
	private int mStandardBitmapHeight = 100;
	/**
	 * 绘成的图片
	 */
	private Bitmap mBitmap;
	/**
	 * 画布
	 */
	private Canvas mCanvas;
	/**
	 * 绘图路径
	 */
	private Path mPath;
	/**
	 * 绘制图片的画笔
	 */
	private Paint mBitmapPaint;
	/**
	 * 绘制路径的画笔
	 */
	private Paint mPathPaint;
	/** 当前坐标 */
	private float mX, mY;
	/**
	 * 完成画图时最终矩形的左上角x轴坐标
	 */
	private float mTopLeftX = 0;
	/**
	 * 完成画图时最终矩形的左上角y轴坐标
	 */
	private float mTopLeftY = 0;
	/**
	 * 完成画图时最终矩形的右下角x轴坐标
	 */
	private float mDownRightX = 0;
	/**
	 * 完成画图时最终矩形的右下角y轴坐标
	 */
	private float mDownRightY = 0;

	// private static final float TOUCH_TOLERANCE = 4;// 公差4dp

	/**
	 * 构造函数
	 */
	public SignatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	// 新建绘图路径，画笔，和画布
	private void init() {

		mTopLeftX = 0;
		mPath = new Path();// 绘图路径
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);// 绘制图片的画笔
		mPathPaint = new Paint();// 绘制路径的画笔
		mPathPaint.setAntiAlias(true);
		mPathPaint.setDither(true);
		mPathPaint.setColor(getResources().getColor(R.color.signature_tv));// 颜色
		mPathPaint.setStyle(Paint.Style.STROKE);// 样式 线条
		mPathPaint.setStrokeJoin(Paint.Join.ROUND);
		mPathPaint.setStrokeCap(Paint.Cap.ROUND);
		mPathPaint.setStrokeWidth(7);// 笔画宽度
	}

	/**
	 * 测量手机屏幕宽度和高度
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.createBitmap();
	}

	
	/**
	 * 生成签名图片
	 */
	protected void createBitmap() {
		if (mBitmap != null) {
			return;
		}
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Bitmap.Config.ARGB_8888);
		mBitmap.eraseColor(Color.TRANSPARENT);
		mCanvas = new Canvas(mBitmap);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	
	/**
	 * 绘制视图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);

		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

		canvas.drawPath(mPath, mPathPaint);
		// 以下代码修复少量字迹像素无法包括进来的偏差
		/*
		 * canvas.drawRect(mTopLeftX - TOUCH_TOLERANCE, mTopLeftY -
		 * TOUCH_TOLERANCE, mDownRightX + TOUCH_TOLERANCE, mDownRightY +
		 * TOUCH_TOLERANCE, mBitmapPaint);
		 */
	}

	/**
	 * 开始绘制
	 */
	private void touch_start(float x, float y) {
		computeDrawMaxRange(x, y);
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	/**
	 * 计算画图最大区域的坐标
	 * 
	 * @param x
	 * @param y
	 */
	private void computeDrawMaxRange(float x, float y) {
		if (mTopLeftX == 0 && mTopLeftY == 0 && mDownRightX == 0
				&& mDownRightY == 0) {
			mTopLeftX = x;
			mTopLeftY = y;
			mDownRightX = x;
			mDownRightY = y;
		}
		mTopLeftX = Math.min(mTopLeftX, x);
		mTopLeftY = Math.min(mTopLeftY, y);
		mDownRightX = Math.max(mDownRightX, x);
		mDownRightY = Math.max(mDownRightY, y);
	}

	
	/**
	 * 绘制过程
	 */
	private void touch_move(float x, float y) {
		if (y >= this.getHeight()) {
			return;
		}
		computeDrawMaxRange(x, y);
		// float dx = Math.abs(x - mX);// 横坐标移动量
		// float dy = Math.abs(y - mY);// 纵坐标移动量
		/*
		 * if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
		 * mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);// 移动画笔 mX = x; mY =
		 * y; }
		 */
		mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);// 移动画笔
		mX = x;
		mY = y;
	}

	// 停止绘制
	private void touch_up() {
		mPath.lineTo(mX, mY);// 路径到达终点坐标
		mCanvas.drawPath(mPath, mPathPaint);// 通过路径，在画布上画图
		mPath.reset();// 重新设置绘图路径到原始状态
	}

	// 触屏事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();// 横坐标
		float y = event.getY();// 纵坐标
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// 手指接触到屏幕
			touch_start(x, y);
			invalidate();// 绘制结束更新视图，触发ondraw事件
			break;
		case MotionEvent.ACTION_MOVE:// 手指移动
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:// 手指释放
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	/**
	 * 设置生成图片的最终大小，单位像素
	 * 
	 * @param width
	 *            图片的宽
	 * @param height
	 *            图片的高
	 */
	public void setStandardBitmapSize(int width, int height) {
		mStandardBitmapWidth = width;
		mStandardBitmapHeight = height;
	}

	/**
	 * 根据给定的标准图片大小放大或缩小给定的源图片
	 * 
	 * @param sourceBitmap
	 *            需要改变的图片
	 * @return 新图片
	 */
	private Bitmap scaleToNewBitmap(Bitmap sourceBitmap) {
		// 计算签字区域矩形宽，和高
		float signAreaWidth = mDownRightX - mTopLeftX;
		float signAreaHeight = mDownRightY - mTopLeftY;
		if (signAreaWidth != 0 || signAreaHeight != 0) {
			float widthScale = mStandardBitmapWidth / signAreaWidth;
			float heightScale = mStandardBitmapHeight / signAreaHeight;
			Matrix matrix = new Matrix();
			float scaleRate = 1f;
			// 第一种：签字超过标准区域大小，需要缩小
			if (signAreaWidth > mStandardBitmapWidth
					|| signAreaHeight > mStandardBitmapHeight) {
				scaleRate = Math.min(widthScale, heightScale);
			} else if (signAreaWidth < mStandardBitmapWidth
					&& signAreaHeight < mStandardBitmapHeight) {
				// 第二中：签字区域宽和高都小于标准图片大小，需要放大
				scaleRate = Math.max(widthScale, heightScale);
			}// 第三种: 任意一边和标准边相等或全部相等，其它边小于标准长度，则保持原样
			matrix.postScale(scaleRate, scaleRate);
			// 由于坐标可能会有几个像素的负数坐标，要转换为0坐标，坐标修正
			if (mTopLeftX < 0) {
				mTopLeftX = 0;
			} else if (mTopLeftY < 0) {
				mTopLeftY = 0;
			}
			// 截图有可能超过Bitmap的范围，画图范围修正（有几个像素的误差）
			if (sourceBitmap.getWidth() < mTopLeftX + signAreaWidth) {
				signAreaWidth = sourceBitmap.getWidth() - mTopLeftX;
			}
			if (sourceBitmap.getHeight() < mTopLeftY + signAreaHeight) {
				signAreaHeight = sourceBitmap.getHeight();
			}

			Bitmap newBitmap = Bitmap.createBitmap(sourceBitmap,
					(int) mTopLeftX, (int) mTopLeftY, (int) signAreaWidth,
					(int) signAreaHeight, matrix, true);
			return newBitmap;
		} else {
			return sourceBitmap;
		}
	}

	/**
	 * 
	 * 将画好的图片经转换保存到指定的路径，保存的文件名为时间字符串
	 * 
	 * @param path
	 *            指定保存的路径，扩展SD卡为根目录，最后一个目录不带"/"
	 * @return 返回图片保存的完整路径名
	 */
	@SuppressLint("SimpleDateFormat")
	public String saveBitmapToPngFile(String path) {
		//如果没有画图，则不生存图片
		if (mTopLeftX == 0) {
			return "";
		}
		String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Bitmap b = scaleToNewBitmap(mBitmap);
		String currentPath = Constants.SDCARD_PATH+ path;
		FileOutputStream fos = null;
		try {
			File sddir = new File(currentPath);
			if (!sddir.exists()) {
				sddir.mkdirs();
			}
			File file = new File(currentPath + "/" + name + ".png");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			if (fos != null) {
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.close();
			}
			return file.getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 清除之前画的图像
	 */
	public void clearSignature() {
		mBitmap.eraseColor(Color.TRANSPARENT);
		// 清空之前记录的全局变量值
		mTopLeftX = 0;
		mTopLeftY = 0;
		mDownRightX = 0;
		mDownRightY = 0;
		invalidate();
	}

	/**
	 * 获取指定规格的图片
	 */
	public Bitmap getSignatureBitmap() {
		return scaleToNewBitmap(mBitmap);
	}
}
