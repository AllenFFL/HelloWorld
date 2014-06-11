package com.coship.game_platform.activity;


import com.coship.game_platform.R;
import com.coship.game_platform.message.MessageManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class ChatActivity extends Activity {
	private MessageManager messageManager;
	private Handler handler;
	private TextView textView;
	private EditText input;
	private String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		ShareSDK.initSDK(this);
		setContentView(R.layout.chat);
		textView = (TextView) findViewById(R.id.t);
		input = (EditText) findViewById(R.id.input);
		name = getIntent().getStringExtra("userName");
		handler = new MyHandler();
		messageManager = MessageManager.getInstance();
		messageManager.getMessage(handler);
		Button chat = (Button) findViewById(R.id.chat);
		chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String string = input.getText().toString();
				textView.append("\n");
				textView.append(string);
				messageManager.sendMessage(name, string);
			}
		});
	}
	
	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 2:
				Toast.makeText(ChatActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				textView.append("\n");
				textView.append(msg.obj.toString());
				break;
			default:
				break;
			}
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}