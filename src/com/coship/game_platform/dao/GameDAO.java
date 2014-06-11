package com.coship.game_platform.dao;

import java.util.ArrayList;

import com.coship.game_platform.bean.GameInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 保存下载游戏数据库
 * 
 * @author 王建春
 * 
 */
public class GameDAO extends DataBaseDAO {

	private final static String TABLE_NAME = "game_info";
	private SQLiteDatabase db;
	private static GameDAO mGameDAO = null;
	private final static String FIELD_id = "_id";
	//游戏名称
	private final String GAME_NAME = "gameName";

	// 游戏路径
	private final String DATE_URL = "dataUrl";

	// 包路径
	private final String PAGE_URL = "pageUrl";

	public GameDAO(Context context) {
		super(context);
	}

	public static GameDAO getInstance(Context context) {
		if (mGameDAO == null) {
			mGameDAO = new GameDAO(context);
		}
		return mGameDAO;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String getContent() {
		return FIELD_id + " integer  primary key autoincrement," + " " + GAME_NAME + " text," + DATE_URL + " text," + PAGE_URL + " text)";
	}

	/**
	 * 插入新下载游戏纪录
	 * 
	 * @param myLocator
	 */
	public void insertGame(GameInfo gameInfo) {
		if(gameInfo!=null){
			db = super.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put("gameName", gameInfo.getGameName());
			cv.put("dataUrl", gameInfo.getDataUrl());
			cv.put("pageUrl", gameInfo.getPageUrl());
			db.insert(TABLE_NAME, null, cv);
		}
		super.close();
	}

	/**
	 * 删除数据
	 */
	public void delAllGameList() {
		db = super.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		super.close();
	}

	/**
	 * 获取我的所有已下载游戏
	 */
	public ArrayList<GameInfo> getAllGameInfoDB() {
		ArrayList<GameInfo> gameInfos = new ArrayList<GameInfo>();
		db = super.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by _id", null);
		while (cursor.moveToNext()) {
			GameInfo gameInfo = new GameInfo();
			gameInfo.setGameName(cursor.getString(cursor.getColumnIndex("gameName")));
			gameInfo.setDataUrl(cursor.getString(cursor.getColumnIndex("dataUrl")));
			gameInfo.setPageUrl(cursor.getString(cursor.getColumnIndex("pageUrl")));
			gameInfos.add(gameInfo);
		}
		cursor.close();
		super.close();
		return gameInfos;
	}
}
