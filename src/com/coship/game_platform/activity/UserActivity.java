package com.coship.game_platform.activity;

import java.util.ArrayList;
import java.util.List;

import com.coship.game_platform.R;
import com.coship.game_platform.bean.FriendBean;
import com.coship.game_platform.bean.MyNotice;
import com.coship.game_platform.message.MessageManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UserActivity extends Activity {
	ListView view;
	String []strings;
	int count = 0;
	MessageManager mesManager;
	List<FriendBean> friends = new ArrayList<FriendBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		Intent i = getIntent();
		friends = i.getExtras().getParcelableArrayList("list");
		view = (ListView) findViewById(R.id.listview);
		final MyAdapter adapter = new MyAdapter();
		view.setAdapter(adapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(UserActivity.this,ChatActivity.class);
				i.putExtra("userName", friends.get(arg2).getName());
				startActivity(i);
			}
		});
	}
	private class MyAdapter extends BaseAdapter{        
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return friends.size();
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
            mTextView.setText(friends.get(position).getName());
            mTextView.setTextSize(15);
            
            return mTextView;
        }
        
    }
}