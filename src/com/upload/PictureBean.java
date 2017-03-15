package com.upload;

import java.io.Serializable;
import java.util.List;

public class PictureBean implements Serializable {

	private ImageBean src;

	private String desc;

	public PictureBean() {
		super();
	}

	

	public PictureBean(ImageBean src, String desc) {
		super();
		this.src = src;
		this.desc = desc;
	}



	public ImageBean getSrc() {
		return src;
	}



	public void setSrc(ImageBean src) {
		this.src = src;
	}



	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
