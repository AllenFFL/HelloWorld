package com.coship.game_platform.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.coship.game_platform.R;
import com.coship.game_platform.bean.GameInfo;
import com.coship.game_platform.dao.DAOSupport;
import com.coship.game_platform.dao.GameDAO;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class MyGameActivity extends Activity implements OnKeyListener{

	private GameDAO gameDao;
	List<GameInfo> gameInfos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_game);
		gameDao = GameDAO.getInstance(this);
		gameDao.delAllGameList();
		GameInfo gameInfo1 = new GameInfo("game1", "com.coship.testnotice", "com.coship.testnotice.MainActivity", null);
		gameDao.insertGame(gameInfo1);
		GameInfo gameInfo2 = new GameInfo("game2", "com.coship.testnotice", "com.coship.testnotice.MainActivity", null);
		gameDao.insertGame(gameInfo2);
		GameInfo gameInfo3 = new GameInfo("game3", "com.coship.testnotice", "com.coship.testnotice.MainActivity", null);
		gameDao.insertGame(gameInfo3);
		gameInfos = gameDao.getAllGameInfoDB();
		System.out.println(gameInfos.size());
		  GridView gridview = (GridView) findViewById(R.id.gridview);  
	      //生成动态数组，并且转入数据  
	      ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
	      for(int i=0;i<gameInfos.size();i++)  
	      {  
		        HashMap<String, Object> map = new HashMap<String, Object>();  
		        map.put("ItemImage", R.drawable.ic_launcher);//添加图像资源的ID  
		        map.put("ItemText", gameInfos.get(i).getGameName());//按序号做ItemText  
		        lstImageItem.add(map);  
	      }  
	      //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应  
	      SimpleAdapter saImageItems = new SimpleAdapter(this, //没什么解释  
	                                                lstImageItem,//数据来源   
	                                                R.layout.game_item,//night_item的XML实现  
	                                                  
	                                                //动态数组与ImageItem对应的子项          
	                                                new String[] {"ItemImage","ItemText"},   
	                                                  
	                                                //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
	                                                new int[] {R.id.ItemImage,R.id.ItemText});  
	      //添加并且显示  
	      gridview.setAdapter(saImageItems);  
	      gridview.setOnKeyListener(this);
	      //添加消息处理  
	      gridview.setOnItemClickListener(new ItemClickListener());  
	  }  
	    
	  //当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件  
	  class  ItemClickListener implements OnItemClickListener  
	  {  
		  @Override
	public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened   
	                                  View arg1,//The view within the AdapterView that was clicked  
	                                  int arg2,//The position of the view in the adapter  
	                                  long arg3//The row id of the item that was clicked  
	                                  ) {  
	    //在本例中arg2=arg3  
	    HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
	    //显示所选Item的ItemText  
	   String name = (String) item.get("ItemText");  
	   System.out.println(name);
	   for (GameInfo gameInfo : gameInfos) {
		if(gameInfo.getGameName().equals(name)){
			Intent i = getPackageManager().getLaunchIntentForPackage(gameInfo.getDataUrl());
			startActivity(i);
//		    Uri packageURI = Uri.parse("package:"+gameInfo.getDataUrl());   
//		    Intent uninstallIntent = new Intent
//		    (Intent.ACTION_DELETE, packageURI);   
//		    startActivity(uninstallIntent);  
		}
	}
	}

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}
}
