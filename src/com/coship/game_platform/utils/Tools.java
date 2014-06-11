package com.coship.game_platform.utils;

import java.io.InputStream;
import java.util.Vector;

import com.coship.game_platform.activity.NewsActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class Tools {

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static float sx, sy;

	public static Bitmap createBitmap(String name) {
		Bitmap bmp = BitmapFactory.decodeStream(getInputStreamFromRes(name));
		return bmp;
	}

	public static Bitmap createBitmap(int id) {
		Bitmap bmp = BitmapFactory.decodeResource(NewsActivity.getInstance()
				.getResources(), id);
		return bmp;
	}

	public static InputStream getInputStreamFromRes(String fileName) {
		AssetManager am = NewsActivity.getInstance().getAssets();
		try {
			InputStream is = am.open(fileName);
			return is;
		} catch (Exception e) {
			throw new RuntimeException("error loading file " + fileName, e);
		}
	}

	public static int getStringLine(String text, int textSize, int widthLine) {
		if (text != null && text.length() > 0) {
			Paint p = new Paint();
			p.setTextSize(textSize);
			Vector<String> v = new Vector<String>();
			String str = "";
			String tempStr = "";
			float width = 0;
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (c == '\n') {
					v.addElement(str);
					str = "";
				} else {
					tempStr = str + c;
					width = p.measureText(tempStr);
					if (width < widthLine) {
						str += c;
						if (i == text.length() - 1) {
							v.addElement(str);
						}
					} else {
						v.addElement(str);
						str = "" + c;
					}
				}
			}
			return v.size();
		}
		return 0;
	}
	public static void largeAni(View currentView) {
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
	}
	public static void clearAni(View imgView) {
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
}
