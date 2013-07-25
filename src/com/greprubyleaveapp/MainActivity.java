package com.greprubyleaveapp;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements OnTabChangeListener {
	
	TabHost tabHost;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* TabHost will have Tabs */
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setOnTabChangedListener(this);
        
        /* TabSpec used to create a new tab. 
         * By using TabSpec only we can able to setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. */
        
        /* tid1 is firstTabSpec Id. Its used to access outside. */
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        
        /* TabSpec setIndicator() is used to set name for the tab. */
        /* TabSpec setContent() is used to set content for a particular tab. */
        firstTabSpec.setIndicator("Log in", getResources().getDrawable(R.drawable.logo));
        secondTabSpec.setIndicator("Sign up", getResources().getDrawable(R.drawable.logo));
        
        firstTabSpec.setContent(new Intent(this,Login.class));
        secondTabSpec.setContent(new Intent(this,Signup.class));
        
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#8B8D8B"));
        	
        	
        }
        
        tabHost.getTabWidget().setCurrentTab(1);
        tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#494849"));
        
    }

    
    
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
        	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#494849"));
        } 
				
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#8B8D8B"));
	}
    
    
    
}
