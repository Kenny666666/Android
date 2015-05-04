package com.demo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.demo.bean.Bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;


public class SharedPreferencesTool {
	/***
	 * 保存PushId
	 * 
	 * @param pushIdList
	 */
	public static void saveBean(Context c, Bean bean) {

		SharedPreferences mySharedPreferences = c.getSharedPreferences("shared", Activity.MODE_PRIVATE);
		ByteArrayOutputStream toByte = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(toByte);
			oos.writeObject(bean);
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			String beanStr = new String(Base64.encode(toByte.toByteArray(),Base64.DEFAULT));
			editor.putString("bean", beanStr);
			// editor.remove(key);
			// 提交当前数据
			if (editor.commit()) {
				// Toast.makeText(getInstance(), R.string.save_print_success,
				// Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Bean readBean(Context c) {
		SharedPreferences sharedPreferences = c.getSharedPreferences("shared",Activity.MODE_PRIVATE);
		Bean bean = null;
		String content = sharedPreferences.getString("bean", "");
		try {
			if (!TextUtils.isEmpty(content)) {
				byte[] base64Bytes = Base64.decode(content, Base64.DEFAULT);
				ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
				ObjectInputStream ois;
				ois = new ObjectInputStream(bais);
				bean = (Bean) ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
}
