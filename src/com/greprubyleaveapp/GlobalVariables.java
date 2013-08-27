package com.greprubyleaveapp;

public class GlobalVariables {
	
	public static String server_url = "http://grepruby-leave-app.herokuapp.com";
	
	public static String api_url = server_url + "/api/v1";
	
	public static String url_signin = api_url + "/session"; 
	
	public static String url_forgot =  api_url + "/session";
	
	public static String url_signup =  api_url + "/registration";
	
	public static String url_apply_leave =  api_url + "/leaves";
	
	public static String url_checkin =  api_url + "/leaves";

}
