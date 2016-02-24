package com.kenny.anim;

import android.animation.TypeEvaluator;

/**
 * 大家应该都还记得，ObjectAnimator内部的工作机制是通过寻找特定属性的get和set方法，然后通过方法不断地对值进行改变，从而实现动画效果的。因此我们就需要在MyAnimView中定义一个color属性，并提供它的get和set方法。这里我们可以将color属性设置为字符串类型，使用#RRGGBB这种格式来表示颜色值，
 */
public class ColorEvaluator implements TypeEvaluator {

	private int mCurrentRed = -1;

	private int mCurrentGreen = -1;

	private int mCurrentBlue = -1;

	/**
	 * 颜色变化的处理，用Color.red()等方法分离出rgb分量，再映射出当前的rgb分量，最后Color.rgb()来合成
	 * 1-2 控制透明度 两位数字
	 3-4 控制红色 两位数字
	 5-6 控制绿色 两位数字
	 7-8 控制蓝色 两位数字
	 总体颜色有三原色组成
	 int colorDiff = redDiff + greenDiff + blueDiff;
	 1-2 透明度可以不设置，这里面就是舍弃掉了。
	 变为#000000六位
	 * @param fraction
	 * @param startValue
	 * @param endValue
	 * @return
	 */
	@Override
	public Object evaluate(float fraction, Object startValue, Object endValue) {
		String startColor = (String) startValue;
		String endColor = (String) endValue;
		int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
		int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
		int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
		int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
		int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
		int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
		// 初始化颜色的值
		if (mCurrentRed == -1) {
			mCurrentRed = startRed;
		}
		if (mCurrentGreen == -1) {
			mCurrentGreen = startGreen;
		}
		if (mCurrentBlue == -1) {
			mCurrentBlue = startBlue;
		}
		// 计算初始颜色和结束颜色之间的差值
		int redDiff = Math.abs(startRed - endRed);
		int greenDiff = Math.abs(startGreen - endGreen);
		int blueDiff = Math.abs(startBlue - endBlue);
		int colorDiff = redDiff + greenDiff + blueDiff;
		if (mCurrentRed != endRed) {
			mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
					fraction);
		} else if (mCurrentGreen != endGreen) {
			mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
					redDiff, fraction);
		} else if (mCurrentBlue != endBlue) {
			mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
					redDiff + greenDiff, fraction);
		}
		// 将计算出的当前颜色的值组装返回
		String currentColor = "#" + getHexString(mCurrentRed)
				+ getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
		return currentColor;
	}

	/**
	 * 根据fraction值来计算当前的颜色。
	 */
	private int getCurrentColor(int startColor, int endColor, int colorDiff,
			int offset, float fraction) {
		int currentColor;
		if (startColor > endColor) {
			currentColor = (int) (startColor - (fraction * colorDiff - offset));
			if (currentColor < endColor) {
				currentColor = endColor;
			}
		} else {
			currentColor = (int) (startColor + (fraction * colorDiff - offset));
			if (currentColor > endColor) {
				currentColor = endColor;
			}
		}
		return currentColor;
	}
	
	/**
	 * 将10进制颜色值转换成16进制。
	 */
	private String getHexString(int value) {
		String hexString = Integer.toHexString(value);
		if (hexString.length() == 1) {
			hexString = "0" + hexString;
		}
		return hexString;
	}

}