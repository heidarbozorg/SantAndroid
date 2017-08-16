package com.farzaneganco.sant.sant;

import com.farzaneganco.sant.sant.logic.User;
import com.farzaneganco.sant.sant.webservice.Common;
import com.farzaneganco.sant.sant.webservice.Common.StringResult;
import com.android.volley.VolleyError;

import android.R.bool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDarkhast extends Activity {

	ImageView imgBack, imgGPS, imgTajhizNotExists, imgForward;
	com.farzaneganco.sant.sant.logic.GPSTracker gps;
	ProgressDialog pDialog;

	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	
	public static double latitude, longitude;
	TextView tvTimeProgress;

	@Override
	protected void onRestart() {
		super.onRestart();
		if (Inbox.SelectedItem == null)
			finish();
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
	private void SendGPSToWS()
	{
		com.farzaneganco.sant.sant.webservice.WFlow.SendGPS(Inbox.SelectedItem.DarkhastKarId,
				Inbox.SelectedItem.ErjaDarkhastId,
			com.farzaneganco.sant.sant.logic.Variables.user.GetPersonelId(), latitude, longitude,
			com.farzaneganco.sant.sant.logic.Tarikh.GetTarikh(), com.farzaneganco.sant.sant.logic.Tarikh.GetSaat(),
				new StringResult() {

					@Override
					public void onSuccessVolleyFinished(String response,
							User user) {
						pDialog.dismiss();
						//Intent go = new Intent(ViewDarkhast.this, com.far.sant.SendGPS.class);
						//startActivity(go);
						/*
						Toast.makeText(getApplicationContext(),
								R.string.GPSSendSuccess, Toast.LENGTH_LONG)
								.show();*/
					}

					@Override
					public void OnErrorVolleyFinished(VolleyError error,
							User user) {
						pDialog.dismiss();
						Log.i("ViewDarkhast",
								"Error in SendGPS: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
				});
	}

	private void SendGPSToDB()
	{
		if (com.farzaneganco.sant.sant.logic.DP.DarkhastGPS.Insert(getApplicationContext(),
				Inbox.SelectedItem.DarkhastKarId,
				Inbox.SelectedItem.ErjaDarkhastId,
			  com.farzaneganco.sant.sant.logic.Variables.user.GetPersonelId(),
				latitude, 
				longitude) == 1)
		{
			pDialog.dismiss();
			//Intent go = new Intent(ViewDarkhast.this, com.farzaneganco.sant.sant.SendGPS.class);
			//startActivity(go);
		}
		else{
			pDialog.dismiss();
			Log.i("ViewDarkhast", "Error in SendGPS into Database");
			Toast.makeText(getApplicationContext(),
					"خطا در ثبت اطلاعات", Toast.LENGTH_LONG)
					.show();
		}			
	}

	
	private void SendGPS(boolean ConnectToInternet) {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.show();

		//gps = new logic.GPSTracker(ViewDarkhast.this);
		gps = com.farzaneganco.sant.sant.logic.Variables.user.GetGPSTracker(getApplicationContext());

		// check if GPS enabled
		if (gps != null) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			if (latitude == 0 && longitude == 0) {
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(), R.string.GPSError,
						Toast.LENGTH_LONG).show();
				return;
			}

			if (ConnectToInternet)
				SendGPSToWS();
			else
				SendGPSToDB();
			
		} else {
			pDialog.dismiss();
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	private void ShowProgressBar()
	{
		Resources res = getResources();
		ProgressBar myProgress = (ProgressBar) findViewById(R.id.progressbar1);
		
		myProgress.setMax(Inbox.SelectedItem.totalMohlatDaghighe); // Maximum Progress
		int Pishraft = Inbox.SelectedItem.totalMohlatDaghighe - Inbox.SelectedItem.mohlatEghdamMandeTotlaDaghighe;
		myProgress.setProgress(Pishraft); // Main Progress
		
		float darsad = (float) ((Pishraft * 1.0) / (Inbox.SelectedItem.totalMohlatDaghighe * 1.0)) * 100;
		TextView tvProgressDarsadValue = (TextView) findViewById(R.id.tvProgressDarsadValue);
		tvProgressDarsadValue.setText(Math.round(darsad) + " %");
		if (darsad > 60)
		{
			Drawable red_progressbar = res.getDrawable(R.drawable.red_progressbar);
			myProgress.setProgressDrawable(red_progressbar);
			tvProgressDarsadValue.setTextColor(getResources().getColor(R.color.Red));
		}
		else
		{
			Drawable green_progressbar = res.getDrawable(R.drawable.green_progressbar);
			myProgress.setProgressDrawable(green_progressbar);
			tvProgressDarsadValue.setTextColor(getResources().getColor(R.color.Green3));
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view);

		com.farzaneganco.sant.sant.logic.Menu.SetMenuItem(this); // نمایش منوهای برنامه

		imgGPS = (ImageView) findViewById(R.id.imgGPS);
		
		imgForward = (ImageView) findViewById(R.id.imgForward);

		com.farzaneganco.sant.sant.logic.Bind.ShowDarkhastInfo(this, "مشاهده");
		ImageView imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
		imgRefresh.setVisibility(View.GONE);

		imgForward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!com.farzaneganco.sant.sant.webservice.Common.IsInternetOn(getApplicationContext()))
				{
					Toast.makeText(getApplicationContext(),
							R.string.InternetDisconnect, Toast.LENGTH_LONG)
							.show();
					return;
				}
				
				//Intent go = new Intent(ViewDarkhast.this, com.far.sant.Forward.class);
				//startActivity(go);
			}
		});
		imgTajhizNotExists = (ImageView) findViewById(R.id.imgTajhizNotExists);
		imgTajhizNotExists.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Intent go = new Intent(ViewDarkhast.this, com.far.sant.TajhizNotExists.class);
				//startActivity(go);
			}
		});

		imgGPS.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SendGPS(com.farzaneganco.sant.sant.webservice.Common.IsInternetOn(getApplicationContext()));
				
				/*
				// TODO Auto-generated method stub
				if (!webservice.Common.IsInternetOn(getApplicationContext()))
				{					
					Toast.makeText(getApplicationContext(),
							R.string.InternetDisconnect, Toast.LENGTH_LONG)
							.show();
					return;					
				}
				else
					SendGPS();
				*/
			}
		});

		imgBack = (ImageView) findViewById(R.id.imgBack);
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		ShowProgressBar();
	}
}
