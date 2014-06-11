package com.coship.game_platform.dao;


import com.coship.game_platform.domain.PlatformConstants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库处理类，负责数据库方面的处理
 * 
 * @author chenyunsong
 * 
 */
public abstract class DataBaseDAO{

	private final String TAG = "DataBaseDAO";

	private static SQLiteOpenHelper sqLiteOpenHelper = null;

	private static int openedConnections = 0;

	protected DataBaseDAO(Context context) {
		synchronized(this){
			if (sqLiteOpenHelper == null) {
			sqLiteOpenHelper = new SQLiteOpenHelper(context, PlatformConstants.DATABASE_NAME, null, PlatformConstants.DATABASE_VERSION) {

				@Override
				public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					String sql = "drop table if exists " + getTableName();
					db.execSQL(sql);
					onCreate(db);
				}

				@Override
				public void onCreate(SQLiteDatabase db) {

				}
				
			};
		}
			SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
			String sql = "create table if not exists " + getTableName() + " (" + getContent();
			db.execSQL(sql);
			Log.d(TAG, sql);
		}
	}

	/**
	 * 获得表名称
	 * 
	 * @return
	 */
	protected abstract String getTableName();

	/**
	 * 获得表结构
	 * 
	 * @return
	 */
	protected abstract String getContent();

	/**
	 * 删除数据库
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(getTableName());
	}

	public synchronized void close() {
		openedConnections--;
		if (openedConnections == 0) {
			sqLiteOpenHelper.close();
		}
	}

	public synchronized SQLiteDatabase getWritableDatabase() {
		openedConnections++;
		return sqLiteOpenHelper.getWritableDatabase();
	}

	public synchronized SQLiteDatabase getReadableDatabase() {
		openedConnections++;
		return sqLiteOpenHelper.getReadableDatabase();
	}

}
