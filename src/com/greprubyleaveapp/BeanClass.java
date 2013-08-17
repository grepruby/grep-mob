package com.greprubyleaveapp;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;


public class BeanClass extends Activity {
	
	
	
	public static String url_signin = "http://grepruby-leave-app.herokuapp.com/api/v1/session"; 
	
	public static String url_forgot = "http://grepruby-leave-app.herokuapp.com/api/v1/session";
	
	public static String url_signup = "http://grepruby-leave-app.herokuapp.com/api/v1/registration";
	
	public static String url_apply_leave = "http://grepruby-leave-app.herokuapp.com/api/v1/leaves";
	
	public static String url_checkin = "http://grepruby-leave-app.herokuapp.com/api/v1/leaves";
	
	public static ArrayList<HashMap<String, String>> contactApplyorCheckinList = new ArrayList<HashMap<String, String>>();
	
	public static ArrayList<HashMap<String, String>> contactCheckinList = new ArrayList<HashMap<String, String>>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        
    }
    
    
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
    
    
    
  
}
