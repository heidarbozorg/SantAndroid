package com.farzaneganco.sant.sant.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.farzaneganco.sant.sant.logic.User;
import com.farzaneganco.sant.sant.logic.DP.DarkhastGPS;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import com.farzaneganco.sant.sant.volley.utils.AppController;
import com.farzaneganco.sant.sant.volley.utils.ArrayRequest;
import com.farzaneganco.sant.sant.webservice.Common.StringResult;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.far.sant.Inbox;

public class WFlow {

	/**
	 * دریافت ارجاع های جدید
	 * 
	 * @param user
	 * @param callBack
	 * @param startIndex
	 * @param maximumRows
	 * @param PersonelId
	 * @param MySazmanId
	 * @return
	 */
	public static Boolean GetInbox(final com.farzaneganco.sant.sant.logic.User user,
			final Common.ArrayResult callBack, int startIndex, int maximumRows,
			int PersonelId, int MySazmanId) {
		try {
			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "ErjaDarkhast/GetAllJary?MySazmanId=" + MySazmanId
					+ "&PersonelId_Requester=" + PersonelId + "&startIndex="
					+ startIndex + "&maximumRows=" + maximumRows;

			ArrayRequest jsonObjReq = new ArrayRequest(url,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							// Log.d("GetInbox", response.toString());
							callBack.onSuccessVolleyFinished(response, user);
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							// Log.d("GetInbox", "error in GetInbox");
							callBack.OnErrorVolleyFinished(error, user);
						}
					});

			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(jsonObjReq,
					tag_json_obj);

