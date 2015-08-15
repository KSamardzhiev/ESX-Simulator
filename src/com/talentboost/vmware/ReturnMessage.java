package com.talentboost.vmware;

import java.util.ResourceBundle;

public class ReturnMessage {

	private static ResourceBundle bundle = ResourceBundle.getBundle("messages_EN");
	
	public static String getMessage(String msg){
		
		return bundle.getString(msg);
		
	}
}
