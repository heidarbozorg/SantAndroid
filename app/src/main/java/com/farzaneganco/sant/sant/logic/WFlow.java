package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.VolleyError;
//import com.farzaneganco.sant.sant.Inbox;

import android.content.Context;
import android.util.Log;

public class WFlow {
	public int DarkhastKarId;
	public int ErjaDarkhastId;
	public int TajhizId;
	
	public int MySazmanId_Darkhast;
	public int PersonelId_Darkhast;
	public int PersonelId_Frestande;
	
	public String NameSazman_Darkhast;
	public String FullName_Darkhast;
	public String FullName_ErjaDahande;
	
	public String TarikhDarkhast;
	public String TarikhErja;
	public String MohlatEghdam;
	public String TarikhRoyat;
	
	public String SharhDarkhast;
	public String SharhErja;
	
	public String ShomareDarkhast;
	public String MozooDarkhastStr;
	
	public String vaziatDarkhastStr;
	
	public String TajhizName;
	public String MahaleEsteghrar;
	
	public int totalMohlatDaghighe;
	public int mohlatEghdamMandeTotlaDaghighe;

	private void SetTajhizInfo(final Context ctx){
		com.farzaneganco.sant.sant.webservice.Tajhiz.GetTajhiz(this, new com.farzaneganco.sant.sant.webservice.Common.TajhizResult() {
			
			@Override
			public void onSuccessVolleyFinished(JSONObject response, WFlow wflow) {
				// TODO Auto-generated method stub
				try
				{
					wflow.TajhizName = response.getString("onvan");
					wflow.MahaleEsteghrar = response.getString("makan");

					com.farzaneganco.sant.sant.logic.DP.Inbox.Update(ctx, wflow);
					//com.farzaneganco.sant.sant.Inbox.adapter.notifyDataSetChanged();
				}
				catch(Exception e){
					Log.i("User", "Error in logic.WFlow.onSuccessVolleyFinished: " + e.getMessage());
				}
			}
			
			@Override
			public void OnErrorVolleyFinished(VolleyError error, WFlow wflow) {
				// TODO Auto-generated method stub
				Log.i("User", "Error in logic.WFlow.OnErrorVolleyFinished: " + error.getMessage());
			}
		});
	}
	
	public WFlow(Context ctx, int DarkhastKarId, int ErjaDarkhastId, int TajhizId, 
			int MySazmanId_Darkhast, int PersonelId_Darkhast, int PersonelId_Frestande,
			String NameSazman_Darkhast, String FullName_Darkhast, String FullName_ErjaDahande,
			String TarikhDarkhast, String TarikhErja, String MohlatEghdam, String TarikhRoyat,
			String SharhDarkhast, String SharhErja, String ShomareDarkhast, String MozooDarkhastStr, 
			String vaziatDarkhastStr,
			int totalMohlatDaghighe, int mohlatEghdamMandeTotlaDaghighe,
			String TajhizName, String MahaleEsteghrar)
	{
		this.DarkhastKarId = DarkhastKarId;
		this.ErjaDarkhastId = ErjaDarkhastId;
		this.TajhizId = TajhizId;
		
		this.MySazmanId_Darkhast = MySazmanId_Darkhast;
		this.PersonelId_Darkhast = PersonelId_Darkhast;
		this.PersonelId_Frestande = PersonelId_Frestande;
		
		this.NameSazman_Darkhast = NameSazman_Darkhast;
		this.FullName_Darkhast = FullName_Darkhast;
		this.FullName_ErjaDahande = FullName_ErjaDahande;
		
		this.TarikhDarkhast = TarikhDarkhast;
		this.TarikhErja = TarikhErja;
		this.MohlatEghdam = MohlatEghdam;
		if (this.MohlatEghdam != null)
			this.MohlatEghdam = this.MohlatEghdam.trim();
		this.TarikhRoyat = TarikhRoyat;
		
		this.SharhDarkhast = SharhDarkhast;
		this.SharhErja = SharhErja;
		
		this.ShomareDarkhast = ShomareDarkhast;
		this.MozooDarkhastStr = MozooDarkhastStr;
		
		this.vaziatDarkhastStr = vaziatDarkhastStr;
		
		this.totalMohlatDaghighe = totalMohlatDaghighe;
		this.mohlatEghdamMandeTotlaDaghighe = mohlatEghdamMandeTotlaDaghighe;
		
		if (this.totalMohlatDaghighe < 0)
			this.totalMohlatDaghighe = 100;
		
		if (this.mohlatEghdamMandeTotlaDaghighe < 0 || this.mohlatEghdamMandeTotlaDaghighe > this.totalMohlatDaghighe)
			this.mohlatEghdamMandeTotlaDaghighe = 0;
		
		this.TajhizName = TajhizName;
		this.MahaleEsteghrar = MahaleEsteghrar;
		
		if (ctx != null && TajhizId > 0)
			SetTajhizInfo(ctx);
	}
	
