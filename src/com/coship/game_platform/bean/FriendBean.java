package com.coship.game_platform.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class FriendBean implements Parcelable{

	private String jId;
	private String name;
	private String status;
	
	
	public FriendBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FriendBean(String jId, String name, String status) {
		super();
		this.jId = jId;
		this.name = name;
		this.status = status;
	}
	public String getjId() {
		return jId;
	}
	public void setjId(String jId) {
		this.jId = jId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：  
    // android.os.BadParcelableException:  
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person  
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用  
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；  
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
    // 5.反序列化对象  
	public static final Parcelable.Creator<FriendBean> CREATOR = new Creator<FriendBean>(){  
  
        @Override  
        public FriendBean createFromParcel(Parcel source) {  
            // TODO Auto-generated method stub  
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
        	FriendBean f = new FriendBean();  
            f.setjId(source.readString()); 
            f.setName(source.readString());  
            f.setStatus(source.readString());
            return f;  
        }  
  
        @Override  
        public FriendBean[] newArray(int size) {  
            // TODO Auto-generated method stub  
            return new FriendBean[size];  
        }  
    };  
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.jId);
		dest.writeString(this.name);
		dest.writeString(this.status);
	}
	
	
	
}
