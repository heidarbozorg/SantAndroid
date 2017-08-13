package com.farzaneganco.sant.sant.volley.utils;

import java.util.Map;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class MyVolleyString extends StringRequest {
	
	private Map<String, String> params;
	
	public MyVolleyString(int method, String url, Listener<String> listener,
			ErrorListener errorListener, Map<String, String> p) {
		super(method, url, listener, errorListener);
		this.params = p;
	}

	public MyVolleyString(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}
	
	public MyVolleyString(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
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
    
    @Override
	protected Map<String, String> getParams() {
		return params;
	}
}