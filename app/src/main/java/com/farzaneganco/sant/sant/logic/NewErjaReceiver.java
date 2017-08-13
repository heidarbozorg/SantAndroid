package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;

import org.json.JSONArray;

import com.farzaneganco.sant.sant.webservice.Common.ArrayResult;

import com.android.volley.VolleyError;
import com.farzaneganco.sant.sant.R;
//import com.farzaneganco.sant.sant.ViewDarkhast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class NewErjaReceiver extends BroadcastReceiver {
	//private static final int NOTIFY_ME_ID = 1337;

	public static void SendOfflineInfo(Context con)
	{
		//Toast.makeText(con, "Start SendOfflineInfo", Toast.LENGTH_LONG).show();
		com.farzaneganco.sant.sant.webservice.WFlow.SendDarkhastGPS(con);
		com.farzaneganco.sant.sant.webservice.WFlow.SendRaftamNabood(con);
		com.farzaneganco.sant.sant.webservice.User.SendPersonTrace(con);
		//Toast.makeText(con, "End SendOfflineInfo", Toast.LENGTH_LONG).show();
	}	
	
	
	private void SaveGPS(Context con)
	{
		try
		{
			if (com.farzaneganco.sant.sant.logic.Variables.user.GetPersonelId() <= 0)
				return;
			
			double latitude = 0, longitude = 0;
			//logic.GPSTracker gps = logic.Variables.user.GetGPSTracker(con);
			com.farzaneganco.sant.sant.logic.GPSTracker gps = new com.farzaneganco.sant.sant.logic.GPSTracker(con);
			
			if (gps == null) {
				//Toast.makeText(con, "in SaveGPSTrace gps is null", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (gps.canGetLocation()) {
				latitude = gps.getLatitude();
				longitude = gps.getLongitude();			
			
				if (latitude != 0 && longitude != 0)
				{
					int gpsInsert = com.farzaneganco.sant.sant.logic.DP.PersonTrace.Insert(con,
						com.farzaneganco.sant.sant.logic.Variables.user.GetPersonelId(),
							latitude, 
							longitude);					
				}
				//else Toast.makeText(con, "in SaveGPSTrace latitude and longitude is 0", Toast.LENGTH_LONG).show();
			}
			//else Toast.makeText(con, "in SaveGPSTrace can not getLocation", Toast.LENGTH_LONG).show();
		}
		catch(Exception err)
		{
			Log.i("NewErjaReceiver", "Error in SaveGPS: " + err.getMessage());
		}
	}
	
	@Override
	public void onReceive(Context con, Intent arg1) {
		final Context ctx = con;
		try {
			Log.i("CheckNewInbox", "NewErjaReceiver.onReceive");
			int PersonelId = Bind.GetPersonelId(ctx);
			int MySazmanId = Bind.GetMySazmanId(ctx);

			com.farzaneganco.sant.sant.webservice.WFlow.GetInbox(null, new ArrayResult() {

				@Override
				public void onSuccessVolleyFinished(JSONArray response,
						User user) {
					if (response == null || response.length() == 0)
						return;

					ArrayList<com.farzaneganco.sant.sant.logic.WFlow> q = com.farzaneganco.sant.sant.logic.WFlow.Convert(response, ctx);
					if (q == null || q.size() == 0)
						return;

					com.farzaneganco.sant.sant.logic.WFlow fq = q.get(0);
					int LastErjaDarkhastId = com.farzaneganco.sant.sant.logic.Bind.GetLastErjaId(ctx);
					if (fq.ErjaDarkhastId > LastErjaDarkhastId) {
						// آی دی آخرین کار را در حافظه ذخیره میکنیم
						com.farzaneganco.sant.sant.logic.Bind.SaveLastErja(ctx, fq);
						
						//به کاربر یک پیغام صدا دار به همراه متن میدهیم
						notifyMe(ctx, fq.SharhErja);
					}
				}

				@Override
				public void OnErrorVolleyFinished(VolleyError error, User user) {
					Log.i("Inbox",
							"Error in CheckNewInbox: " + error.getMessage());
				}
			}, 0, 1, PersonelId, MySazmanId);
			
			SendOfflineInfo(con);
			SaveGPS(con);
			
		} catch (Exception e) {
			Toast.makeText(ctx, "Error in onReceive: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	public static void PlaySoundNotification(Context ctx)
	{
		try {
		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    Ringtone r = RingtoneManager.getRingtone(ctx, notification);
		    r.play();
		} catch (Exception e) {
			Log.i("NewErjaReceiver", "Error on PlaySoundNotification: " + e.getMessage());
		    e.printStackTrace();
		}
	}
	
	
	public static void notifyMe(Context ctx, String message) {
		/*
		try {
			
			NotificationManager notificationManager  = (NotificationManager) ctx
					.getSystemService(ctx.NOTIFICATION_SERVICE);
			
			Notification notification  = new Notification(R.drawable.message, "new message in inbox", System.currentTimeMillis());

			//Intent notificationIntent = new Intent(ctx, com.far.sant.Inbox.class);
			Intent notificationIntent = new Intent(ctx, com.farzaneganco.sant.sant.SplashActivity.class);
			
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		            | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		    PendingIntent intent = PendingIntent.getActivity(ctx, 0,
		            notificationIntent, 0);

		    notification.setLatestEventInfo(ctx, "ارجاع جدید در سانت", message, intent);
		    notification.flags |= Notification.FLAG_AUTO_CANCEL;
		    notificationManager.notify(0, notification);
			PlaySoundNotification(ctx);
		} catch (Exception e) {
			Toast.makeText(ctx, "Error in notifyMe: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
		*/
	}
}
