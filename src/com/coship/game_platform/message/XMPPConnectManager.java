package com.coship.game_platform.message;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPConnectManager {
	
	private static XMPPConnectManager xmppConnectManager;
	
	private XMPPConnectManager() {
	}
	
	
	public static XMPPConnectManager getInstance(){
		if(xmppConnectManager == null){
			xmppConnectManager = new XMPPConnectManager();
		}
		return xmppConnectManager;
	}
	
	public Connection getConnection(){
		final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(  
		        "10.9.20.201", Integer.parseInt("5222"), "10.9.20.201");
		Connection connection = new XMPPConnection(connectionConfig);
		connectionConfig.setReconnectionAllowed(true);  
		connectionConfig.setSendPresence(true);  
		connectionConfig.setSASLAuthenticationEnabled(false);
		connectionConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		
		return connection;
	}
}
