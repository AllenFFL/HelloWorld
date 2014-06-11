package com.coship.game_platform.utils;

import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Graphics {

	Canvas c;
	Paint p;

	public Graphics(Canvas canvas, Paint paint) {
		this.c = canvas;
		this.p = paint;
	}

	public void drawRect(int x, int y, int w, int h, int color) {
		if (p != null && c != null) {
			p.setColor(color);
			c.drawRect(x, y, x + w, y + h, p);
		}
	}

	public void drawRect(int x, int y, int w, int h, int a, int r, int g, int b) {
		if (p != null && c != null) {
			p.setARGB(a, r, g, b);
			c.drawRect(x, y, x + w, y + h, p);
		}
	}

	public void drawBitmap(Bitmap bitmap, int x, int y) {
		if (p != null && c != null) {
			c.drawBitmap(bitmap, x, y, null);
		}
	}

	public void drawBitmap(Bitmap bitmap, float x, float y) {
		if (p != null && c != null) {
			c.drawBitmap(bitmap, x, y, null);
		}
	}

	public void drawScaleBitmap(Bitmap bitmap, int x, int y, float sx, float sy) {
		if (p != null && c != null) {
			c.save();
			c.scale(sx, sy);
			c.drawBitmap(bitmap, x, y, null);
			c.restore();
		}
	}

	public void drawMatrixBitmap(Bitmap bitmap, float x, float y, float sx,
			float sy) {
		if (p != null && c != null) {
			c.save();
			Matrix m = new Matrix();
			m.postScale(sx, sy);
			m.postTranslate(x, y);
			c.drawBitmap(bitmap, m, null);
			c.restore();
		}
	}

	public void drawString(String text, int x, int y, int color, int textSize) {
		if (p != null && c != null) {
			p.setColor(color);
			p.setTextSize(textSize);
			c.drawText(text, x, y, p);
		}
	}

	public void drawARGBString(String text, int x, int y, int textSize, int a,
			int r, int g, int b) {
		if (p != null && c != null) {
			p.setARGB(a, r, g, b);
			p.setTextSize(textSize);
			c.drawText(text, x, y, p);
		}
	}

	public void drawLineString(String text, int x, int y, int color,
			int textSize, int widthLine, int padding) {
		if (p != null && c != null) {
			if (text != null && text.length() > 0) {
				p.setColor(color);
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
				for (int i = 0; i < v.size(); i++) {
					c.drawText(v.elementAt(i), x, y + i * (textSize + padding),
							p);
				}
			}
		}
	}

	public void drawLineString(String text, int x, int y, int color,
			int textSize, int widthLine, int padding, int showPage, int showLine) {
		if (p != null && c != null) {
			if (text != null && text.length() > 0) {
				showPage--;
				p.setColor(color);
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
				for (int i = showPage * showLine; i < (showPage + 1) * showLine; i++) {
					if (i >= 0 && i < v.size()) {
						c.drawText(v.elementAt(i), x, y
								+ (i - showPage * showLine)
								* (textSize + padding), p);
					}
				}
			}
		}
	}

	public Canvas getCanvas() {
		return c;
	}

	public Paint getPaint() {
		return p;
	}

	public int getTextSize() {
		return (int) p.getTextSize();
	}

	public void setTextSize(int size) {
		p.setTextSize(size);
	}

}
