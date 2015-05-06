package com.demo;

import com.demo.interfaces.SortInterface;

/**
 * 插入排序
 * 
 * @author Hugs
 * @2014-11-14
 */
public class InsertSort implements SortInterface {

	@Override
	public int[] getSortMethod(int[] arr) {
		// 直接插入排序
		for (int i = 1; i < arr.length; i++) {
			// 待插入元素
			int temp = arr[i];
			int j;
			/*
			 * for (j = i-1; j>=0 && a[j]>temp; j--) { //将大于temp的往后移动一位 a[j+1] =
			 * a[j]; }
			 */
			for (j = i - 1; j >= 0; j--) {
				// 将大于temp的往后移动一位
				if (arr[j] > temp) {
					arr[j + 1] = arr[j];
				} else {
					break;
				}
			}
			arr[j + 1] = temp;
		}
		return arr;
	}

}
