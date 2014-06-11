package com.coship.game_platform.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.coship.game_platform.R;
import com.coship.game_platform.bean.FriendBean;
import com.coship.game_platform.bean.GameInfo;
import com.coship.game_platform.bean.MyNotice;
import com.coship.game_platform.dao.DAOSupport;
import com.coship.game_platform.domain.PlatformConstants;
import com.coship.game_platform.message.MessageManager;
import com.coship.game_platform.view.BadgeView;


public class UserFragment extends ProFragment implements OnTouchListener{
	private String TAG="UserFragment";
	private MainActivity activity;
	private ImageView view1,view2,view3,view4,view5,view6,view7;
	private View currentView,lastImageView;
	private View vFocus;
	private int i;
	private int index ;
	private ArrayList<View> viewcount;
	private int currentIndex = 0;
	private int currentCIndex = 0;
	private View [][]views = new View[3][4];
	private GridLayout gridLayout;
	private ImageView emptyView;
	public BadgeView bView1;
	private FrameLayout layout;
	private int msgCount;
	private MessageManager messageManager;
	private Handler handler;
	private List<MyNotice> msgs = new ArrayList<MyNotice>();
	private DAOSupport<GameInfo> support;
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		activity = (MainActivity)getActivity();
		View rootView = inflater.inflate(R.layout.fragment_user, container, false);
		emptyView = new ImageView(activity);
		gridLayout = (GridLayout) rootView.findViewById(R.id.menu_item);
		handler = new MyHandler();
		messageManager = MessageManager.getInstance();
		messageManager.login("admin", "coship",handler);
		view1 = (ImageView) rootView.findViewById(R.id.im1);
		view1.setOnTouchListener(this);
		view2 = (ImageView) rootView.findViewById(R.id.im2);
		view2.setOnTouchListener(this);
		view3 = (ImageView) rootView.findViewById(R.id.im3);
		view3.setOnTouchListener(this);
		view4 = (ImageView) rootView.findViewById(R.id.im4);
		view4.setOnTouchListener(this);
		bView1 = new BadgeView(rootView.getContext(), view4);
		bView1.setBackgroundResource(R.drawable.badge_ifaux);
	    bView1.setTextSize(14);
        layout = bView1.getContainer();
		view5 = (ImageView) rootView.findViewById(R.id.im5);
		view6 = (ImageView) rootView.findViewById(R.id.im6);
		view7 = (ImageView) rootView.findViewById(R.id.im7);
		views = new View[][]{{view1,view1,view5,view6},{view2,view3,view5,view7},{view2,layout,emptyView,emptyView}};
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			Log.e("onTouch", "ACTION_DOWN");
			switch (v.getId()) {
			case R.id.im1:
				Intent i = new Intent();
				i.setAction("android.intent.action.second");
				startActivity(i);
				break;
			case R.id.im2:
				boolean flag = messageManager.addUser("test33@10.9.20.201","test33 ");
				if(flag){
					Toast.makeText(activity.getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(activity.getApplicationContext(), "不成功", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.im3:
//			    Uri packageURI = Uri.parse("package:com.coship.testnotice");   
//			    Intent uninstallIntent = new Intent
//			    (Intent.ACTION_DELETE, packageURI);   
//			    startActivity(uninstallIntent);  
				Intent i3 = new Intent();
				i3.setClass(activity, MyGameActivity.class);
				startActivity(i3);
				break;
			default:
				break;
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.e("onTouch", "ACTION_UP");
			switch (v.getId()) {
			case R.id.im2:
				view4.setSelected(false);
				break;
			default:
				break;
			}
		}
		return false;
	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void onKeyDown(KeyEvent event) {
		 View v = null;
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_DPAD_DOWN:
				 v = (View) gridLayout.findFocus();
				 if(v != null){
					 vFocus = v;
				 }
				 currentIndex = getViewIndexColumn(vFocus);
				 viewcount = getIconInColumn(currentIndex);
				 Log.e("onKeyDown", viewcount.size()+"");
				 i = viewcount.indexOf(vFocus);
				 index = i+1;
				if(index < viewcount.size()&&index >= 0){
					lastImageView = vFocus;
					currentView = viewcount.get(index);
					scaleAnimation(currentView);   
					clearScaleAnimation(lastImageView);   
						index +=1;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				 v = (View) gridLayout.findFocus();
				 if(v != null){
					 vFocus = v;
				 }
				 currentIndex = getViewIndexColumn(vFocus);
				  viewcount = getIconInColumn(currentIndex);
				  Log.e("onKeyDown", viewcount.size()+"");
				 i = viewcount.indexOf(vFocus);
				 index = i-1;
				 Log.e("onKeyDown", index+"");
				if(index < viewcount.size() && index >= 0){
						lastImageView = vFocus;
						currentView = viewcount.get(index);
						scaleAnimation(currentView);   
						clearScaleAnimation(lastImageView);    
						index -=1;
				}else if(index < 0){
					//ת��title
					activity.focusFlag = false;
					removeFocusFragMent(v);
				}
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				 v = (View) gridLayout.findFocus();
				 if(v != null){
					 vFocus = v;
				 }
				 currentIndex = getViewIndexRow(vFocus);
				  viewcount = getIconInRow(currentIndex);
				  Log.e("onKeyDown", viewcount.size()+"");
				 i = viewcount.indexOf(vFocus);
				 index = i-1;
				 if(index < viewcount.size() && index >= 0){
					lastImageView = vFocus;
					currentView = viewcount.get(index);
					scaleAnimation(currentView);   
					clearScaleAnimation(lastImageView);   
					index -=1;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				v = (View) gridLayout.findFocus();
				 if(v != null){
					 vFocus = v;
				 }
				 currentIndex = getViewIndexRow(vFocus);
				  viewcount = getIconInRow(currentIndex);
				  Log.e("onKeyDown", viewcount.size()+"");
				 i = viewcount.indexOf(vFocus);
				 index = i+1;
				 if(index < viewcount.size() && index >= 0){
					lastImageView = vFocus;
					currentView = viewcount.get(index);
					scaleAnimation(currentView);   
					clearScaleAnimation(lastImageView);   
					index +=1;
				}else if(index == viewcount.size()){
					//ת����һ��fragment
					activity.fragmentKeyEvent(PlatformConstants.GAME_FRAGMENT,event);
				}
				break;
			case KeyEvent.KEYCODE_DPAD_CENTER:
				v = (View) gridLayout.findFocus();
				 if(v != null){
					 vFocus = v;
				 }
				if(vFocus.equals(layout)){
					Intent i = new Intent();
					Bundle mBundle = new Bundle();    
					mBundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) msgs);  
					i.putExtras(mBundle);
					i.setAction("android.intent.action.second");
					startActivityForResult(i, 0);
				}else if(vFocus.equals(view1)){
					TreeMap< String, FriendBean> frs = messageManager.getFriends();
					System.out.println(frs.size());
					List<FriendBean> friList = new ArrayList<FriendBean>();
					//第二种方法
					//这是用TreeMap的keySet()方法，生成的对象是由key对象组成的Set
					//再利用TreeMap的get(key)方法，得到对应的value值
					Iterator titer=frs.entrySet().iterator();  
					while (titer.hasNext()) {
					//it.next()得到的是key，tm.get(key)得到obj
						Map.Entry ent=(Map.Entry )titer.next();  
			            String keyt=ent.getKey().toString();  
			            FriendBean valuet=(FriendBean) ent.getValue();  
			            friList.add((FriendBean) ent.getValue());
			            System.out.println(keyt+"*"+valuet.getjId());  
					}
					System.out.println(friList.get(0).getName()+"*");  
					Intent i = new Intent(activity,UserActivity.class);
					Bundle mBundle = new Bundle();    
					mBundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) friList);  
					i.putExtras(mBundle);
					startActivity(i);
//					boolean flag = messageManager.removeUser("wangjianchun@10.9.20.201");
//					if(flag){
//						Toast.makeText(activity.getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
//					}
				}
			default:
				break;
			}
			}
		