	public static ArrayList<WFlow> Convert(JSONArray response, Context ctx) 
	{
		if (response == null)
			return (null);
		
		try
		{
			ArrayList<WFlow> rst = new ArrayList<WFlow>();
			for (int i = 0; i < response.length(); i++)
			{
				JSONObject item = response.getJSONObject(i);
				int DarkhastKarId = item.getInt("darkhastKarId");
				int ErjaDarkhastId = item.getInt("erjaDarkhastId");
				int TajhizId = -1;
				try
				{
					TajhizId = item.getInt("tajhizId");
				}catch(Exception err){
					TajhizId = -1;
				}
				int MySazmanId_Darkhast = item.getInt("mySazmanId_Darkhast");
				int PersonelId_Darkhast = item.getInt("personelId_Darkhast");
				int PersonelId_Frestande = item.getInt("personelId_Frestande");
				
				String NameSazman_Darkhast = item.getString("nameSazman_Darkhast");
				String FullName_Darkhast = item.getString("fullName_Darkhast");
				String FullName_ErjaDahande = item.getString("fullName_ErjaDahande");
				String TarikhDarkhast = item.getString("tarikhDarkhast");
				String TarikhErja = item.getString("tarikhErja");
				String MohlatEghdam = item.getString("mohlatEghdam");
				String TarikhRoyat = item.getString("tarikhRoyat");
				String SharhDarkhast = item.getString("sharhDarkhast");
				String SharhErja = item.getString("hamesh");
				String ShomareDarkhast = item.getString("shomareDarkhast");
				String MozooDarkhastStr = item.getString("mozooDarkhastStr");
				String vaziatDarkhastStr = item.getString("vaziatDarkhastStr");
				int totalMohlatDaghighe = item.getInt("totalMohlatDaghighe");
				int mohlatEghdamMandeTotlaDaghighe = item.getInt("mohlatEghdamMandeTotlaDaghighe");
				
				WFlow ii = new WFlow(ctx, DarkhastKarId, ErjaDarkhastId, TajhizId, MySazmanId_Darkhast, 
						PersonelId_Darkhast, PersonelId_Frestande, NameSazman_Darkhast, 
						FullName_Darkhast, FullName_ErjaDahande, TarikhDarkhast, TarikhErja, 
						MohlatEghdam, TarikhRoyat, SharhDarkhast, SharhErja, ShomareDarkhast, 
						MozooDarkhastStr, vaziatDarkhastStr, totalMohlatDaghighe, 
						mohlatEghdamMandeTotlaDaghighe, null, null);
				
				/*
				WFlow ii = new WFlow(DarkhastKarId, ErjaDarkhastId, TajhizId, MySazmanId_Darkhast, PersonelId_Darkhast, 
						PersonelId_Frestande, NameSazman_Darkhast, FullName_Darkhast, FullName_ErjaDahande, 
						TarikhDarkhast, TarikhErja, MohlatEghdam, TarikhRoyat, SharhDarkhast, SharhErja,
						ShomareDarkhast, MozooDarkhastStr, vaziatDarkhastStr,
						totalMohlatDaghighe, mohlatEghdamMandeTotlaDaghighe);
				*/
				
				rst.add(ii);
			}
			return (rst);
		}
		catch(Exception e)
		{
			Log.i("WFlow", "Error on convert to WFlow object: " + e.getMessage());
			return (null);
		}
	}
}
