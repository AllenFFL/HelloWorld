package com.coship.game_platform.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String NAME="platform.db";
	private static final int VERSION=1;
	private static final String TABLE_GAME="gameinfo";
	public static final String TABLE_ID="_id";// 通用的主键";
	public DBHelper(Context context) {
		super(context, NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//游戏信息的表
		db.execSQL("CREATE TABLE"+TABLE_ID+"varchar(50),"+
		TABLE_GAME+"("+"gameName"+"varchar(50),"
				+"dataUrl"+"varchar(50),"
		+"pageUrl"+"varchar(50),"
				+"appDetail"+"varchar(50),"+")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
