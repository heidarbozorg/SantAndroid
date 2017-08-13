package com.farzaneganco.sant.sant.webservice;

import org.json.JSONArray;

import com.farzaneganco.sant.sant.volley.utils.AppController;
import com.farzaneganco.sant.sant.volley.utils.ArrayRequest;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class Paye {
	
	/**
	 * دریافت رکوردهای مربوط به شرح اقدام بدون در نظر گرفتن نوع تجهیز
	 * @param callBack
	 * @return
	 */
	
	public static Boolean GetSharhEghdam(
			final Common.ArrayResult callBack) {
		try {
			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "Paye/GetAllSharhTamir?NameListId=-1";

			ArrayRequest jsonObjReq = new ArrayRequest(url,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							callBack.onSuccessVolleyFinished(response, null);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							callBack.OnErrorVolleyFinished(error, null);
						}
					});

			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(jsonObjReq,
					tag_json_obj);

			return (true);
		} catch (Exception e) {
			Log.i("GetSharhEghdam",
					"Error in webservice.Paye.GetSharhEghdam, Error: " + e.getMessage());
			return (false);
		}
	}
}
