package com.my.project.client;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Image;

public class Square implements Serializable{
	public static final String Image1="Bdiscs.gif";
	public static final String Image2="Wdiscs.gif";
	
	public static final String color1="Black";
	public static final String color2="white";
	
	private boolean flag;
	private String discus;
	private String color;
	
	public void setFlag(boolean flag){
		this.flag=flag;
	}

	public boolean getFlag(){
		return flag;
	}
	
	public void setDiscus(String discus){
		this.discus=discus;
	}

	String getDiscus(){
		return discus;
	}
	
	public void setColor(String color){
		this.color=color;
	}

	public String getColor(){
		return color;
	}
}
