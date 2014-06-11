package com.coship.game_platform.activity;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.coship.game_platform.R;
import com.coship.game_platform.bean.AppInfo;
import com.coship.game_platform.utils.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;

public class GameActivity extends Activity {
	private static String TAG="GameActivity";
	private View bt_dowload;
	private View im_detail;
	private ProgressBar pb_progress;
	private AppInfo appinfo;
	private View tv_name;
	private View tv_game;
	private View tv_time;
	private View tv_version;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_detail);
		initData();
		findViewById();
//		initView();
		im_detail.bringToFront();
		im_detail.setFocusable(true);
		im_detail.requestFocus();
		im_detail.hasFocus();
	}

	private void initData() {
		appinfo=new AppInfo();
		appinfo.setGameName("我叫MT");
		appinfo.setAppDetail("《我叫MT》是一部由七彩映画工作室出品的原创3D网络动画，被众多网友冠之以“国产动画新光芒”的动画剧集。该动画是以魔兽为核心的人气旺盛的同人网络动画，其原型是暴雪公司著名的网络游戏《魔兽世界》。");
		appinfo.setDataUrl("http://gdown.baidu.com/data/wisegame/fd5dbbf49f75cb1c/wojiaoMT_3520.apk");
		appinfo.setPageUrl("http://c.hiphotos.bdimg.com/wisegame/pic/item/15094b36acaf2edda9f6754a8f1001e939019309.jpg");
//http://e.hiphotos.bdimg.com/wisegame/pic/item/b6628535e5dde7118cd57f55a5efce1b9d166109.jpg
//http://a.hiphotos.bdimg.com/wisegame/pic/item/d01c8701a18b87d68f0f97bc050828381e30fdc0.jpg
//http://b.hiphotos.bdimg.com/wisegame/pic/item/8a24b899a9014c08df761406087b02087af4f4c0.jpg
//http://e.hiphotos.bdimg.com/wisegame/pic/item/d23533fa828ba61eece9f48c4334970a304e5909.jpg
//http://h.hiphotos.bdimg.com/wisegame/pic/item/b2c8a786c9177f3e2833c0ae72cf3bc79f3d5609.jpg
	}

	private void findViewById() {
		im_detail = findViewById(R.id.im_detail);
		bt_dowload = findViewById(R.id.bt_dowload);
		tv_name = findViewById(R.id.tv_name);
		tv_game = findViewById(R.id.tv_game);
		tv_time = findViewById(R.id.tv_time);
		tv_version = findViewById(R.id.tv_version);
		pb_progress=(ProgressBar)findViewById(R.id.pb_progress);
	}
	@Override
	public boolean dispatchKeyEvent(android.view.KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_DPAD_UP:
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			break;
		case KeyEvent.KEYCODE_ENTER://确定
		case KeyEvent.KEYCODE_DPAD_CENTER:
			Tools.largeAni(im_detail);
			File dir = this.getDir("newapp", Context.MODE_WORLD_READABLE);
			Log.i(TAG, "------确定键----------"+dir.getAbsolutePath());
//			bt_dowload.performClick();
//			/data/data/com.coship.game_platform/app_newapp
//			if (Environment.getExternalStorageState().equals(
//					Environment.MEDIA_MOUNTED)) {
			String filedir="/data/app/";
				final File file = new File(filedir,"update.apk");
				FinalHttp finalHttp = new FinalHttp();// 多线程断点下载框架
				Log.i(TAG, "-----onLoading----------"+Environment.getDataDirectory());
				finalHttp.download(appinfo.getDataUrl(),"/data/app/", 
						new AjaxCallBack<File>() {
							@Override
							public void onLoading(long count, long current) {
								Log.i(TAG, "-----onLoading----------"+file.getAbsolutePath());
								pb_progress.setVisibility(View.VISIBLE);
								pb_progress.setMax((int)count);
								int progress=(int) (current*100 % count);
								pb_progress.setProgress(progress);
								super.onLoading(count, current);
							}
							@Override
							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								// TODO Auto-generated method stub
								super.onFailure(t, errorNo, strMsg);
							}
							@Override
							public void onSuccess(File t) {
								//安装app
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setDataAndType(Uri.fromFile(t),
										"application/vnd.android.package-archive");
								startActivity(intent);
								super.onSuccess(t);
							}
						});
			break;
		default:
			break;
	}
		return super.dispatchKeyEvent(event);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i(TAG, "------确定键eeeeee----------");
		return super.onKeyDown(keyCode, event);
	}
//	AsyncTask<Params, Progress, Result>{
//		
//	}
	private void exit() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
