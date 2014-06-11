package com.coship.game_platform.bean;

import java.net.URL;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * name urldata urlpagecut
 * @author coship
 *
 */
public class AppInfo implements Parcelable{
	private String gameName;
	private String dataUrl;
	private String pageUrl;
	private String appDetail;
	
	Parcelable.Creator<AppInfo> CREATOR=new Creator<AppInfo>() {
		@Override
		public AppInfo createFromParcel(Parcel source) {
			AppInfo app=new AppInfo();
			app.setGameName(source.readString());
			app.setDataUrl(source.readString());
			app.setPageUrl(source.readString());
			app.setAppDetail(source.readString());
			return app;
		}
		@Override
		public AppInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new AppInfo[size];
		}
	};
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
	public void setAppDetail(String appDetail){
		this.appDetail=appDetail;
	}
	public String getAppDetail() {
		return appDetail;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.gameName);
		dest.writeString(this.dataUrl);
		dest.writeString(this.pageUrl);
		dest.writeString(this.appDetail);
		
	}
}
