package com.coship.game_platform.bean;

public class GameInfo {

	private String gameName;
	private String dataUrl;
	private String pageUrl;
	private String appDetail;
	public GameInfo(String gameName, String dataUrl, String pageUrl,
			String appDetail) {
		super();
		this.gameName = gameName;
		this.dataUrl = dataUrl;
		this.pageUrl = pageUrl;
		this.appDetail = appDetail;
	}
	public GameInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getDataUrl() {
		return dataUrl;
	}
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getAppDetail() {
		return appDetail;
	}
	public void setAppDetail(String appDetail) {
		this.appDetail = appDetail;
	}
	
	
}
