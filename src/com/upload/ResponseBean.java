package com.upload;

import java.io.Serializable;

public class ResponseBean implements Serializable {

	private int error_code;

	private String message;

	private ImageBean src;

	public ResponseBean() {
		super();
	}

	public ResponseBean(int error_code, String message, ImageBean src) {
		super();
		this.error_code = error_code;
		this.message = message;
		this.src = src;
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ImageBean getSrc() {
		return src;
	}

	public void setSrc(ImageBean src) {
		this.src = src;
	}

	@Override
	public String toString() {
		return "ResponseBean [error_code=" + error_code + ", message=" + message + ", src=" + src + "]";
	}
	
}
