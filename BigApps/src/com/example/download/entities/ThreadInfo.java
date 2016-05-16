package com.example.download.entities;
/**
 *@Author Administrator
 *@Time 2016-5-3 下午11:45:07
 *线程下载信息
 */
public class ThreadInfo {

	private int id;
	private String url;
	/**开始位置*/
	private int start;
	/**结束位置*/
	private int stop;
	/**完成进度*/
	private int finished;
	public ThreadInfo(int id, String url, int start, int stop, int finished) {
		super();
		this.id = id;
		this.url = url;
		this.start = start;
		this.stop = stop;
		this.finished = finished;
	}
	public ThreadInfo() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getStop() {
		return stop;
	}
	public void setStop(int stop) {
		this.stop = stop;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
}
