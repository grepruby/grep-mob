package com.greprubyleaveapp;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;




public class CheckinLeav extends ListActivity
{
	
	private JSONParser jsonParser = new JSONParser();
	private ArrayList<HashMap<String, String>> contactList;
	
	private ImageView back;
	private Button previous,more;

	private static final String LEAVES = "leaves";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String APPLIED_DATE = "applied_date";
	private static final String LEAVE_FROM = "leave_from";
	private static final String LEAVE_TO = "to";
	private static final String LEAVE_TYPE = "leave_type";
	private static final String REASON = "reason";
	private static final String TOTAL_LEAVES="total_leaves";
	private static final String STATUS="status";
	
	private JSONArray leaves = null;
	
	private String apiToken;
	private String uName;
	private String lvStatus;
	
	
	private ProgressDialog pDialog;
	
	private BeanClass bc = new BeanClass();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin);
		
		 ((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener() {
	            @Override
	            public void onRefresh() {
	                // Do work to refresh the list here.
	                new CheckinDetail().execute();
	            }
	        });
		
		    uName = BeanClass.getUserName();
			apiToken = BeanClass.getApiToken();
		
		
		
		if(bc.getCheckinJsonValue().isEmpty()){
			new CheckinDetail().execute();
		}else{
			loadedJsonData();
		}
		
		
		
		
		//previous=(Button)findViewById(R.id.previous);
		//more=(Button)findViewById(R.id.more);
		
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 
            	startActivity(i);
	        	finish();
	        	overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
	        	
            }
           });
		
		//previous.setEnabled(false);
	
		//Toast.makeText(getApplicationContext(), "tokan="+apiToken, Toast.LENGTH_SHORT).show();
		
	}
    
    
    /**
	 * Background Async Task to Create new product
	 * */
	class CheckinDetail extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CheckinLeav.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
		//	pDialog.show();
			
			
			
			// *****  create every time new list view when use pull to refresh  *****  //
			
			contactList = new ArrayList<HashMap<String, String>>();
			
			
			
		}
			
		/**
		 * Creating product
		 * */
		@SuppressWarnings("deprecation")
		protected String doInBackground(String... args) {
			
			
			

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("api_token", apiToken));
			

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(GlobalVariables.url_checkin,"GET", params);
			
			
			// check log cat fro response
			//Log.d("Create Response", json.toString());

			
			try {
				leaves = json.getJSONArray(LEAVES);
				
				//pDialog.dismiss();
				
				int temp_length = leaves.length();
				/*if(temp_length>6){
					temp_length=6;
				}*/
				
				// looping through All Contacts
				for(int i = 0; i < temp_length; i++){
					
					JSONObject c = leaves.getJSONObject(i);
					
					// Storing each json item in variable
					String id = c.getString(ID);
					//String name = c.getString(NAME);
					String apply_date = c.getString(APPLIED_DATE);
					String leave_from = c.getString(LEAVE_FROM);
					String leave_to = c.getString(LEAVE_TO);
					String leave_type = c.getString(LEAVE_TYPE);
					String reason = c.getString(REASON);
					lvStatus = c.getString(STATUS);
					
					
					
					String fr[] = leave_from.split("-");
			        int day_fr = Integer.parseInt(fr[2]);
			        int month_fr = Integer.parseInt(fr[1]);
			        month_fr=month_fr-1;
			        int year_fr = Integer.parseInt(fr[0]);
			        
					
			        String to[] = leave_to.split("-");
			        int day_to = Integer.parseInt(to[2]);
			        int month_to = Integer.parseInt(to[1]);
			        month_to=month_to-1;
			        int year_to = Integer.parseInt(to[0]);
			        
			        
					  
					  Calendar frmDay = Calendar.getInstance();
					  frmDay.set(Calendar.DAY_OF_MONTH,day_fr);
					  frmDay.set(Calendar.MONTH,month_fr); // 0-11 so 1 less
					  frmDay.set(Calendar.YEAR, year_fr);

					  Calendar toDay = Calendar.getInstance();
					  toDay.set(Calendar.DAY_OF_MONTH,day_to);
					  toDay.set(Calendar.MONTH,month_to); // 0-11 so 1 less
					  toDay.set(Calendar.YEAR, year_to);

					  long diff = (toDay.getTimeInMillis() - frmDay.getTimeInMillis()); 
					  
					  if(diff==0){
						  
						  diff=(diff/100000000)+1;
						  
					  }else{
						  
						  diff=(diff/100000000)+2;
					  
					  }
					  
					  
					  
					  String noOfLeave= String.valueOf(diff);
					  
					 if(leave_type.equals("Half day(10am-3pm)") || leave_type.equals("Half day(3pm-7pm)")){
						
						 noOfLeave="1/2";
					 }
					
					 
					 
					 
					 noOfLeave=noOfLeave+" days";
					 
					 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					 SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
					 
					 Date date_from = format1.parse(leave_from);
					 leave_from = format2.format(date_from);
					 leave_from=leave_from+"  to ";
					 
					 Date date_to = format1.parse(leave_to);
					 leave_to = format2.format(date_to);
					 
					// System.out.println("------date="+format2.format(date));
					 
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					
					// adding each child node to HashMap key => value
					
					map.put(ID, id);
					//map.put(NAME, name);
					map.put(APPLIED_DATE,apply_date);
					map.put(LEAVE_FROM, leave_from);
					map.put(LEAVE_TO, leave_to);
					map.put(LEAVE_TYPE,leave_type);
					map.put(REASON,reason);
					map.put(TOTAL_LEAVES, noOfLeave);
					map.put(STATUS, lvStatus);

					// adding HashList to ArrayList
					
					contactList.add(map);
					
					/* try {
			                Thread.sleep(2000);
			            } catch (InterruptedException e) {
			                ;
			            }*/
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			return null;
		}

	
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			
			bc.setCheckinJsonValue(contactList);
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(CheckinLeav.this, bc.getCheckinJsonValue(),	R.layout.list_item, new String[] { LEAVE_FROM,
						LEAVE_TO,TOTAL_LEAVES,LEAVE_TYPE,REASON,STATUS},new int[] { R.id.start_date_id, R.id.end_date_id,R.id.no_of_days,R.id.leave_type,R.id.reason,R.id.status });
					// updating listview
					setListAdapter(adapter);
					
					
					((PullToRefreshListView) getListView()).onRefreshComplete();
		            //super.onPostExecute(result);
					       // selecting single ListView item
					
							ListView lv = getListView();
							
							lv.setCacheColorHint(Color.TRANSPARENT);
							
							//lv.setBackground(background);
							

							// Launching new screen on Selecting Single ListItem
					       lv.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
									// getting values from selected ListItem
									String from = ((TextView) view.findViewById(R.id.start_date_id)).getText().toString();
									String to = ((TextView) view.findViewById(R.id.no_of_days)).getText().toString();
									String leave_type = ((TextView) view.findViewById(R.id.leave_type)).getText().toString();
									String reason = ((TextView) view.findViewById(R.id.reason)).getText().toString();
									String status = ((TextView) view.findViewById(R.id.status)).getText().toString();
									
									
									// Starting new intent
									Intent in = new Intent(getApplicationContext(), LeaveDetail.class);
									in.putExtra(LEAVE_FROM, from);
									in.putExtra(LEAVE_TO, to);
									in.putExtra(LEAVE_TYPE, leave_type);
									in.putExtra(REASON, reason);
									in.putExtra(STATUS, status);
									in.putExtra("uName", uName);
									in.putExtra("apiToken", apiToken);
									in.putExtra("isComingFrom", "Checkin");
									startActivity(in);
									overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
								}
							});
				}
			});

		}

		
	}
	
	
	private void loadedJsonData(){

		
		// updating UI from Background Thread
		runOnUiThread(new Runnable() {
			public void run() {
				/**
				 * Updating parsed JSON data into ListView
				 * */
				ListAdapter adapter = new SimpleAdapter(CheckinLeav.this, bc.getCheckinJsonValue(),	R.layout.list_item, new String[] { LEAVE_FROM,
					LEAVE_TO,TOTAL_LEAVES,LEAVE_TYPE,REASON,STATUS},new int[] { R.id.start_date_id, R.id.end_date_id,R.id.no_of_days,R.id.leave_type,R.id.reason,R.id.status });
				// updating listview
				setListAdapter(adapter);
				
				
				
				       // selecting single ListView item
				
				
				
						ListView lv = getListView();
						
						lv.setCacheColorHint(Color.TRANSPARENT);
						
						//lv.setBackground(background);
						

						// Launching new screen on Selecting Single ListItem
						lv.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
								// getting values from selected ListItem
								String from = ((TextView) view.findViewById(R.id.start_date_id)).getText().toString();
								String to = ((TextView) view.findViewById(R.id.no_of_days)).getText().toString();
								String leave_type = ((TextView) view.findViewById(R.id.leave_type)).getText().toString();
								String reason = ((TextView) view.findViewById(R.id.reason)).getText().toString();
								String status = ((TextView) view.findViewById(R.id.status)).getText().toString();
								
								// Starting new intent
								
								Intent in = new Intent(getApplicationContext(), LeaveDetail.class);
								in.putExtra(LEAVE_FROM, from);
								in.putExtra(LEAVE_TO, to);
								in.putExtra(LEAVE_TYPE, leave_type);
								in.putExtra(REASON, reason);
								in.putExtra(STATUS, status);
								in.putExtra("uName", uName);
								in.putExtra("apiToken", apiToken);
								in.putExtra("isComingFrom", "Checkin");
								startActivity(in);
								overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
							}
						});
			}
		});

	
	}
	
	
	
	
	
	
	
	
	
}