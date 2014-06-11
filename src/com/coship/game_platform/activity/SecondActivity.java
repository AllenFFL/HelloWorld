package com.coship.game_platform.activity;

import java.util.ArrayList;
import java.util.List;

import com.coship.game_platform.R;
import com.coship.game_platform.bean.MyNotice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends Activity {

	ListView view;
	String []strings;
	int count = 0;
	List<MyNotice> messages = new ArrayList<MyNotice>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		Intent i = getIntent();
		messages = i.getExtras().getParcelableArrayList("list");
		view = (ListView) findViewById(R.id.listview);
		final MyAdapter adapter = new MyAdapter();
		view.setAdapter(adapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(messages.get(arg2).getIsRead()==2){
					
				}else{
					count++;
					Toast.makeText(SecondActivity.this, messages.get(arg2).getContent(), 1).show();
					messages.get(arg2).setIsRead(2);
				}
			}
		});
	}
    private class MyAdapter extends BaseAdapter{        
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView mTextView = new TextView(getApplicationContext());
            mTextView.setText(messages.get(position).getTitle());
            mTextView.setTextSize(15);
            mTextView.setTextColor(Color.BLUE);
            
            return mTextView;
        }
        
    }
    /**
	 * 按键处理
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		switch (event.getAction()) {
		case KeyEvent.ACTION_DOWN:
			if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
				 Intent intent = new Intent();  
	                intent.putExtra("count", count);  
	                setResult(RESULT_CANCELED, intent);  
	                finish();  
			}
			return true;
		case KeyEvent.ACTION_UP:
			return true;
		default:
			break;
		}
		return super.dispatchKeyEvent(event);
	}

    
    
}