			return (true);
		} catch (Exception e) {
			Log.i("GetInbox",
					"Error in webservice.WFlow.GetInbox, PersonelId: "
							+ PersonelId + ", Error: " + e.getMessage());
			return (false);
		}
	}

	public static Boolean GetInbox(final com.farzaneganco.sant.sant.logic.User user,
			final Common.ArrayResult callBack, int startIndex, int maximumRows) {
		return (GetInbox(user, callBack, startIndex, maximumRows,
				user.GetPersonelId(), user.MySazmanId));
	}

	public static Boolean GetInbox(final com.farzaneganco.sant.sant.logic.User user,
			final Common.ArrayResult callBack) {
		return (GetInbox(user, callBack, 0, 20));
	}

	public static Boolean SetAsRead(final int ErjaDarkhastId) {
		try {
			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "ErjaDarkhast/PutRoayt?ErjaDarkhastId=" + ErjaDarkhastId;

			JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.PUT,
					url, null, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							// Log.d("SetAsRead successfully",
							// response.toString());
							Log.i("SetAsRead", "WFlow.SetAsRead successfully");
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.i("SetAsRead",
									"Error in webservice.WFlow.SetAsRead, ErjaDarkhastId: "
											+ ErjaDarkhastId
											+ ", onErrorResponse Error: "
											+ error.getMessage());
						}
					});

			String tag_json_obj = "json_obj_req";
			AppController.getInstance().addToRequestQueue(jsonObjReq,
					tag_json_obj);
			return (true);
		} catch (Exception e) {
			Log.i("SetAsRead",
					"Error in webservice.WFlow.SetAsRead, ErjaDarkhastId: "
							+ ErjaDarkhastId + ", Error: " + e.getMessage());
			return (false);
		}
	}

	public static Boolean SaveFinalResponse(final com.farzaneganco.sant.sant.logic.User user,
			final com.farzaneganco.sant.sant.logic.WFlow q, String Pasokh, Boolean GhabeleAnjam,
			Boolean AnjamShod, final Common.StringResult callBack) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("DarkhastKarId", String.valueOf(q.DarkhastKarId));
		params.put("ErjaDarkhastId", String.valueOf(q.ErjaDarkhastId));
		params.put("PersonelId_Pasokh", String.valueOf(user.GetPersonelId()));
		params.put("Pasokh", Pasokh);
		params.put("GhabelAnjam", String.valueOf(GhabeleAnjam));
		params.put("AnjamShod", String.valueOf(AnjamShod));
		String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
				+ "ErjaDarkhast/PostFinalPasokhKar";
		return (Common.CallPostString(url, params, callBack, user));
	}

	public static Boolean PostEghdam(Context context, final com.farzaneganco.sant.sant.logic.User user,
			final com.farzaneganco.sant.sant.logic.WFlow q, final String TarikhEghdam,
			final String azSaat, final String taSaat, final String SharhEghdam,
			final Common.StringResult callBack) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("DarkhastKarId", String.valueOf(q.DarkhastKarId));
		params.put("ErjaDarkhastId", String.valueOf(q.ErjaDarkhastId));
		params.put("PersonelId", String.valueOf(user.GetPersonelId()));
		params.put("TarikhEghdam", TarikhEghdam);
		params.put("azSaat", azSaat);
		params.put("taSaat", taSaat);
		params.put("SharhEghdam", SharhEghdam);

		String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
				+ "ErjaDarkhast/PostEghdam";
		return (Common.CallPostString(url, params, callBack, user));
	}

	public static Boolean Forward(final com.farzaneganco.sant.sant.logic.User user, final com.farzaneganco.sant.sant.logic.WFlow q,
			ArrayList<com.farzaneganco.sant.sant.logic.User> SelectedUsers, String MohlatEghdam,
			int SabetId_Olaviat, Boolean AnjamShod, String Hamesh,
			final Common.StringResult callBack) {
		try {
			String ChartShoghlId_Girande = "", PersonelId_Girande = "";
			for (com.farzaneganco.sant.sant.logic.User user2 : SelectedUsers) {
				if (ChartShoghlId_Girande == "") {
					ChartShoghlId_Girande = String.valueOf(user2.ChartShoghlId);
					PersonelId_Girande = String.valueOf(user2.GetPersonelId());
				} else {
					ChartShoghlId_Girande = ChartShoghlId_Girande + ","
							+ user2.ChartShoghlId;
					PersonelId_Girande = PersonelId_Girande + ","
							+ user2.GetPersonelId();
				}
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("ErjaDarkhastId", String.valueOf(q.ErjaDarkhastId));
			params.put("ChartShoghlId_Girande", ChartShoghlId_Girande);
			params.put("SabetId_Olaviat", String.valueOf(SabetId_Olaviat));
			params.put("Hamesh", Hamesh);
			params.put("MohlatEghdam", MohlatEghdam);
			params.put("PersonelId_Girande", PersonelId_Girande);
			params.put("AnjamShod", String.valueOf(AnjamShod));

			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "ErjaDarkhast/PostForward";
			return (Common.CallPostString(url, params, callBack, user));

		} catch (Exception e) {
			Log.i("Forward", "Error in webservice.WFlow.Forward, Username: "
					+ user.GetUsername() + ", Error: " + e.getMessage());
			return (false);
		}
	}

	public static Boolean EghdamAndForward(final com.farzaneganco.sant.sant.logic.User user,
			final com.farzaneganco.sant.sant.logic.WFlow q, int SharhEghdam1, int SharhEghdam2,
			int SharhEghdam3, int SharhEghdam4, String SharhEghdam,
			String TarikhEghdam, String AzSaatEghdam, String TaSaatEghdam,
			ArrayList<com.farzaneganco.sant.sant.logic.User> SelectedUsers, String MohlatEghdam,
			int SabetId_Olaviat, Boolean AnjamShod, String Hamesh,
			final Common.StringResult callBack) {
		try {
			String ChartShoghlId_Girande = "", PersonelId_Girande = "";
			for (com.farzaneganco.sant.sant.logic.User user2 : SelectedUsers) {
				if (ChartShoghlId_Girande == "") {
					ChartShoghlId_Girande = String.valueOf(user2.ChartShoghlId);
					PersonelId_Girande = String.valueOf(user2.GetPersonelId());
				} else {
					ChartShoghlId_Girande = ChartShoghlId_Girande + ","
							+ user2.ChartShoghlId;
					PersonelId_Girande = PersonelId_Girande + ","
							+ user2.GetPersonelId();
				}
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("DarkhastKarId", String.valueOf(q.DarkhastKarId));
			params.put("ErjaDarkhastId", String.valueOf(q.ErjaDarkhastId));
			params.put("PersonelId", String.valueOf(user.GetPersonelId()));
			params.put("TarikhEghdam", TarikhEghdam);
			params.put("azSaat", AzSaatEghdam);
			params.put("taSaat", TaSaatEghdam);

			params.put("SharhEghdam1", String.valueOf(SharhEghdam1));
			params.put("SharhEghdam2", String.valueOf(SharhEghdam2));
			params.put("SharhEghdam3", String.valueOf(SharhEghdam3));
			params.put("SharhEghdam4", String.valueOf(SharhEghdam4));

			params.put("SharhEghdam", SharhEghdam);

			params.put("ChartShoghlId_Girande", ChartShoghlId_Girande);
			params.put("SabetId_Olaviat", String.valueOf(SabetId_Olaviat));
			params.put("Hamesh", Hamesh);
			params.put("MohlatEghdam", MohlatEghdam);
			params.put("PersonelId_Girande", PersonelId_Girande);
			params.put("AnjamShod", String.valueOf(AnjamShod));

			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "ErjaDarkhast/PostEghdamAndForward";
			return (Common.CallPostString(url, params, callBack, user));

		} catch (Exception e) {
			Log.i("Forward",
					"Error in webservice.WFlow.EghdamAndForward, Username: "
							+ user.GetUsername() + ", Error: " + e.getMessage());
			return (false);
		}
	}

	public static Boolean NiazBeGhete(final com.farzaneganco.sant.sant.logic.User user,
			final com.farzaneganco.sant.sant.logic.WFlow q, ArrayList<com.farzaneganco.sant.sant.logic.User> SelectedUsers,
			final Common.StringResult callBack) {
		try {
			String ChartShoghlId_Girande = "", PersonelId_Girande = "";
			for (com.farzaneganco.sant.sant.logic.User user2 : SelectedUsers) {
				if (ChartShoghlId_Girande == "") {
					ChartShoghlId_Girande = String.valueOf(user2.ChartShoghlId);
					PersonelId_Girande = String.valueOf(user2.GetPersonelId());
				} else {
					ChartShoghlId_Girande = ChartShoghlId_Girande + ","
							+ user2.ChartShoghlId;
					PersonelId_Girande = PersonelId_Girande + ","
							+ user2.GetPersonelId();
				}
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("ErjaDarkhastId", String.valueOf(q.ErjaDarkhastId));
			params.put("ChartShoghlId_Girande", ChartShoghlId_Girande);
			params.put("PersonelId_Girande", PersonelId_Girande);
			params.put("PersonelId", String.valueOf(user.GetPersonelId()));

			String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
					+ "ErjaDarkhast/PostNiazBeGhete";
			return (Common.CallPostString(url, params, callBack, user));

		} catch (Exception e) {
			Log.i("Forward",
					"Error in webservice.WFlow.NiazBeGhete, Username: "
							+ user.GetUsername() + ", Error: " + e.getMessage());
			return (false);
		}
	}

	
	/**
	 * ارسال اعلام در دسترس نبودن هایی که در دیتابیس محلی ذخیره شده
	 * 
	 * @param context
	 */
	public static void SendRaftamNabood(final Context context) {
		final Context ctx = context;
		ArrayList<com.farzaneganco.sant.sant.logic.DP.RaftamNabood> arr = com.farzaneganco.sant.sant.logic.DP.RaftamNabood
				.GetAll(context);
		
		if (arr == null || arr.size() == 0)
			return;
		
		//Toast.makeText(context, "RaftamNabood.len: " + arr.size(), Toast.LENGTH_LONG).show();

		for (int i = 0; i < arr.size(); i++) {
			final com.farzaneganco.sant.sant.logic.DP.RaftamNabood item = arr.get(i);
			SendTajhizNotExists
			(
				item.DarkhastKarId, 
				item.ErjaDarkhastId,
				item.PersonelId, 
				item.Tarikh, 
				item.Saat, 
				item.Sharh,
				new com.farzaneganco.sant.sant.webservice.Common.StringResult() {

					@Override
					public void onSuccessVolleyFinished(String response, User user) {
						// در صورت موفقیت آمیز بودن عملیات
						com.farzaneganco.sant.sant.logic.DP.RaftamNabood.Delete(ctx, item.ErjaDarkhastId);
					}

					@Override
					public void OnErrorVolleyFinished(VolleyError error,
							User user) {
						if (error.getMessage().contains("اطلاعات"))
							Toast.makeText(context, "repeat SendRaftamNabood", Toast.LENGTH_LONG).show();
						else
							Toast.makeText(context, "خطا در ارسال اطلاعات رفتم نبود: " + error.getMessage(), Toast.LENGTH_LONG).show();
						
						Log.i("SendRaftamNabood", "Error in SendRaftamNabood response: " + error.getMessage());
						
					}
				}
			);

		}//end for
	}

	
	/**
	 * ارسال اعلام حضوضرهایی که در دیتابیس محلی ذخیره شده
	 * 
	 * @param context
	 */
	public static void SendDarkhastGPS(final Context context) {
		final Context ctx = context;
		ArrayList<com.farzaneganco.sant.sant.logic.DP.DarkhastGPS> arr = com.farzaneganco.sant.sant.logic.DP.DarkhastGPS
				.GetAll(context);
		if (arr == null || arr.size() == 0)
			return;
		
		//Toast.makeText(context, "DarkhastGPS.len: " + arr.size(), Toast.LENGTH_LONG).show();

		for (int i = 0; i < arr.size(); i++) {
			final com.farzaneganco.sant.sant.logic.DP.DarkhastGPS item = arr.get(i);
			SendGPS(item.DarkhastKarId, 
					item.ErjaDarkhastId, 
					item.PersonelId,
					item.Latitude, 
					item.Longitude, 
					item.Tarikh, 
					item.Saat,
					new StringResult() {

						@Override
						public void onSuccessVolleyFinished(String response,
								User user) {
							// TODO Auto-generated method stub
							com.farzaneganco.sant.sant.logic.DP.DarkhastGPS.Delete(ctx, item.ErjaDarkhastId);
						}

						@Override
						public void OnErrorVolleyFinished(VolleyError error,
								User user) {
							// TODO Auto-generated method stub
							if (error.getMessage().contains("اطلاعات"))
								com.farzaneganco.sant.sant.logic.DP.DarkhastGPS.Delete(ctx, item.ErjaDarkhastId);
							else
								Toast.makeText(context, "خط در ارسال اطلاعات تعیین موقعیت جغرافیایی: " + error.getMessage(), Toast.LENGTH_LONG).show();
						}
					});

		}
	}

	
	public static Boolean SendGPS(int DarkhastKarId, int ErjaDarkhastId,
			int PersonelId, double Latitude, double Longitude, String Tarikh,
			String Saat, final Common.StringResult callBack) {

		Map<String, String> params = new HashMap<String, String>();

		params.put("DarkhastKarId", String.valueOf(DarkhastKarId));
		params.put("ErjaDarkhastId", String.valueOf(ErjaDarkhastId));
		params.put("PersonelId", String.valueOf(PersonelId));
		params.put("Latitude", String.valueOf(Latitude));
		params.put("Longitude", String.valueOf(Longitude));
		params.put("azSaat", Saat);
		params.put("TarikhEghdam", Tarikh);
		String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
				+ "ErjaDarkhast/PostEghdamGPS";

		return (Common.CallPostString(url, params, callBack, null));
	}
	

	/**
	 * اعلام عدم دسترسی به تجهیز
	 * 
	 * @param DarkhastKarId
	 * @param ErjaDarkhastId
	 * @param PersonelId
	 * @param Tarikh
	 * @param Saat
	 * @param SharhEghdam
	 * @param callBack
	 * @return
	 */
	public static Boolean SendTajhizNotExists(int DarkhastKarId,
			int ErjaDarkhastId, int PersonelId, String Tarikh, String Saat,
			String SharhEghdam, final Common.StringResult callBack) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("DarkhastKarId", String.valueOf(DarkhastKarId));
		params.put("ErjaDarkhastId", String.valueOf(ErjaDarkhastId));
		params.put("PersonelId", String.valueOf(PersonelId));
		params.put("azSaat", Saat);
		params.put("TarikhEghdam", Tarikh);
		params.put("SharhEghdam", SharhEghdam);
		String url = com.farzaneganco.sant.sant.logic.Variables.WebServiceAddress
				+ "ErjaDarkhast/PostEghdamTajhizNotFound";

		return (Common.CallPostString(url, params, callBack, null));
	}
}