		super.onKeyDown(event);
	}
	
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){
			msgCount = msgCount - data.getIntExtra("count", 0) ;
			if(msgCount == 0){
				bView1.hide(true);
			}else{
				bView1.setText(String.valueOf(msgCount));
		        bView1.toggle(true);
			}
		}
	}
	private ArrayList<View> getIconInColumn(int position)
    {
    	boolean flag = false;
    	ArrayList<View> viewColumns = new ArrayList<View>();
    	for(int i = 0;i < views.length;i++){
    		for(int j = 0;j < views[i].length;j++){
    			flag = false;
    			if(j == position){
    				if(!views[i][j].equals(emptyView)){
    					for (int t = 0; t < viewColumns.size(); t++) {
							if(views[i][j].equals(viewColumns.get(t))){
								flag =true;
								break;
							}
						}
    					if(!flag){
    						viewColumns.add(views[i][j]);
    					}
    				}
    			}
    		}
    	}
        return viewColumns;
    }
    private ArrayList<View> getIconInRow(int position)
    {
    	boolean flag = false;
    	ArrayList<View> viewColumns = new ArrayList<View>();
    	for(int i = 0;i < views[position].length;i++){
    		 flag = false;
    				if(!views[position][i].equals(emptyView)){
    					for (int t = 0; t < viewColumns.size(); t++) {
							if(views[position][i].equals(viewColumns.get(t))){
								flag =true;
								break;
							}
						}
    					if(!flag){
    						viewColumns.add(views[position][i]);
    					}
    		}
    	}
        return viewColumns;
    }
    public int getViewIndexColumn(View v){
    	for(int i = 0;i < views.length;i++){
    		for(int j = 0;j < views[i].length;j++){
    			if(v.equals(views[i][j])){
    				Log.e("onKeyDown", "i="+i+"j="+j);
    				return j;
    			}
    		}
    	}
    	return 0;
    }
    
    public int getViewIndexRow(View v){
    	for(int i = 0;i < views.length;i++){
    		for(int j = 0;j < views[i].length;j++){
    			if(v.equals(views[i][j])){
    				Log.e("onKeyDown", "i="+i+"j="+j);
    				return i;
    			}
    		}
    	}
    	return 0;
    }
	public void hasFocusFragMent(){
		currentView = views[0][0];
		Log.e("onKeyDown", "currentCIndex = "+currentCIndex+"currentIndex = "+currentIndex);
		currentView.setFocusable(true);
		currentView.requestFocus();
		currentView.hasFocus();
		scaleAnimation(currentView);   
	}
	
	public void removeFocusFragMent(View v){
		currentCIndex = 0;
		currentIndex = 0;
		v = (View) gridLayout.findFocus();
		 if(v != null){
			 vFocus = v;
		 }
		clearScaleAnimation(vFocus);   
	}
	public void scaleAnimation(View v){
		
		AnimationSet animationSet1 = new AnimationSet(true); 
		ScaleAnimation scaleAnimation1;
		  scaleAnimation1 = new ScaleAnimation(1f,1.2f,1f,1.2f,   
				Animation.RELATIVE_TO_SELF,0.5f,    
				Animation.RELATIVE_TO_SELF,0.5f);   
		scaleAnimation1.setDuration(500);   
		animationSet1.addAnimation(scaleAnimation1);   
		animationSet1.setFillAfter(true);    
		v.startAnimation(animationSet1);   
		v.bringToFront();
		v.setFocusable(true);
		v.requestFocus();
		v.hasFocus();
	}
	public void clearScaleAnimation(View v){
		AnimationSet animationSet1 = new AnimationSet(true); 
		ScaleAnimation scaleAnimation1;
		  scaleAnimation1 = new ScaleAnimation(1.2f,1f,1.2f,1f,   
				Animation.RELATIVE_TO_SELF,0.5f,    
				Animation.RELATIVE_TO_SELF,0.5f);   
		  scaleAnimation1.setDuration(500);   
			animationSet1.addAnimation(scaleAnimation1);   
			animationSet1.setFillAfter(true);    
			v.startAnimation(animationSet1);   
			v.setFocusable(false);
			v.requestFocus();
			v.hasFocus();
	}
	
	/**
	 * 接收消息handler
	 * @author jc
	 *
	 */
	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(activity.getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(activity.getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				msgCount += 1;
				bView1.setText(String.valueOf(msgCount));
		        bView1.toggle(true);
		        MyNotice message = new MyNotice(msg.obj.toString(),msg.obj.toString(),1);
		        msgs.add(message);
				break;
			default:
				break;
			}
		}
		
	}
}
