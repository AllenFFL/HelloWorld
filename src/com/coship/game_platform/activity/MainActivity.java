package com.coship.game_platform.activity;

import java.util.ArrayList;

import com.coship.game_platform.R;
import com.coship.game_platform.R.layout;
import com.coship.game_platform.R.menu;
import com.coship.game_platform.adapter.MyPagerAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private static String titles[] = { "游戏", "活动", "资讯","个人","周边","直播","影院"};
	private ArrayList<TextView> textViews= new ArrayList<TextView>();
	private ArrayList<ProFragment> fragments= new ArrayList<ProFragment>();
	private UserFragment userf;
	private GameFragment gamef;
	private NewsFragment newf;
	private LiveFragment livef;
	private FilmFragment filmf;
	private ActivityFragment actif;
	private PeripheralsFragment perif;
	
	private LinearLayout ll_title;
	private ViewPager vp_pager;
	
	private String TAG="MainActivity";
	/**
	 * 代表当前显示的fragment
	 */
	private static int currentIndex=0;
	/**
	 * false代表标题获焦 ,true代表fragment获焦
	 */
	public static boolean focusFlag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		initView();
		setListener();
	}
	
	private void findView() {
		ll_title = (LinearLayout)findViewById(R.id.ll_title);
		vp_pager = (ViewPager)findViewById(R.id.vp_pager);
	}
	
	private void initView() {
		initTitle();
		initFrgament();
		
		vp_pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),titles,fragments));
		vp_pager.clearAnimation();
	}
	private void initTitle() {
		int width = getWindowManager().getDefaultDisplay().getWidth() /titles.length;
		int height = 100;
		for (int i = 0; i < titles.length; i++) {
			TextView textView = new TextView(this);
			textView.setText(titles[i]);
			textView.setTextSize(30);
			textView.setTextColor(Color.BLACK);
			textView.setWidth(width);
			textView.setHeight(height - 30);
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setOnClickListener(this);
			textViews.add(textView);
			ll_title.addView(textView);
		}
		for (int i = 0; i < textViews.size(); i++) {
			if(i==0){
				vp_pager.setCurrentItem(0);
				textViews.get(0).setTextColor(Color.WHITE);
				textViews.get(0).setTextSize(40);
			}else{
				textViews.get(i).setTextColor(Color.GRAY);
			}
		}
	}
	private void initFrgament() {
		userf = new UserFragment();
		gamef = new GameFragment();
		newf = new NewsFragment();
		actif = new ActivityFragment();
		livef = new LiveFragment();
		perif = new PeripheralsFragment();
		filmf = new FilmFragment();
		fragments.add(gamef);
		fragments.add(actif);
		fragments.add(newf);
		fragments.add(userf);
		fragments.add(perif);
		fragments.add(livef);
		fragments.add(filmf);
	}
	
	private void setListener() {
		vp_pager.setOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int arg0) {
						for (int i = 0; i < textViews.size(); i++) {
							if(i!=arg0){
								textViews.get(i).setTextColor(Color.GRAY);
								textViews.get(i).setTextSize(30);
							}else{
								textViews.get(arg0).setTextColor(Color.WHITE);
								textViews.get(arg0).setTextSize(40);
							}
						}
						Log.i(TAG, "PageSelected:"+arg0);
	
					}
					
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						
					}
					
					@Override
					public void onPageScrollStateChanged(int arg0) {
						
					}
					
				});
	}

	private static boolean keyFlag=true;
	/**
	 * 按键处理
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		switch (event.getAction()) {
		case KeyEvent.ACTION_DOWN:
			if(keyFlag){
				keyFlag=false;
				if(!focusFlag){
					return titleKeyEvent(event);
				}else{
					fragments.get(currentIndex).onKeyDown(event);
				}
			}
			return true;
		case KeyEvent.ACTION_UP:
			Log.i(TAG, "KeyEvent.ACTION_UP-keyFlag-"+keyFlag);
			keyFlag=true;
			return true;
		default:
			break;
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 标题栏获焦
	 * @param event
	 * @return
	 */
	public boolean titleKeyEvent(KeyEvent event){
		focusFlag=false;
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_DPAD_UP://上 19
			Log.i(TAG, "无效的操作！");
			return true;
		case KeyEvent.KEYCODE_DPAD_DOWN://下20
			Log.i(TAG, "--KEYCODE_DPAD_DOWN:"+currentIndex);
			if(fragments.get(currentIndex).equals(userf)){
				userf.hasFocusFragMent();
				focusFlag=true;
			}else{
				fragments.get(currentIndex).onKeyDown(event);
				focusFlag=true;
			}
		
			return true;
		case KeyEvent.KEYCODE_DPAD_LEFT://左21
			currentIndex=currentIndex-1;
			if(currentIndex<0){
				currentIndex=(currentIndex + titles.length) % titles.length;
			}else{
				currentIndex=currentIndex % titles.length;
			}
			Log.i(TAG, "--KeyDOWNCode:"+event.getKeyCode()+"--currentIndex:"+currentIndex+"--ll_title-Focused--"+ll_title.isFocused());
			vp_pager.setCurrentItem(currentIndex);
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT://右22
			currentIndex=currentIndex+1;
			currentIndex=currentIndex % titles.length;
			Log.i(TAG, "--Key--DOWN--Code:"+event.getKeyCode()+"----currentIndex:"+currentIndex);
			vp_pager.setCurrentItem(currentIndex);
			return true;
		default:
			break;
		}
		return true;
	}

	/**
	 * fragment获焦
	 * @param index
	 * @param event
	 */
	public void fragmentKeyEvent(int index,KeyEvent event) {
		if(index>-1&&index<titles.length){
			focusFlag=true;
			currentIndex=index;
			vp_pager.setCurrentItem(currentIndex);
		}
	}

	@Override
	public void onClick(View v) {
		vp_pager.setCurrentItem(v.getId());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
