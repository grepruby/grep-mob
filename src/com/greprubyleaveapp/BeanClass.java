package com.greprubyleaveapp;

import java.util.ArrayList;
import java.util.HashMap;


public class BeanClass{
	
	public static ArrayList<HashMap<String, String>> contactApplyorCheckinList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> contactCheckinList = new ArrayList<HashMap<String, String>>();
	
	public static String uName;
	public static String apiToken;
	public static String uEmail;
    
	
    public static void setApplyorCheckinJsonValue(ArrayList<HashMap<String, String>> contacAppleorCheckintListItem)
    {
    	contactApplyorCheckinList=contacAppleorCheckintListItem;
    }
    public static ArrayList<HashMap<String, String>> getApplyorCheckinJsonValue()
    {
        return contactApplyorCheckinList;
    }
    
    public static void setCheckinJsonValue(ArrayList<HashMap<String, String>> contactCheckinListItem)
    {
    	contactCheckinList=contactCheckinListItem;
    }
    public static ArrayList<HashMap<String, String>> getCheckinJsonValue()
    {
       return contactCheckinList;
    }
    
    public static void setUserName(String user)
    {
    	uName=user;
    }
    public static String getUserName()
    {
       return uName;
    }
    
    public static void setApiToken(String api)
    {
    	apiToken=api;
    }
    public static String getApiToken()
    {
       return apiToken;
    }
    
    public static void setEmail(String email)
    {
    	uEmail=email;
    }
    public static String getEmail()
    {
       return uEmail;
    }
  
}
