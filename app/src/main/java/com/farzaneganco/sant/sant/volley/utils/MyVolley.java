package com.farzaneganco.sant.sant.volley.utils;

import org.json.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class MyVolley extends JsonObjectRequest {
	
	public MyVolley(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
	}
	

	public MyVolley(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
	}

	private int mStatusCode;

    public int getStatusCode() {
        return mStatusCode;
    }

    @Override
	protected VolleyError parseNetworkError(VolleyError volleyError){
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                String msg = error.getMessage();
                msg = msg.substring(12, msg.length() - 2);
                
                volleyError = new VolleyError(msg);     
            }
        else
        {
        	volleyError = new VolleyError("خطا در شبکه");
        }

        return volleyError;
    }
}
