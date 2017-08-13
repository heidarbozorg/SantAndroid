package com.farzaneganco.sant.sant.webservice;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.farzaneganco.sant.sant.volley.utils.AppController;

public class Common {
	/**
	 * بررسی اتصال دستگاه به اینترنت
	 * @param ctx
	 * @return
	 */
	public static boolean IsInternetOn(Context ctx) {

		// get Connectivity Manager object to check connection
		ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);

		// Check for network connections
		if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
			return true;

		} else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

			return false;
		}
		return false;
	}
	
	public interface Result {
		void onSuccessVolleyFinished(JSONObject response, com.farzaneganco.sant.sant.logic.User user);

		void OnErrorVolleyFinished(VolleyError error, com.farzaneganco.sant.sant.logic.User user);
	}
	
	public interface TajhizResult {
		void onSuccessVolleyFinished(JSONObject response, com.farzaneganco.sant.sant.logic.WFlow wflow);

		void OnErrorVolleyFinished(VolleyError error, com.farzaneganco.sant.sant.logic.WFlow wflow);
	}

	public interface ArrayResult {
		void onSuccessVolleyFinished(JSONArray response, com.farzaneganco.sant.sant.logic.User user);

		void OnErrorVolleyFinished(VolleyError error, com.farzaneganco.sant.sant.logic.User user);
	}

	public interface StringResult {
		void onSuccessVolleyFinished(String response, com.farzaneganco.sant.sant.logic.User user);

		void OnErrorVolleyFinished(VolleyError error, com.farzaneganco.sant.sant.logic.User user);
	}

	public static Boolean CallStringRequest(String url,
			final StringResult callBack, final com.farzaneganco.sant.sant.logic.User user) {
		try {
			String EditUrl = url.replaceAll(" ", "%20").replaceAll("\n", "%0A");
			com.farzaneganco.sant.sant.volley.utils.MyVolleyString sReq = new com.farzaneganco.sant.sant.volley.utils.MyVolleyString(Method.GET, EditUrl,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							callBack.onSuccessVolleyFinished(response, user);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							callBack.OnErrorVolleyFinished(error, user);
						}
					}) {
			};

			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(sReq, tag_json_obj);
			return (true);
		} catch (Exception e) {
			Log.i("CallStringRequest",
					"Error in webservice.Common.CallStringRequest, url: " + url
							+ ", Error: " + e.getMessage());
			return (false);
		}
	}

	public static Boolean CallPostString(String url,
			final Map<String, String> params, final StringResult callBack,
			final com.farzaneganco.sant.sant.logic.User user) {
		try {
			com.farzaneganco.sant.sant.volley.utils.MyVolleyString sr = new com.farzaneganco.sant.sant.volley.utils.MyVolleyString(Method.POST, url,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							callBack.onSuccessVolleyFinished(response, user);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							callBack.OnErrorVolleyFinished(error, user);
						}
					}, params) {
			};

			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(sr, tag_json_obj);
			return (true);
		} catch (Exception errCatch) {
			Log.i("Forward", "Error in webservice.WFlow.PostEghdam, Username: "
					+ user.GetUsername() + ", Error: " + errCatch.getMessage());
			return (false);
		}
	}
}