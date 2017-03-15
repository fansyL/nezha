package com.upload;

import java.io.Serializable;
import java.util.List;

public class RequestBean implements Serializable {

	private String title;
	private String desc;
	private int categoryId;
	private int userId;
	private String tag;
	private String price;;
	private List<PictureBean> recordList;
	private String token;
	private String time;
	
	public RequestBean() {
		super();
	}

	
	public RequestBean(String title, String desc, int categoryId, int userId, String tag, String price,
			List<PictureBean> recordList, String token, String time) {
		super();
		this.title = title;
		this.desc = desc;
		this.categoryId = categoryId;
		this.userId = userId;
		this.tag = tag;
		this.price = price;
		this.recordList = recordList;
		this.token = token;
		this.time = time;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<PictureBean> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<PictureBean> recordList) {
		this.recordList = recordList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}

}
