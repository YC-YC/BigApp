package com.example.download.entities;

import java.io.Serializable;

/**
 *@Author Administrator
 *@Time 2016-5-3 下午11:40:40
 *文件信息
 */
public class FileInfo implements Serializable{
	/**文件ID*/
	private int id;
	/**文件URL*/
	private String url;
	/**文件名*/
	private String fileName;
	/**文件长度*/
	private int length;
	/**文件下载完成的进度*/
	private int finished;
	public FileInfo(int id, String url, String fileName, int length,
			int finished) {
		super();
		this.id = id;
		this.url = url;
		this.fileName = fileName;
		this.length = length;
		this.finished = finished;
	}
	public FileInfo() {
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", url=" + url + ", fileName=" + fileName
				+ ", length=" + length + ", finished=" + finished + "]";
	}
	
	
}
