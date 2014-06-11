package com.coship.game_platform.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.coship.game_platform.R;

public class FilmFragment extends ProFragment implements OnTouchListener {
	private String TAG = "NewsFragment";
	private MainActivity activity;

	ImageView[] imgView;
	ImageView tempImgView;

	int[] ids = { R.id.newImage0, R.id.newImage1, R.id.newImage2,
			R.id.newImage3, R.id.newImage4, R.id.newImage5, R.id.newImage6,
			R.id.newImage7, R.id.newImage8 };

	public static int focus;
	public static boolean isGetFocus;

	HorizontalScrollView horizontalScrollView;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (MainActivity) getActivity();
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);
		imgView = new ImageView[ids.length];
		for (int i = 0; i < imgView.length; i++) {
			imgView[i] = (ImageView) rootView.findViewById(ids[i]);
			imgView[i].setOnTouchListener(this);
		}
		// 水平滑动布局
		horizontalScrollView = (HorizontalScrollView) rootView
				.findViewById(R.id.horizon);
		horizontalScrollView.setSmoothScrollingEnabled(true);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onKeyDown(KeyEvent event) {
		Log.i(TAG, "--onKeyDown--:" + event.getKeyCode());
		if (!isGetFocus && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
			isGetFocus = true;
			focus = 0;
			setAni(imgView[0]);
		} else {
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_DPAD_CENTER:
			case 66:
				exit(focus);
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if (focus > 2) {
					focus -= 2;
					setAni(imgView[focus]);
				} else if (focus == 1 || focus == 2) {
					focus = 0;
					setAni(imgView[0]);
					horizontalScrollView.setScrollX(0);
					horizontalScrollView.scrollBy(
							horizontalScrollView.getScrollX(),
							horizontalScrollView.getScrollY());
				} else {
					activity.fragmentKeyEvent(1, event);
				}
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if (focus == 0 && ids.length > 1) {
					focus = 1;
					setAni(imgView[1]);
				} else if (focus + 2 < ids.length) {
					focus += 2;
					setAni(imgView[focus]);
					if (focus > ids.length - 3) {
						horizontalScrollView.setScrollX(horizontalScrollView
								.getMaxScrollAmount());
						horizontalScrollView.scrollBy(
								horizontalScrollView.getScrollX(),
								horizontalScrollView.getScrollY());
					}
				} else {
					activity.fragmentKeyEvent(0, event);
				}
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				if (focus > 0 && focus % 2 == 0) {
					focus--;
					setAni(imgView[focus]);
				} else {
					// 焦点切换到标题
					clearAni(imgView[focus]);
					focus = 0;
					isGetFocus = false;
					tempImgView = null;
					activity.titleKeyEvent(event);
				}
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				if (focus > 0) {
					if (focus % 2 == 1 && focus + 1 < ids.length) {
						focus++;
						setAni(imgView[focus]);
					} else {
						// 焦点到达底部
					}
				}
				break;
			}
		}
		super.onKeyDown(event);
	}

	public void exit(int position) {
		Intent intent = new Intent(activity, NewsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void setAni(ImageView imgView) {
		if (tempImgView != null && isGetFocus) {
			clearAni(tempImgView);
		}
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(200);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setFillAfter(true);
		imgView.startAnimation(animationSet);
		imgView.bringToFront();
		imgView.setFocusable(true);
		imgView.requestFocus();
		imgView.hasFocus();
		tempImgView = imgView;
	}

	public void clearAni(ImageView imgView) {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.2f, 1.0f, 1.2f,
				1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(200);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setFillAfter(true);
		imgView.startAnimation(animationSet);
		imgView.setFocusable(false);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			System.out.println("取消");
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			System.out.println("抬起");
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("按下");
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] == v.getId()) {
					setAni(imgView[i]);
					break;
				}
			}
		}
		return false;
	}

}
