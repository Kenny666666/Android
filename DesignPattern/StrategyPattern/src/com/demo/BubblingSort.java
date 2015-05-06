package com.demo;

import com.demo.interfaces.SortInterface;

/**
 * 冒泡排序
 * 稳定
 * @author Hugs
 * @2014-11-14
 */
public class BubblingSort implements SortInterface {

	@Override
	public int[] getSortMethod(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				// 这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return arr;
	}

}
