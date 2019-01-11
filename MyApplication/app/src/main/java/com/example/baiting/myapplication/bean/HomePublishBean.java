package com.example.baiting.myapplication.bean;


import java.io.Serializable;

public class HomePublishBean implements Serializable{

	private static final long serialVersionUID = -1678410215260044682L;
	private String action;
	private boolean needLogin;
	private String icon;
	private int listName;
	private String name;
	private String cateid;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	public boolean isNeedLogin() {
		return needLogin;
	}
	public void setNeedLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getListName() {
		return listName;
	}
	public void setListName(int listName) {
		this.listName = listName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCateid() {
		return cateid;
	}
	public void setCateid(String cateid) {
		this.cateid = cateid;
	}
	
}
