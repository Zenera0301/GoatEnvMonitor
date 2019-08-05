package com.goatenvmonitor.adapter;

public class HKitem {

	private String title;
	private String content;
	private String date;
	private int imgID;

	private String key;
	private String value;

	public HKitem(String title, String content, String date, int imgID){
		this.title = title;
		this.content = content;
		this.date = date;
		this.imgID = imgID;
	}

	public HKitem(String title, int imgID){
		this.title = title;
		this.imgID = imgID;
	}

	public HKitem(String key , String value){
		this.key = key;
		this.value = value;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getImgID() {
		return imgID;
	}

	public void setImgID(int imgID) {
		this.imgID = imgID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
