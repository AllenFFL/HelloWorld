package com.coship.game_platform.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;

public class AppDownloadTask extends Thread {
	private static final String DIR_PATH = "/mnt/sdcard/";	// 下载目录
	private static final int THREAD_AMOUNT = 5;				// 总线程数
	
	private URL url;			// 目标下载地址
	private File dataFile;		// 本地文件
	private File tempFile;		// 用来存储每个线程下载的进度的临时文件
	private int totalLength;	// 服务端文件总长度
	private int threadLength;	// 每个线程要下载的长度
	private int totalFinish;	// 总共完成了多少
	private long begin;			// 用来记录开始下载时的时间
	private Callback callback;					// 回调对象
	private Handler handler = new Handler();	// 主线程中创建 向主线程发送消息 改变view数据

	public AppDownloadTask(String address, Callback callback) throws IOException {	
		url = new URL(address);															// 记住下载地址
		dataFile = new File(DIR_PATH, address.substring(address.lastIndexOf("/") + 1));	// 截取地址中的文件名, 创建本地文件
		tempFile = new File(dataFile.getAbsolutePath() + ".temp");						// 在本地文件所在文件夹中创建临时文件
		this.callback = callback;
	}

	public void run() {
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			
			totalLength = conn.getContentLength();							// 获取服务端发送过来的文件长度
			threadLength = (totalLength + THREAD_AMOUNT - 1) / THREAD_AMOUNT;// 计算每个线程要下载的长度
			
			handler.post(new Runnable(){
				public void run() {
					callback.onStart(totalLength);
				}
			});
			
			if (!tempFile.exists()) {											// 如果临时文件不存在
				RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");	//** 创建临时文件, 用来记录每个线程已下载多少
				for (int i = 0; i < THREAD_AMOUNT; i++)							// 按照线程数循环
					raf.writeInt(0);	// 用一个int值来记录每个线程的下载量 写入每个线程的开始位置(都是从0开始)
				raf.close();
			}
			
			for (int i = 0; i < THREAD_AMOUNT; i++)	// 按照线程数循环
				new DownloadThread(i).start();		// 开启线程, 每个线程将会下载一部分数据到本地文件中
			
			begin = System.currentTimeMillis();		// 记录开始时间
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class DownloadThread extends Thread {
		private int id; 	// 用来标记当前线程是下载任务中的第几个线程
		
		public DownloadThread(int id) {
			this.id = id;
		}
		
		public void run() {
			try {
				RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rws");//??不重复码？ 记录进度的临时文件
				tempRaf.seek(id * 4);						// 将指针移动到当前线程的位置(每个线程写1个int值, 占4字节)
				int threadFinish = tempRaf.readInt();		// 读取当前线程已完成了多少
				synchronized(AppDownloadTask.this) {			// 多个下载线程之间同步
					totalFinish += threadFinish;			// 统计所有线程总共完成了多少
				}
				
				int start = id * threadLength + threadFinish;		// 计算当前线程起始位置
				int end = id * threadLength + threadLength - 1;		// 计算当前线程结束位置
				System.out.println("线程" + id + ": " + start + "-" + end);
			
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);		// 设置当前线程下载的范围
				
				InputStream in = conn.getInputStream();								// 获取连接的输入流
				RandomAccessFile dataRaf = new RandomAccessFile(dataFile, "rws");	// 保存数据的本地文件
				dataRaf.seek(start);												// 设置当前线程保存数据的位置
				
				byte[] buffer = new byte[1024 * 100];			// 100KB的缓冲区
				int len;
				while ((len = in.read(buffer)) != -1) {			// 每次读取100KB
					dataRaf.write(buffer, 0, len);				// 写到本地文件
					threadFinish += len;						// 统计当前线程完成了多少
					tempRaf.seek(id * 4);						// 将临时文件的指针指向当前线程的位置
					tempRaf.writeInt(threadFinish);				// 将当前线程完成了多少写入到临时文件
					synchronized(AppDownloadTask.this) {			// 多个下载线程之间同步
						totalFinish += len;						// 统计所有线程总共完成了多少
					}
					handler.post(new Runnable() {
						public void run() {
							callback.onDownloading(totalLength, totalFinish);
						}
					});
				}
				dataRaf.close();
				tempRaf.close();
				
				System.out.println("线程" + id + "下载完毕");
				if (totalFinish == totalLength) {				// 如果已完成长度等于服务端文件长度(代表下载完成)
					System.out.println("下载完成, 耗时: " + (System.currentTimeMillis() - begin));
					tempFile.delete();							// 删除临时文件
					handler.post(new Runnable() {
						public void run() {
							callback.onSuccess();
						}
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public interface Callback {
		public void onStart(int totalLength);
		public void onDownloading(int totalLength, int totalFinish);
		public void onSuccess();
	}
}

