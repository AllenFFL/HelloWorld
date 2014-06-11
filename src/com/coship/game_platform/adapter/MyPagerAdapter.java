package com.coship.game_platform.adapter;

import java.util.ArrayList;

import com.coship.game_platform.activity.ProFragment;
import com.coship.game_platform.domain.PlatformConstants;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MyPagerAdapter extends FragmentPagerAdapter {
	private String[] titles;
	private ArrayList<ProFragment> fragments;
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	public MyPagerAdapter(FragmentManager fm,String[] titles,ArrayList<ProFragment> fragments){
		super(fm);
		this.titles=titles;
		this.fragments=fragments;
	}
	@Override
	public Fragment getItem(int id) {
			Bundle args = new Bundle();
			args.putString(PlatformConstants.ARGUMENTS_NAME, titles[id]);
			fragments.get(id).setArguments(args);
			return fragments.get(id);
	}

	@Override
	public int getCount() {
		
		return titles.length;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return 	titles[position];
	}

}
