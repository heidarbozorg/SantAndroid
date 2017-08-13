package com.farzaneganco.sant.sant.webservice;

import org.json.JSONObject;

import com.farzaneganco.sant.sant.volley.utils.AppController;
import com.farzaneganco.sant.sant.volley.utils.MyVolley;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;

public class Tajhiz {
	public static Boolean GetTajhiz(final com.farzaneganco.sant.sant.logic.WFlow wflow, final Common.TajhizResult callBack) {
		try {
			MyVolley jsonObjReq = new MyVolley(Method.GET,
				com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress + "Tajhiz/GetTajhiz?TajhizId=" + wflow.TajhizId, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							callBack.onSuccessVolleyFinished(response, wflow);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							callBack.OnErrorVolleyFinished(error, wflow);
						}
					}) { };
			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
			return (true);
		} catch (Exception e) {
			Log.i("Login",
					"Error in webservice.Tajhiz.GetTajhiz, TajhizId: "
							+ wflow.TajhizId + ", Error: " + e.getMessage());
			return (false);
		}
	}
}
