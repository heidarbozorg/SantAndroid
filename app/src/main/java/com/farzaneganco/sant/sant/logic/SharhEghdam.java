package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.farzaneganco.sant.sant.webservice.Common;
import com.farzaneganco.sant.sant.webservice.Common.ArrayResult;
import android.util.Log;

import com.android.volley.VolleyError;

public class SharhEghdam {
	public int sharhTamirId, sharhTamirId_Parent, nameListId;
	public String sharhEghdam;

	public SharhEghdam(int id, int parent, int namelistid, String name) {
		this.sharhTamirId = id;
		this.sharhTamirId_Parent = parent;
		this.nameListId = namelistid;
		this.sharhEghdam = name;
		
		//Log.i("SharhEghdam", "SharhEghdam(" + id + ", " + parent+ ", " + name + ")");
	}

	/**
	 * در کومبوباکسها مورد استفاده قرار میگیرد و متن آیتم انتخاب شده را نمایش
	 * میدهد
	 */
	public String toString() {
		return (this.sharhEghdam);
	}

	public static void GetFromWebservice() {
		com.farzaneganco.sant.sant.webservice.Paye.GetSharhEghdam(new ArrayResult() {
			@Override
			public void onSuccessVolleyFinished(JSONArray response, User user) {
				// TODO Auto-generated method stub
				Variables.SharhEghdamList = Convert(response);
			}

			@Override
			public void OnErrorVolleyFinished(VolleyError error, User user) {
				// TODO Auto-generated method stub
				Log.i("SharhEghdam", "Error: " + error.getMessage());
			}
		});
	}

	public static ArrayList<SharhEghdam> Convert(JSONArray response) {
		if (response == null)
			return (null);

		try {
			ArrayList<SharhEghdam> rst = new ArrayList<SharhEghdam>();
			for (int i = 0; i < response.length(); i++) {
				JSONObject item = response.getJSONObject(i);
				int sharhTamirId = item.getInt("sharhTamirId");
				int sharhTamirId_Parent = -1;
				try {
					sharhTamirId_Parent = item.getInt("sharhTamirId_Parent");
				} catch (Exception err) {
					sharhTamirId_Parent = -1;
				}
				int nameListId = item.getInt("nameListId");

				String sharhEghdam = item.getString("sharhEghdam");

				SharhEghdam ii = new SharhEghdam(sharhTamirId,
						sharhTamirId_Parent, nameListId, sharhEghdam);

				rst.add(ii);
			}

			Log.i("SharhEghdam",
					"in convert SharhEghdam.rst.size(): " + rst.size());

			return (rst);
		} catch (Exception e) {
			Log.i("SharhEghdam",
					"Error on convert to SharhEghdam object: " + e.getMessage());
			return (null);
		}
	}

	public static ArrayList<SharhEghdam> Get(int ParentId, int NameListId, 
			Boolean GetDummy, String DummyText) {
		try {
			Log.i("SharhEghdam", "Get(" + ParentId + ", " + NameListId + ")");

			ArrayList<SharhEghdam> rst = new ArrayList<SharhEghdam>();
			if (GetDummy)
			{
				SharhEghdam Dummyitem = new SharhEghdam(0, -1, -1, DummyText);
				rst.add(Dummyitem);
			}
			for (int i = 0; i < Variables.SharhEghdamList.size(); i++) {
				SharhEghdam item = Variables.SharhEghdamList.get(i);
				if ((NameListId == -1 || item.nameListId == NameListId)
						&& (item.sharhTamirId_Parent == ParentId))
					rst.add(item);
			}

			Log.i("SharhEghdam", "Get(" + ParentId + ", " + NameListId
					+ "), rst.Size(): " + rst.size());

			return (rst);
		} catch (Exception e) {
			Log.i("SharhEghdam", "Error in Get(" + ParentId + ", " + NameListId
					+ "), rst.Size(): " + e.getMessage());
			return (null);
		}
	}
}
