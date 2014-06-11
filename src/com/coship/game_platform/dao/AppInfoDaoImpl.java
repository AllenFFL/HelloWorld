package com.coship.game_platform.dao;

import java.io.Serializable;
import java.util.List;

import android.content.Context;

import com.coship.game_platform.bean.AppInfo;

public class AppInfoDaoImpl extends DAOSupport<AppInfo>  implements AppInfoDao {
	public AppInfoDaoImpl(Context context) {
		super(context);
	}
	@Override
	public long insert(AppInfo m) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(AppInfo m) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AppInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppInfo> findByCondition(String orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppInfo getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
