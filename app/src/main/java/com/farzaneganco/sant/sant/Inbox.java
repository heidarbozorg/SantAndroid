package com.farzaneganco.sant.sant;

import java.util.ArrayList;

import com.farzaneganco.sant.sant.logic.User;
import org.json.JSONArray;
import com.android.volley.VolleyError;
import com.farzaneganco.sant.sant.webservice.Common.ArrayResult;
import android.app.Activity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.app.AlarmManager;
import java.util.Calendar;

public class Inbox extends Activity {

	ImageView imgBack, imgRefresh;
	ProgressDialog pDialog;
	public static com.farzaneganco.sant.sant.logic.WFlow SelectedItem;
	public static int SelectedItemIndex;
	public static com.farzaneganco.sant.sant.logic.InboxAdapter adapter;

	// notification

	// private Timer timer = new Timer();
	// private int count = 0;

	// notification

	@Override
	public void onBackPressed() {
		finish();
	}

	/**
	 * بررسی وجود کار جدید در کارتابل در صورت وجود کار جدید را به کارتابل اضافه
	 * کرده و یک پیغام هشدار نیز میدهد
	 */
	private void CheckNewInbox() {

	}
	
	private void BindFromDatabase()
	{
		ArrayList<com.farzaneganco.sant.sant.logic.WFlow> q = com.farzaneganco.sant.sant.logic.DP.Inbox.GetAll(getApplicationContext());
		BindListView(q);
	}

	/**
	 * نمایش کارهای موجود در کارتابل این کاربر
	 */
	private void BindInbox() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.show();
		
		if (!com.farzaneganco.sant.sant.webservice.Common.IsInternetOn(getApplicationContext()))
		{
			//در صورتی که دستگاه به اینترنت متصل نبود، اطلاعات را از دیتابیس میخوانیم
			BindFromDatabase();
			pDialog.dismiss();
			return;
		}

		com.farzaneganco.sant.sant.webservice.WFlow.GetInbox(com.farzaneganco.sant.sant.logic.Variables.user, new ArrayResult() {

			@Override
			public void onSuccessVolleyFinished(JSONArray response, User user) {
				// TODO Auto-generated method stub
				ArrayList<com.farzaneganco.sant.sant.logic.WFlow> q = com.farzaneganco.sant.sant.logic.WFlow.Convert(response, getApplicationContext());
				pDialog.dismiss();

				//در صورتی که کاری وجود داشت که باید در کارتابل نمایشی دهیم
				//ابتدا آنها را در دیتابیس ذخیره میکنیم
				com.farzaneganco.sant.sant.logic.DP.Inbox.Insert(getApplicationContext(), q);
				BindListView(q);
				
				//اطلاعات آفلاین ذخیره شده در دیتابیس محلی را به سرور ارسال میکنیم
				com.farzaneganco.sant.sant.logic.NewErjaReceiver.SendOfflineInfo(getApplicationContext());
			}

			@Override
			public void OnErrorVolleyFinished(VolleyError error, User user) {
				// TODO Auto-generated method stub
				com.farzaneganco.sant.sant.logic.DP.Inbox.Insert(getApplicationContext(), null);
				pDialog.dismiss();
				Log.i("Inbox", "Error: " + error.getMessage());
				/*
				Toast.makeText(getApplicationContext(), error.getMessage(),
						Toast.LENGTH_LONG).show();
				*/
			}
		});
	}
	
	
	private void BindListView(final ArrayList<com.farzaneganco.sant.sant.logic.WFlow> q) {
		if (q == null || q.size() == 0)
			return;

		com.farzaneganco.sant.sant.logic.WFlow fq = q.get(0);
		com.farzaneganco.sant.sant.logic.Bind.SaveLastErja(getApplicationContext(), fq);
		
		adapter = new com.farzaneganco.sant.sant.logic.InboxAdapter(this, q);
		ListView listView = (ListView) findViewById(R.id.lvInbox);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SelectedItem = q.get(position);
				SelectedItemIndex = position;

				Intent go = new Intent(Inbox.this, com.farzaneganco.sant.sant.ViewDarkhast.class);
				startActivity(go);
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		try
		{
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.inbox);		
			
			
			imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
			imgRefresh.setVisibility(View.VISIBLE);
	
			imgBack = (ImageView) findViewById(R.id.imgBack);
			imgBack.setVisibility(View.GONE);
			imgRefresh.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BindInbox();			//به صورت موقت غیر فعال شد
					//logic.NewErjaReceiver.notifyMe(getApplicationContext(), "تست نمایش و صدا");
					
				}
			});

			com.farzaneganco.sant.sant.logic.Menu.SetMenuItem(this); // نمایش منوهای برنامه
			
			//نمایش کارهای موجود در کارتابل
			BindInbox();	
			
			// هر 2 دقیقه یکبار اطلاعات جدید را از سرور درخواست میکنیم
			CreateAlarm();
		}
		catch(Exception errCreate){
			Toast.makeText(getApplicationContext(), "Error in Inbox.OnCreate" + errCreate.getMessage(), 
					Toast.LENGTH_LONG).show();
		}
	}
	
	private void CreateAlarm()
	{
		Intent i = new Intent(this, com.farzaneganco.sant.sant.logic.NewErjaReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(pi); // cancel any existing alarms
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 15);
		long time = cal.getTimeInMillis();
		
		int interval = 2 * 60 * 1000;
		
		am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
		    time,
		    interval, pi);
	}
}