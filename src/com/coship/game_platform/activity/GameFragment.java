package com.coship.game_platform.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coship.game_platform.R;
import com.coship.game_platform.domain.PlatformConstants;

public class GameFragment extends ProFragment implements OnTouchListener {

	private String TAG = "GameFragment";
	private MainActivity activity;

	private ImageView iv_app1, iv_app2, iv_app3, iv_app4;
	private int[] ids = new int[] { R.id.iv_app1, R.id.iv_app2, R.id.iv_app3,
			R.id.iv_app4 };
	private ArrayList<ImageView> items = new ArrayList<ImageView>(ids.length);
	private View vp_game;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (MainActivity) getActivity();
		View rootView = inflater.inflate(R.layout.fragment_game, container,
				false);
		vp_game = rootView.findViewById(R.id.vp_game);
		for (int i = 0; i < ids.length; i++) {
			ImageView img = (ImageView) rootView.findViewById(ids[i]);
			img.setOnTouchListener(this);
			items.add(img);
		}
		;
		// ��ݴ���
		// Bundle bundle = getArguments();
		// button1.setText(bundle.getString(PlatformConstants.ARGUMENTS_NAME,
		// ""));
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	private int index = 0;
	private ImageView currentView = null;

	@Override
	public void onKeyDown(KeyEvent event) {
		Log.i(TAG, "--onKeyDown--:" + event.getKeyCode());
		if (currentView != null) {
			clearAni(currentView);
		}
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_DPAD_UP:
			if (index > -1 && index < ids.length/2) {
				activity.titleKeyEvent(event);
			} else {
				index = index - ids.length/2;
				largeAni();
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (index == 0 || index > ids.length) {
				largeAni();
			} else {
				index = index + ids.length;
				largeAni();
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			index--;
			if (index < 0) {
				index = 0;
				activity.fragmentKeyEvent(PlatformConstants.USER_FRAGMENT,
						event);
			} else if (index == ids.length) {
				index = 0;
				largeAni();
			} else {
				largeAni();
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			index++;
			if (index == 7 || index == 13) {
				index = 0;
				activity.fragmentKeyEvent(PlatformConstants.NEWS_FRAGMENT,
						event);
			} else {
				index = index % items.size();
				largeAni();
			}
			break;
		case KeyEvent.KEYCODE_ENTER:// 确定
		case KeyEvent.KEYCODE_DPAD_CENTER:
			// 打开游戏详情页①下载图片 ②填充页面
			Intent intent = new Intent(activity, GameActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("index", index);
			intent.putExtras(bundle);
			startActivity(intent);
			//
			// Log.i(TAG, "确定键 选中app");
			// Bundle bundle=new Bundle();
			// bundle.putInt("app", index);
			// Intent intent = new Intent(activity, GameFragment.class);
			// intent.putExtras(bundle);
			// startActivity(intent);
			break;
		case KeyEvent.KEYCODE_BACK:// 返回
			activity.fragmentKeyEvent(PlatformConstants.USER_FRAGMENT, event);
			break;
		default:
			break;
		}
		super.onKeyDown(event);
	}

	private void largeAni() {
		if (currentView != null) {
			clearAni(currentView);
		}
		currentView = items.get(index);
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(100);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setFillAfter(true);
		currentView.startAnimation(animationSet);
		currentView.bringToFront();
		currentView.setFocusable(true);
		currentView.requestFocus();
		currentView.hasFocus();
		// currentView.bringToFront();
	}

	public void clearAni(ImageView imgView) {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 1.0f, 1.2f,
				1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(100);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setFillAfter(true);
		imgView.startAnimation(animationSet);
		imgView.setFocusable(false);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
