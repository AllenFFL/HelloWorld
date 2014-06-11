package com.coship.game_platform.view;

import com.coship.game_platform.utils.Graphics;
import com.coship.game_platform.utils.Tools;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class NewsCanvas extends SurfaceView implements Runnable, Callback {

	public static NewsCanvas mc;
	private Thread m_thread;
	private SurfaceHolder sfh;
	private boolean isRun;
	private int gcLoop;

	/**************** 场景状态 *********************/
	private boolean isLoading;// 正在加载ing
	private byte scene_NeedState = 0;// 需要切换的场景状态
	private byte scene_State = -1;// 当前场景状态
	public static final byte Scene_News = 0;// 资讯详情状态

	/**************** 场景对象 *********************/
	private NewsScene newsScene;// 详情场景

	public NewsCanvas(Context context) {
		super(context);
		mc = this;
		sfh = this.getHolder();
		sfh.addCallback(this);
		setsSceneNeedState(Scene_News);
	}

	@Override
	public void run() {
		Paint p = new Paint();
		Canvas canvas = null;
		Graphics g = null;
		while (isRun) {
			canvas = sfh.lockCanvas();
			g = new Graphics(canvas, p);
			try {
				drawScene(g);
				runScene();
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			}
			gcLoop++;
			if (gcLoop > 500) {
				gcLoop = 0;
				System.gc();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		m_thread = new Thread(this);
		m_thread.start();
		isRun = true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRun = false;
	}

	private void replaceScene(final byte replaceScene) {
		isLoading = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				switch (scene_State) {
				case Scene_News:
					newsScene.free();
					newsScene = null;
					break;
				}
				scene_State = replaceScene;
				switch (scene_State) {
				case Scene_News:
					newsScene = new NewsScene();
					break;
				}
				isLoading = false;
			}
		}).start();
	}

	public void setsSceneNeedState(byte state) {
		scene_NeedState = state;
	}

	/**
	 * 绘制场景
	 * 
	 * @param c
	 */
	private void drawScene(Graphics g) {
		g.drawRect(0, 0, Tools.SCREEN_WIDTH, Tools.SCREEN_HEIGHT, 0);
		if (isLoading) {
			g.drawString("加载中...", Tools.SCREEN_WIDTH / 2 - 60,
					(Tools.SCREEN_HEIGHT - 30) >> 1, Color.WHITE, 30);
		} else {
			switch (scene_State) {
			case Scene_News:
				newsScene.draw(g);
				break;
			}
		}
	}

	/**
	 * 场景逻辑
	 */
	private void runScene() {
		if (scene_NeedState != -88) {
			replaceScene(scene_NeedState);
			scene_NeedState = -88;
			return;
		}
		if (!isLoading) {
			switch (scene_State) {
			case Scene_News:
				newsScene.run();
				break;
			}
		}
	}

	public void keyDown(int keyCode) {
		if (!isLoading) {
			switch (scene_State) {
			case Scene_News:
				newsScene.keyDown(keyCode);
				break;
			}
		}
	}

}
