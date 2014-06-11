package com.coship.game_platform.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

public class MyNotice implements  Parcelable{

	private String title;
	private String content;
	private int isRead = 1;
	
	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：  
    // android.os.BadParcelableException:  
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person  
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用  
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；  
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
    // 5.反序列化对象  
	public static final Parcelable.Creator<MyNotice> CREATOR = new Creator<MyNotice>(){  
  
        @Override  
        public MyNotice createFromParcel(Parcel source) {  
            // TODO Auto-generated method stub  
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
        	MyNotice p = new MyNotice();  
            p.setTitle(source.readString());  
            p.setContent(source.readString());  
            p.setIsRead(source.readInt());
            return p;  
        }  
  
        @Override  
        public MyNotice[] newArray(int size) {  
            // TODO Auto-generated method stub  
            return new MyNotice[size];  
        }  
    };  
	
	public MyNotice(String title, String content, int isRead) {
		super();
		this.title = title;
		this.content = content;
		this.isRead = isRead;
	}
	public MyNotice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.content);
		dest.writeInt(this.isRead);
	}
	
	
}
