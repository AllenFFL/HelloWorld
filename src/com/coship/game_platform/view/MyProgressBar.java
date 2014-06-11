package com.coship.game_platform.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyProgressBar extends View {
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); 
	
	public MyProgressBar(Context context){
		super(context);
		initPaint();
	}

	public MyProgressBar(Context context, AttributeSet attrs){
		super(context, attrs);
		initPaint();
	}
	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	private void initPaint() {
		// TODO Auto-generated method stub
		
	}
	
	
}
