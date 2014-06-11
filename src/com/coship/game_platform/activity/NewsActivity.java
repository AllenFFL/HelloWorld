package com.coship.game_platform.activity;

import com.coship.game_platform.view.NewsCanvas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class NewsActivity extends Activity {

	public static NewsActivity instance;
	NewsCanvas mc;
	public int focus;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		instance = this;

		Bundle bundle = this.getIntent().getExtras();
		focus = bundle.getInt("position");

		mc = new NewsCanvas(this);
		setContentView(mc);
	}

	public static NewsActivity getInstance() {
		return instance;
	}

	private boolean isKeyDown;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (!isKeyDown) {
			mc.keyDown(keyCode);
			isKeyDown = true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		isKeyDown = false;
		return false;
	}

	public void exit() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}