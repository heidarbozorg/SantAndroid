package com.farzaneganco.sant.sant.volley.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

public class ArrayRequest extends JsonRequest<JSONArray> {

    /**
     * Creates a new request.
     * @param url URL to fetch the JSON from
     * @param listener Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public ArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

	@Override
    protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", "value");
        return params;
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
    
    @Override
	protected VolleyError parseNetworkError(VolleyError volleyError){
    	Log.d("ArrayRequest", "getMessage(): " + volleyError.getMessage() + ", getLocalizedMessage:" + volleyError.getLocalizedMessage());
    	volleyError.printStackTrace();
    	
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
