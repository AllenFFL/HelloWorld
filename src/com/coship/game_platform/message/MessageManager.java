package com.coship.game_platform.message;

import java.util.TreeMap;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import com.coship.game_platform.bean.FriendBean;

import android.content.res.Resources.Theme;
import android.os.Handler;

public class MessageManager {
	
	private Handler handler,handlerChat;
	private Connection connection;
	private static MessageManager messageManager;
	
	private MessageManager(){
		
	}
	
	public static MessageManager getInstance(){
		if(messageManager==null){
			messageManager = new MessageManager();
		}
		return messageManager;

	}
	
	public void login(String userId, String password,Handler handler){
		ConnextThread thread = new ConnextThread(userId, password,handler);
		thread.start();
		this.handler = handler;
	}
	
	public void sendNotice(String userId, String content){
		Message newmsg = new Message();   
		newmsg.setTo(userId+"@10.9.20.201");  
		newmsg.setSubject("notice");  
		newmsg.setBody(content);  
		newmsg.setType(Message.Type.headline);// normal支持离线   
		connection.sendPacket(newmsg);  
	}
	
	
	public void sendMessage(String userId, String content){
		Message newmsg = new Message();   
		newmsg.setTo(userId + "@10.9.20.201");  
		newmsg.setSubject("chat");  
		newmsg.setBody(content);  
		newmsg.setType(Message.Type.chat);// normal支持离线   
		connection.sendPacket(newmsg);  
	}
	
	public void getMessage(Handler handler){
		ChatMessage threadM = new ChatMessage(handler);
		threadM.start();
		this.handlerChat = handler;
		
	}
	
	
	public TreeMap<String, FriendBean> getFriends(){
		TreeMap<String, FriendBean> groupFriendInfo = new TreeMap<String, FriendBean>();
		Roster roster =	connection.getRoster();
		String jId = "";
		for (RosterEntry entry : roster.getEntries()) {
			FriendBean fb = new FriendBean();
			Presence presence = roster.getPresence(entry.getUser());
			fb.setjId(jId = entry.getUser());
			fb.setName(entry.getName());
			fb.setStatus("");
			groupFriendInfo.put(jId, fb);
			System.out.println(entry.getName()+"  "+entry.getUser()+"  "+entry.getGroups()+"  "+entry.getStatus()+"  "+entry.getType());  
			System.out.println(presence.getFrom()+" "+presence.getStatus());  
		}
		return groupFriendInfo;
		
	}
	/** 
     * 添加好友 无分组 
     *  
     * @param roster 
     * @param userName 
     * @param name 
     * @return 
     */  
    public  boolean addUser(String userName, String name) {  
    	Roster roster =	connection.getRoster();
        try {  
            roster.createEntry(userName, name, null);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
    
    /** 
     * 删除好友 
     *  
     * @param roster 
     * @param userName 
     * @return 
     */  
    public  boolean removeUser(String userName) {  
    	Roster roster =	connection.getRoster();
        try {  
            if (userName.contains("@")) {  
                userName = userName.split("@")[0];  
            }  
  
            RosterEntry entry = roster.getEntry(userName);  
            System.out.println("删除好友：" + userName);  
            System.out.println("User." + roster.getEntry(userName));  
            roster.removeEntry(entry);  
  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
    
    public class ChatMessage extends Thread{
    	private Handler handler;
    	
    	public ChatMessage(Handler handler){
			this.handler = handler;
		}
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		 connection.addPacketListener(pListener, new MessageTypeFilter(
 					Message.Type.chat));
    	}
    }
     
	public class ConnextThread extends Thread{
		
		private String userId;
		private String password;
		private Handler handler;
		
		public ConnextThread(String userId, String password,Handler handler){
			this.userId = userId;
			this.password = password;
			this.handler = handler;
		}
		
		@Override
		public void run() {
			XMPPConnectManager manager = XMPPConnectManager.getInstance();
			connection = manager.getConnection();
			android.os.Message msg = new android.os.Message();
			try {  
			    connection.connect();// 开启连接  
			    connection.login(userId, password,"sdk");
			    connection.addPacketListener(pListenerNotice, new MessageTypeFilter(
	 					Message.Type.headline));
			} catch (XMPPException e) {
				msg.what = -1;
				msg.obj = "XMPP服务器连接失败";
				handler.sendMessage(msg);
			    throw new IllegalStateException(e);  
			} 
			msg.what = 1;
			msg.obj = "XMPP服务器连接成功";
			handler.sendMessage(msg);
			
		}
	}
	
	
	PacketListener pListener = new PacketListener() {

		@Override
		public void processPacket(Packet arg0) {
			Message message = (Message) arg0;
			if (message != null && message.getBody() != null
					&& !message.getBody().equals("null")) {
				android.os.Message msg = new android.os.Message();
				msg.what = 2;
				msg.obj = message.getBody();
				handlerChat.sendMessage(msg);
			}

		}

	};
	PacketListener pListenerNotice = new PacketListener() {

		@Override
		public void processPacket(Packet arg0) {
			Message message = (Message) arg0;
			if (message != null && message.getBody() != null
					&& !message.getBody().equals("null")) {
				android.os.Message msg = new android.os.Message();
				msg.what = 2;
				msg.obj = message.getBody();
				handler.sendMessage(msg);
			}

		}

	};
	public void disConnect(){
		if(connection!=null){
			connection.disconnect();
		}
	}
	
}
