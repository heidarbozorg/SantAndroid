package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.farzaneganco.sant.sant.ViewDarkhast;

import android.content.Context;
import android.util.Log;

public class User {
	private String Username;
	private String Password;

	public String FName;
	public String LName;
	public String FullName;
	public String Semat;
	private int PersonelId;
	public int ChartShoghlId;
	public int PrsnShoghlId;
	public String SharhErja;
	public int Olaviat;

	public Boolean Checked;

	public int MySazmanId;

	com.farzaneganco.sant.sant.logic.GPSTracker gps;

	// *******************************************************************************************
	// ********************Set and Get
	// method*****************************************************
	// *******************************************************************************************
	public String GetUsername() {
		return (this.Username);
	}

	public void SetUsername(String val) {
		this.Username = val;
	}

	public String GetPassword() {
		return (this.Password);
	}

	public void SetPassword(String val) {
		this.Password = val;
	}

	public int GetPersonelId() {
		return (this.PersonelId);
	}

	public com.farzaneganco.sant.sant.logic.GPSTracker GetGPSTracker(Context context, int tryNumber) {
		try {
			if (tryNumber > 10)
				return (this.gps);

			if (this.gps == null) {
				this.gps = new com.farzaneganco.sant.sant.logic.GPSTracker(context);
				return (GetGPSTracker(context, tryNumber + 1));
			} else {
				if (tryNumber == 2)
					this.gps = new com.farzaneganco.sant.sant.logic.GPSTracker(context);

				if (this.gps.canGetLocation())
					return (this.gps);
				else
					return (GetGPSTracker(context, tryNumber + 1));
			}
		} catch (Exception err) {
			Log.i("User", "Error in GetGPSTracker: " + err.getMessage());
			return (null);
		}

	}

	public com.farzaneganco.sant.sant.logic.GPSTracker GetGPSTracker(Context context) {
		return (GetGPSTracker(context, 0));
	}

	public void SetPersonelId(int val) {
		this.PersonelId = val;
	}

	// *******************************************************************************************
	// ********************Set and Get
	// method*****************************************************
	// *******************************************************************************************

	public String GetIpAddress() {
		return ("192.168.25.120");
	}

	public User(String u, String p) {
		this.Username = u;
		this.Password = p;
		this.PersonelId = 0;
	}

	public User(int PersonelId, int ChartShoghlId, int PrsnShoghlId,
			String FullName, String SharhErja, int Olaviat) {
		this.FullName = FullName;
		if (FullName != null && FullName != "") {
			String[] splitedName = FullName.split("-");
			if (splitedName != null && splitedName.length > 1) {
				this.FullName = splitedName[0];
				this.Semat = splitedName[1];
			}
		}

		this.PersonelId = PersonelId;
		this.ChartShoghlId = ChartShoghlId;
		this.PrsnShoghlId = PrsnShoghlId;

		this.SharhErja = SharhErja;
		this.Olaviat = Olaviat;
		this.Checked = false;
	}

	public static ArrayList<User> Convert(JSONArray response) {
		if (response == null)
			return (null);

		try {
			ArrayList<User> rst = new ArrayList<User>();
			for (int i = 0; i < response.length(); i++) {
				JSONObject item = response.getJSONObject(i);

				int PrsnShoghlId = item.getInt("prsnShoghlId");
				int ChartShoghlId = item.getInt("chartShoghlId");
				int PersonelId = item.getInt("personelId");
				String GirandeName = item.getString("girandeName");
				String SharhErja = item.getString("sharhErja");
				int Olaviat = item.getInt("olaviat");

				User ii = new User(PersonelId, ChartShoghlId, PrsnShoghlId,
						GirandeName, SharhErja, Olaviat);

				rst.add(ii);
			}

			Log.i("WFlow",
					"After succcessfully convert. Records: " + rst.size());

			return (rst);
		} catch (Exception e) {
			Log.i("WFlow",
					"Error on convert to WFlow object: " + e.getMessage());
			return (null);
		}
	}
}
