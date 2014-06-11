package com.coship.game_platform.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.KeyEvent;

import com.coship.game_platform.activity.NewsActivity;
import com.coship.game_platform.domain.NewsData;
import com.coship.game_platform.utils.Graphics;
import com.coship.game_platform.utils.Tools;

public class NewsScene {

	public static int focusInfoContent;// 选择内容焦点

	private Bitmap bmpNews, bmpNewsNull, bmpSure, bmpBack;// 新闻图片、load图片、确定键、返回键
	private Bitmap bmpArrowL, bmpArrowR;// 箭头
	public int focusBmp;// 选择图片焦点
	private int newsBmpCenterX = 400;// 图片显示中心点X坐标
	private final int fontSize_Title = 40;// 标题文字大小
	private final int fontSize_Content = 20;// 内容文字大小
	private final int content_Padding = 20;// 内容间距
	private final int content_Width = 400;// 内容宽度
	private final int showLine = 7;// 内容显示行数
	private int showPage = 1, totalPage = 1;// 显示內容第几页/共有几页
	private int showBmpPage = 1, totalBmpPage = 1;// 显示图片页数/共有图片页数
	private String news_title, news_time, news_comefrom, news_content;// 标题、时间、来源、内容
	private float scale = 1.5f;// 查看大图缩放比例

	private int state = 0;// 查看状态
	private final int state_ShowContent = 0;// 查看内容状态
	private final int state_ShowBmp = 1;// 查看大图状态

	public NewsScene() {
		focusInfoContent = NewsActivity.getInstance().focus;
		initInternetData();
		init();
	}

	public void initInternetData() {
		NewsData.news_BmpName = new String[NewsData.news_TotalBmpNum];
		for (int i = 0; i < NewsData.news_BmpName.length; i++) {
			NewsData.news_BmpName[i] = "news/news_" + i+ ".jpg";
		}
		NewsData.news_InfoContent = new String[NewsData.info_TotalNum][4];
		for (int i = 0; i < NewsData.news_InfoContent.length; i++) {
			NewsData.news_InfoContent[i][0] = "匿名马来西亚调查人员：机长系客机失联唯一元凶";
			NewsData.news_InfoContent[i][1] = (10 + i * 3) + "分钟前";
			NewsData.news_InfoContent[i][2] = "来源于环球网";
			NewsData.news_InfoContent[i][3] = "据《今日美国报》报道，3月26日，一名参与马航MH370失联客机调查的马来西亚执法人员对该报表示，调查人员相信机长扎哈里是飞机失联的唯一元凶。这名隶属于马来西亚警局特别调查小组的高级官员称，没有任何证据显示，飞机出现过机械故障或遭到某一位乘客劫持。这名匿名官员说，调查人员正在对扎哈里家属进行问询，试图了解3月8日执行飞行任务前扎哈里的行为。";
		}

		NewsData.news_InfoContent[2][3] = "据《今日美国报》报道，3月26日，一名参与马航MH370失联客机调查的马来西亚执法人员对该报表示，调查人员相信机长扎哈里是飞机失联的唯一元凶。";
		NewsData.news_InfoContent[4][3] = "据《今日美国报》报道，3月26日\n，一名参与马航MH370失联客机调查的马来西亚执法人员对该报表示，调查人员相信机长扎哈里是飞机失联的唯\n一元凶。这名隶属于马来西亚警局特别调查小组的高级\n官员称，没有任何证据显示，飞\n机出现过机械故障或遭到某一位乘客劫持。这名匿名官员说，调查人员正在对扎哈里家属进行问询，试图了解3月8日执行飞行任务前扎哈\n里的行为。据《今日美国报》报道，3月26日，一名参与马航MH370失联客机调查的马来西亚执法人员对该报表示，调查人员相信机长扎哈里是飞机失联的唯一元凶。这名隶属于马来西亚警局特别调查小组的高级官员称，没有任\n何证据显示，飞机出现过机械故障或\n遭到某一位乘客劫持。这名匿名官员说，调查人员正\n在对扎哈里家属进行问询，试图了解3月8日\n执行飞行任务前扎哈里\n的行为。";

		initContent(focusInfoContent);
		for (int i = 0; i < focusInfoContent; i++) {
			focusBmp += NewsData.news_EachNum[i];
		}
	}

