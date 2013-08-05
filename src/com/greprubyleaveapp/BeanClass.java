package com.greprubyleaveapp;
import android.app.Activity;
import android.os.Bundle;


public class BeanClass extends Activity {
	
	
	
	public static String url_signin = "http://grep-ruby-leave-app.herokuapp.com/api/v1/session"; 
	
	public static String url_forgot = "http://grep-ruby-leave-app.herokuapp.com/api/v1/session";
	
	public static String url_signup = "http://grep-ruby-leave-app.herokuapp.com/api/v1/registration";
	
	public static String url_apply_leave = "http://grep-ruby-leave-app.herokuapp.com/api/v1/leaves";
	
	public static String url_checkin = "http://grep-ruby-leave-app.herokuapp.com/api/v1/leaves";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        
    }
  
}
