package com.greprubyleaveapp;



import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.greprubyleaveapp.Login.SigninData;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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


public class CheckinLeav extends ListActivity
{
	
	JSONParser jsonParser = new JSONParser();
	
	ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
    
    private static String url_checkin = "http://grep-ruby-leave-app.herokuapp.com/api/v1/leaves";
	
	ImageView back;

	private static final String LEAVES = "leaves";
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String APPLIED_DATE = "applied_date";
	private static final String LEAVE_FROM = "leave_from";
	private static final String LEAVE_TO = "to";
	private static final String LEAVE_TYPE = "leave_type";
	private static final String REASON = "reason";
	private static final String TOTAL_LEAVES="total_leaves";
	
	
	
	JSONArray leaves = null;
	
	private String apiToken;
	private String uName;
	
	// Progress Dialog
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin_leav);
		
		Bundle gb  = this.getIntent().getExtras();
		apiToken = gb.getString("apiToken");
		uName  = gb.getString("uName");
		new CheckinDetail().execute();
		
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	Bundle pb = new Bundle();
            	pb.putString("uName", uName);
            	pb.putString("apiToken", apiToken);
            	
            	Intent i = new Intent(view.getContext(), ApplyOrCheckin.class); 
            	i.putExtras(pb);
	        	startActivity(i);
	        	finish();
	        	
            }
           });
		
	
		Toast.makeText(getApplicationContext(), "tokan="+apiToken, Toast.LENGTH_SHORT).show();
		
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
			JSONObject json = jsonParser.makeHttpRequest(url_checkin,"GET", params);
			
			
			// check log cat fro response
			//Log.d("Create Response", json.toString());

			
			try {
				leaves = json.getJSONArray(LEAVES);
				
				pDialog.dismiss();
				
				// looping through All Contacts
				for(int i = 0; i < leaves.length(); i++){
					JSONObject c = leaves.getJSONObject(i);
					
					// Storing each json item in variable
					String id = c.getString(ID);
					//String name = c.getString(NAME);
					String apply_date = c.getString(APPLIED_DATE);
					String leave_from = c.getString(LEAVE_FROM);
					String leave_to = c.getString(LEAVE_TO);
					String leave_type = c.getString(LEAVE_TYPE);
					String reason = c.getString(REASON);
					
					
					
					String fr[] = leave_from.split("-");
			        int day_fr = Integer.parseInt(fr[2]);
			        int month_fr = Integer.parseInt(fr[1]);
			        month_fr=month_fr-1;
			        int year_fr = Integer.parseInt(fr[0]);
			        
			        
			        
			        
			        System.out.println("--------day_fr"+day_fr);
			       // System.out.println("--------month_fr"+month_fr);
			      //  System.out.println("--------year_fr"+year_fr);
			        
			        
					
			        String to[] = leave_to.split("-");
			        int day_to = Integer.parseInt(to[2]);
			        int month_to = Integer.parseInt(to[1]);
			        month_to=month_to-1;
			        int year_to = Integer.parseInt(to[0]);
			        
			        System.out.println("--------day_to"+day_to);
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
					  
					  diff=(diff/100000000)+2;
					  
					  String noOfLeave= String.valueOf(diff);
					  
					  System.out.println("--------no. of days"+diff);
					  System.out.println("--------no. of days"+noOfLeave);
					
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

					// adding HashList to ArrayList
					contactList.add(map);
					
					
					
				}
			} catch (JSONException e) {
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
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(CheckinLeav.this, contactList,	R.layout.list_item, new String[] { LEAVE_FROM,
							TOTAL_LEAVES,LEAVE_TYPE,REASON},new int[] { R.id.start_date_id, R.id.no_of_days,R.id.leave_type,R.id.reason });
					// updating listview
					setListAdapter(adapter);
					
					
					
					// selecting single ListView item
							ListView lv = getListView();

							// Launching new screen on Selecting Single ListItem
							lv.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									// getting values from selected ListItem
									String from = ((TextView) view.findViewById(R.id.start_date_id)).getText().toString();
									String to = ((TextView) view.findViewById(R.id.no_of_days)).getText().toString();
									String leave_type = ((TextView) view.findViewById(R.id.leave_type)).getText().toString();
									String reason = ((TextView) view.findViewById(R.id.reason)).getText().toString();
									
									
									// Starting new intent
									Intent in = new Intent(getApplicationContext(), LeaveDetail.class);
									in.putExtra(LEAVE_FROM, from);
									in.putExtra(LEAVE_TO, to);
									in.putExtra(LEAVE_TYPE, leave_type);
									in.putExtra(REASON, reason);
									
									in.putExtra("uName", uName);
									in.putExtra("apiToken", apiToken);
									startActivity(in);
								}
							});
				}
			});

		}

		
	}
	
}