	public void init() {
		bmpNewsNull = Tools.createBitmap("news/news_null.png");
		bmpSure = Tools.createBitmap("news/sure.png");
		bmpBack = Tools.createBitmap("news/back.png");
		bmpArrowL = Tools.createBitmap("news/arrow_left.png");
		bmpArrowR = Tools.createBitmap("news/arrow_right.png");
		initNewsBmp();
	}

	public void initContent(int focus) {
		news_title = NewsData.news_InfoContent[focus][0];
		news_time = NewsData.news_InfoContent[focus][1];
		news_comefrom = NewsData.news_InfoContent[focus][2];
		news_content = NewsData.news_InfoContent[focus][3];
		showPage = 1;// 设置内容为第一页
		totalPage = Tools.getStringLine(news_content, fontSize_Content,
				content_Width) / showLine + 1;// 获取内容总页数
		totalBmpPage = NewsData.news_EachNum[focus];// 该条资讯的图片数量
	}

	private void initNewsBmp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (bmpNews != null) {
					bmpNews.recycle();
					bmpNews = null;
				}
				bmpNews = Tools.createBitmap(NewsData.news_BmpName[focusBmp]);
			}
		}).start();
	}

	public void draw(Graphics g) {
		g.drawRect(0, 0, Tools.SCREEN_WIDTH, Tools.SCREEN_HEIGHT, Color.GRAY);
		switch (state) {
		case state_ShowContent:
			if (bmpNews == null) {
				g.drawBitmap(bmpNewsNull,
						newsBmpCenterX - bmpNewsNull.getWidth() / 2,
						(Tools.SCREEN_HEIGHT - bmpNewsNull.getHeight()) >> 1);
			} else {
				g.drawBitmap(bmpNews, newsBmpCenterX - bmpNews.getWidth() / 2,
						(Tools.SCREEN_HEIGHT - bmpNews.getHeight()) >> 1);
			}
			g.drawBitmap(bmpSure, newsBmpCenterX - bmpSure.getWidth() / 2,
					Tools.SCREEN_HEIGHT - bmpSure.getHeight() - 20);

			g.drawRect(800, 20, 3, Tools.SCREEN_HEIGHT - 40, Color.DKGRAY);
			g.drawRect(
					830,
					60,
					3,
					(fontSize_Title + content_Padding)
							* Tools.getStringLine(news_title, fontSize_Title,
									content_Width) + fontSize_Title, Color.RED);

			// 标题
			g.drawLineString(news_title, 850, 100, Color.BLACK, fontSize_Title,
					content_Width, content_Padding);

			// 时间
			g.drawLineString(
					news_time,
					850,
					100
							+ (fontSize_Title + content_Padding)
							* Tools.getStringLine(news_title, fontSize_Title,
									content_Width) - content_Padding,
					Color.BLACK, fontSize_Content, content_Width,
					content_Padding);
			// 来源
			g.drawLineString(
					news_comefrom,
					850 + 200,
					100
							+ (fontSize_Title + content_Padding)
							* Tools.getStringLine(news_title, fontSize_Title,
									content_Width) - content_Padding,
					Color.BLACK, fontSize_Content, content_Width,
					content_Padding);

			// 内容
			if (showBmpPage == 1) {
				g.drawLineString(news_content, 850, 400, Color.BLACK,
						fontSize_Content, content_Width, content_Padding,
						showPage, showLine);

				// 滚轮
				if (totalPage > 1) {
					g.drawRect(850 + content_Width + 10, 400 - fontSize_Content
							* 2 - fontSize_Content / 2, 1,
							300 + fontSize_Content, 127, 173, 216, 230);

					g.drawRect(850 + content_Width + 10 - 1, 400
							- fontSize_Content * 2 + 300 * (showPage - 1)
							/ totalPage, 3, 300 / totalPage, 127, 250, 250, 210);
				}
			}
			break;
		case state_ShowBmp:
			if (bmpNews == null) {
				g.drawBitmap(bmpNewsNull,
						(Tools.SCREEN_WIDTH - bmpNewsNull.getWidth()) / 2,
						(Tools.SCREEN_HEIGHT - bmpNewsNull.getHeight()) >> 1);
			} else {
				g.drawMatrixBitmap(
						bmpNews,
						(Tools.SCREEN_WIDTH - bmpNews.getWidth() * scale) / 2,
						(Tools.SCREEN_HEIGHT - bmpNews.getHeight() * scale) / 2,
						scale, scale);
			}
			g.drawBitmap(bmpBack,
					(Tools.SCREEN_WIDTH - bmpBack.getWidth()) / 2,
					Tools.SCREEN_HEIGHT - bmpBack.getHeight() - 20);
			break;
		}
		if (focusBmp > 0) {
			g.drawBitmap(bmpArrowL, 0,
					Tools.SCREEN_HEIGHT - bmpArrowL.getHeight());
		}
		if (focusBmp < NewsData.news_TotalBmpNum - 1) {
			g.drawBitmap(bmpArrowR, Tools.SCREEN_WIDTH - bmpArrowR.getWidth(),
					Tools.SCREEN_HEIGHT - bmpArrowR.getHeight());
		}
		// g.drawString("文本:" + showPage + "/" + totalPage, 900, 690,
		// Color.WHITE,
		// 30);
		g.drawARGBString(showBmpPage + "/" + totalBmpPage, 1000, 700, 30, 127,
				245, 245, 200);
	}

	public void run() {
	}

	public void keyDown(int keyCode) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_ENTER:
			if (state == state_ShowContent && bmpNews != null) {
				state = state_ShowBmp;
			} else {
				state = state_ShowContent;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if (focusBmp > 0) {
				focusBmp--;
				showBmpPage--;
				checkFocusInfoContent();
				initNewsBmp();
			}
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (focusBmp < NewsData.news_TotalBmpNum - 1) {
				focusBmp++;
				showBmpPage++;
				checkFocusInfoContent();
				initNewsBmp();
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			if (state == state_ShowContent) {
				if (showBmpPage == 1) {
					if (showPage > 1) {
						showPage--;
					}
				}
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			if (state == state_ShowContent) {
				if (showBmpPage == 1) {
					if (showPage < totalPage) {
						showPage++;
					}
				}
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			if (state == state_ShowContent) {
				NewsActivity.getInstance().finish();
			} else {
				state = state_ShowContent;
			}
			break;
		}
	}

	/**
	 * 检测是否更换资讯内容
	 * 
	 * @param text
	 */
	private void checkFocusInfoContent() {
		for (int i = 0; i < NewsData.news_EachNum.length; i++) {
			if (focusBmp < getTotalEachNum(i)) {
				if (focusInfoContent != i) {
					if (focusInfoContent < i) {
						showBmpPage = 1;
					} else {
						showBmpPage = NewsData.news_EachNum[i];
					}
					focusInfoContent = i;
					initContent(focusInfoContent);
				}
				break;
			}
		}
	}

	private int getTotalEachNum(int index) {
		int total = 0;
		for (int i = 0; i < index + 1; i++) {
			total += NewsData.news_EachNum[i];
		}
		return total;
	}

	public void free() {
		if (bmpNews != null) {
			bmpNews.recycle();
			bmpNews = null;
		}
		if (bmpNewsNull != null) {
			bmpNewsNull.recycle();
			bmpNewsNull = null;
		}
		if (bmpSure != null) {
			bmpSure.recycle();
			bmpSure = null;
		}
		if (bmpBack != null) {
			bmpBack.recycle();
			bmpBack = null;
		}
		if (bmpArrowL != null) {
			bmpArrowL.recycle();
			bmpArrowL = null;
		}
		if (bmpArrowR != null) {
			bmpArrowR.recycle();
			bmpArrowR = null;
		}
		NewsData.news_BmpName = null;
	}

}
