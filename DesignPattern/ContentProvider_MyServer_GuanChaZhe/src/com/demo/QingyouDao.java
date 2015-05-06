package com.demo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class QingyouDao extends ContentProvider {
	DBHelper db2;

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		SQLiteDatabase db = db2.getWritableDatabase();
		int dd=db.delete("aa", arg1, arg2);
		return dd;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = db2.getWritableDatabase();
		db.insert("aa", "name", values);
		//告诉观察者对象:我现在发生变化
		this.getContext().getContentResolver().notifyChange(uri, null);
		db.close();
		return uri;
	}

	/**
	 * 用于创建数据库
	 */
	@Override
	public boolean onCreate() {
		db2 = new DBHelper(this.getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db3 = db2.getReadableDatabase();
		Cursor cursor = db3.query("aa", projection, selection,
				selectionArgs, null, null, sortOrder);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db3 = db2.getReadableDatabase();
		int count=db3.update("aa", values, selection, selectionArgs);
		return count;
	}

}
