package com.upload;
import java.io.Serializable;

public class ImageBean implements Serializable {

	private String thumb;
	private String middle;
	private String master;

	public ImageBean(String thumb, String middle, String master) {
		super();
		this.thumb = thumb;
		this.middle = middle;
		this.master = master;
	}

	public ImageBean() {
		super();
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Override
	public String toString() {
		return "ImageBean [thumb=" + thumb + ", middle=" + middle + ", master=" + master + "]";
	}

}
