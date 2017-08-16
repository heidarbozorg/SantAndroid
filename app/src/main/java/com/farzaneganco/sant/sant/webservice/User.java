package com.farzaneganco.sant.sant.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.farzaneganco.sant.sant.volley.utils.AppController;
import com.farzaneganco.sant.sant.volley.utils.ArrayRequest;
import com.farzaneganco.sant.sant.volley.utils.MyVolley;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class User {
	public static Boolean Login(final com.farzaneganco.sant.sant.logic.User user, final Common.Result callBack) {
		try {
			String UserName = user.GetUsername();
			//به دلیل محدودیت هایی که برای ارسال آدرس ایمیل یاهو از طریق کوری استرینگ وجود دارد، مجبور به اعمال این تغییرات شدیم
			UserName = UserName.toLowerCase().replace("@yahoo.com", "@ansarcoyahoo.ir");
			Log.i("Login", "In webservice.User.Login, replace "
							+ user.GetUsername() + " to "
							+ UserName);
			
			MyVolley jsonObjReq = new MyVolley(Method.GET,
				com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress + "User/Login?Username=" + UserName
				  + "&Password=" + user.GetPassword(), null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							callBack.onSuccessVolleyFinished(response, user);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							callBack.OnErrorVolleyFinished(error, user);
						}
					}) { };
			String tag_json_obj = "json_obj_req";

			AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
			return (true);
		} catch (Exception e) {
			Log.i("Login",
					"Error in webservice.User.Login, Username: "
							+ user.GetUsername() + ",  Password: "
							+ user.GetPassword() + ", Error: " + e.getMessage());
			return (false);
		}
	}

	public static Boolean GetPersonForForward(final int ErjaDarkhastId,
			final Common.ArrayResult callBack) {
		try {
			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "User/GetGirande?ErjaDarkhastId=" + ErjaDarkhastId;
			
			ArrayRequest jsonObjReq = new ArrayRequest(url,
	                new Response.Listener<JSONArray>() {
	                    @Override
	                    public void onResponse(JSONArray response) {
	                        //Log.d("GetInbox", response.toString());
	                        callBack.onSuccessVolleyFinished(response, null);
	                    }
	                }, new Response.ErrorListener() {
	                    @Override
	                    public void onErrorResponse(VolleyError error) {
	                    	callBack.OnErrorVolleyFinished(error, null);
	                    }
	                });
		
			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
			
			return (true);
		} catch (Exception e) {
			Log.i("GetInbox",
					"Error in webservice.User.GetPersonForForward, ErjaDarkhastId: "
							+ ErjaDarkhastId + ", Error: " + e.getMessage());
			return (false);
		}
	}

	
	/**
	 * ارسال اعلام در دسترس نبودن هایی که در دیتابیس محلی ذخیره شده
	 * 
	 * @param context
	 */
	public static void SendPersonTrace(final Context context) {
		final Context ctx = context;
		ArrayList<com.farzaneganco.sant.sant.logic.DP.PersonTrace> arr = com.farzaneganco.sant.sant.logic.DP.PersonTrace
				.GetAll(context);
		
		if (arr == null || arr.size() == 0)
			return;
		
		//Toast.makeText(context, "PersonTrace.len: " + arr.size(), Toast.LENGTH_LONG).show();

		for (int i = 0; i < arr.size(); i++) {
			final com.farzaneganco.sant.sant.logic.DP.PersonTrace item = arr.get(i);
			SendPersonTrace
			(
				item.PersonelId, 
				item.Tarikh, 
				item.Saat, 
				item.Latitude,
				item.Longitude,
				new com.farzaneganco.sant.sant.webservice.Common.StringResult() {

					@Override
					public void onSuccessVolleyFinished(String response,
																							com.farzaneganco.sant.sant.logic.User user) {
						// در صورت موفقیت آمیز بودن عملیات
						com.farzaneganco.sant.sant.logic.DP.PersonTrace.Delete(ctx, item.PersonelId,
								item.Tarikh, item.Saat);
					}

					@Override
					public void OnErrorVolleyFinished(VolleyError error,
																						com.farzaneganco.sant.sant.logic.User user) {
						Log.i("PersonTrace", "Error in SendPersonTrace response: " + error.getMessage());	
						//Toast.makeText(context, "Error in SendPersonTrace: " + error.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			);

		}//end for
	}

	
	public static Boolean SendPersonTrace(
			int PersonelId, 
			String Tarikh, 
			String Saat,
			double Latitude, 
			double Longitude,			
			final Common.StringResult callBack) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("PersonelId", String.valueOf(PersonelId));
		params.put("Tarikh", Tarikh);
		params.put("Saat", Saat);
		params.put("Latitude", String.valueOf(Latitude));
		params.put("Longitude", String.valueOf(Longitude));
		String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
				+ "User/PostGPS";

		return (Common.CallPostString(url, params, callBack, null));
	}
	


}
