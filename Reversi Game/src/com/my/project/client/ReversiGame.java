package com.my.project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.my.project.client.service.ClientImplementation;

public class ReversiGame implements EntryPoint{
	
	
	public void onModuleLoad() {
		ClientImplementation clientImplementation=new ClientImplementation();
		 RootPanel.get("div1").add(clientImplementation.getuserinterface());  
	}	
}
