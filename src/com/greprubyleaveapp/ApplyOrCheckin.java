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
import android.widget.Toast;


public class ApplyOrCheckin extends ListActivity
{
	private Button apply,profile;
	private ImageView signOut,calander;
	private TextView checkin;
	
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
	
	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	private JSONParser jsonParser = new JSONParser();
	private JSONArray leaves = null;
	
	private String apiToken;
	private String uName;
	private String lvStatus;
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	BeanClass bc = new BeanClass();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_or_checkin);
		Bundle gb  = this.getIntent().getExtras();
		apiToken = gb.getString("apiToken");
		uName  = gb.getString("uName");
		
		//String answer = String.valueOf(apiToken);
    	//Toast.makeText(getApplicationContext(), uName, Toast.LENGTH_LONG).show();
		
		final Bundle pb = new Bundle();
		
		pb.putString("uName", uName);
    	pb.putString("apiToken", apiToken);
    	
		apply=(Button)findViewById(R.id.apply);
		apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	Intent i = new Intent(view.getContext(), ApplyLeave.class); 	
            	i.putExtras(pb);
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		checkin=(TextView)findViewById(R.id.checkin);
		checkin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Intent i = new Intent(view.getContext(), CheckinLeav.class); 	
            	i.putExtras(pb);
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		signOut=(ImageView)findViewById(R.id.signout);
		signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent i = new Intent(view.getContext(), Login.class); 
            	i.putExtras(pb);
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		calander=(ImageView)findViewById(R.id.calander);
		calander.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	
            	Intent i = new Intent(view.getContext(), CalendarView.class); 	
            	i.putExtras(pb);
	        	startActivity(i);
	        	overridePendingTransition( R.anim.slide_in_down, R.anim.slide_out_down);
	        	finish();
	        	
            }
           });
		
		profile=(Button)findViewById(R.id.profile);
		profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Intent i = new Intent(view.getContext(), MyProfile.class); 
            	i.putExtras(pb);
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		
		
		
		
		
		if(bc.getApplyorCheckinJsonValue().isEmpty()){
			
			new CheckinDetail().execute();
			
		}else{
			
			loadedList();
		}
		
		
		
		
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
				pDialog = new ProgressDialog(ApplyOrCheckin.this);
				pDialog.setMessage("Please wait...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
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
					
					pDialog.dismiss();
					
					int temp_length = leaves.length();
					if(temp_length>4){
						temp_length=4;
					}
					
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
				        
				        
				        
				        
				       // System.out.println("--------day_fr"+day_fr);
				       // System.out.println("--------month_fr"+month_fr);
				      //  System.out.println("--------year_fr"+year_fr);
				        
				        
						
				        String to[] = leave_to.split("-");
				        int day_to = Integer.parseInt(to[2]);
				        int month_to = Integer.parseInt(to[1]);
				        month_to=month_to-1;
				        int year_to = Integer.parseInt(to[0]);
				        
				       // System.out.println("--------day_to"+day_to);
				       // System.out.println("--------month_to"+month_to);
				        //System.out.println("--------year_fr"+year_to);
				      
						
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
						
						// System.out.println("--------id="+id);
						

						// adding HashList to ArrayList
						
						contactList.add(map);
						
						
						
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
				
				bc.setApplyorCheckinJsonValue(contactList);
				
				//System.out.println("--------obj = "+bc.getJsonValue());
				
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 * */
						ListAdapter adapter = new SimpleAdapter(ApplyOrCheckin.this, bc.getApplyorCheckinJsonValue(),	R.layout.list_item, new String[] { LEAVE_FROM,
							LEAVE_TO,TOTAL_LEAVES,LEAVE_TYPE,REASON,STATUS},new int[] { R.id.start_date_id, R.id.end_date_id,R.id.no_of_days,R.id.leave_type,R.id.reason,R.id.status });
						// updating listview
						setListAdapter(adapter);
						
						
						
						       // selecting single ListView item
						
								ListView lv = getListView();
								
								lv.setCacheColorHint(Color.TRANSPARENT);
								
								//Toast.makeText(getApplicationContext(), lvStatus, Toast.LENGTH_SHORT).show();
								
								//lv.setBackgroundColor(Color.parseColor("#505050"));
								
								/*if(lvStatus.equals("approved")){
									
									lv.setBackgroundColor(Color.BLUE);
								}
							    if(lvStatus.equals("not approved")){
							    	
									lv.setBackgroundColor(Color.RED);
									
								}*/

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
										in.putExtra("isComingFrom", "ApplyOrCheckin");
										startActivity(in);
										overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
									}
								});
								
								
								
								
								
								
					}
				});

			}

			
		}
		
		protected void loadedList() {
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(ApplyOrCheckin.this, bc.getApplyorCheckinJsonValue(),	R.layout.list_item, new String[] { LEAVE_FROM,
						LEAVE_TO,TOTAL_LEAVES,LEAVE_TYPE,REASON,STATUS},new int[] { R.id.start_date_id,R.id.end_date_id, R.id.no_of_days,R.id.leave_type,R.id.reason,R.id.status });
					// updating listview
					setListAdapter(adapter);
					
					
					
					       // selecting single ListView item
					
							ListView lv = getListView();
							
							lv.setCacheColorHint(Color.TRANSPARENT);
							
							//Toast.makeText(getApplicationContext(), lvStatus, Toast.LENGTH_SHORT).show();
							
							//lv.setBackgroundColor(Color.parseColor("#505050"));
							
							/*if(lvStatus.equals("approved")){
								
								lv.setBackgroundColor(Color.BLUE);
							}
						    if(lvStatus.equals("not approved")){
						    	
								lv.setBackgroundColor(Color.RED);
								
							}*/

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
									in.putExtra("isComingFrom", "ApplyOrCheckin");
									startActivity(in);
									overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
								}
							});
							
							
							
							
							
							
				}
			});

		
			
			
		}
		
}