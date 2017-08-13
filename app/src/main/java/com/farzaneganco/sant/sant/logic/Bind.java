package com.farzaneganco.sant.sant.logic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.far.sant.Inbox;
import com.farzaneganco.sant.sant.R;

public class Bind {
	public static void SetAsRead()
	{
		Log.i("Bind", "Start SetAsRead");
		
		//Inbox.SelectedItem.TarikhRoyat = "1";
		//Inbox.adapter.getItem(Inbox.SelectedItemIndex).TarikhRoyat = "1";
		
		//set as read to server
		//com.farzaneganco.sant.sant.webservice.WFlow.SetAsRead(Inbox.SelectedItem.ErjaDarkhastId);
		//Inbox.adapter.notifyDataSetChanged();		//for referesh inbox
	}
	
	public static void ShowDarkhastInfo(final Activity v, String Title)
	{
		/*
		if (Inbox.SelectedItem.NameSazman_Darkhast == null)
			return;
		
		TextView txtNameTaghizAction = (TextView) v.findViewById(R.id.txtNameTaghizAction);
		TextView txtMahaleTaghiz = (TextView) v.findViewById(R.id.txtMahaleTaghiz);
		TextView txtDarkhastkonandeAction = (TextView) v.findViewById(R.id.txtDarkhastkonandeAction);
		TextView txtDateDarkhastAction = (TextView) v.findViewById(R.id.txtDateDarkhastAction);
		TextView txtSharheDarkhastAction = (TextView) v.findViewById(R.id.txtSharheDarkhastAction);
		TextView txtNoDarkhastAction = (TextView) v.findViewById(R.id.txtNoDarkhastAction);
		TextView txtErjaDate = (TextView) v.findViewById(R.id.txtErjaDate);
		TextView txtErjaDahande = (TextView) v.findViewById(R.id.txtErjaDahande);
		TextView txtMohlateEghdam = (TextView) v.findViewById(R.id.txtMohlateEghdam);
		TextView txtHameshinfo = (TextView) v.findViewById(R.id.txtHameshinfo);

		txtNameTaghizAction.setText(Inbox.SelectedItem.TajhizName);
		txtMahaleTaghiz.setText(Inbox.SelectedItem.MahaleEsteghrar);
		
		txtDarkhastkonandeAction
				.setText(Inbox.SelectedItem.NameSazman_Darkhast);
		txtDateDarkhastAction.setText(Inbox.SelectedItem.TarikhDarkhast
				.substring(0, 10));
		txtSharheDarkhastAction.setText(Inbox.SelectedItem.SharhDarkhast);
		txtNoDarkhastAction.setText(Inbox.SelectedItem.ShomareDarkhast);
		txtErjaDate.setText(Inbox.SelectedItem.TarikhErja);
		txtErjaDahande.setText(Inbox.SelectedItem.FullName_ErjaDahande);
		
		v.setTitle(Title + " - " + Inbox.SelectedItem.MozooDarkhastStr);
		TextView titleActionBar = (TextView) v.findViewById(R.id.titleActionBar);
		titleActionBar.setText(Title + " - " + Inbox.SelectedItem.MozooDarkhastStr);
		
		LinearLayout llTajhizName = (LinearLayout) v.findViewById(R.id.llTajhizName);
		LinearLayout llMahaleEsteghrar = (LinearLayout) v.findViewById(R.id.llMahaleEsteghrar);
		
		if (Inbox.SelectedItem.TajhizId <= 0)
		{
			//در صورتی که درخواست به تحهیز مرتبط نبود
			llTajhizName.setVisibility(View.GONE);
			llMahaleEsteghrar.setVisibility(View.GONE);
		}
		else
		{
			llTajhizName.setVisibility(View.VISIBLE);
			llMahaleEsteghrar.setVisibility(View.VISIBLE);
		}
		
		txtMohlateEghdam.setText(Inbox.SelectedItem.MohlatEghdam);
		txtHameshinfo.setText(Inbox.SelectedItem.SharhErja);
		
		if (Inbox.SelectedItem.TarikhRoyat == null 
				|| Inbox.SelectedItem.TarikhRoyat == ""
				|| (Inbox.SelectedItem.TarikhRoyat != null && Inbox.SelectedItem.TarikhRoyat != "1" && Inbox.SelectedItem.TarikhRoyat.length() < 10)
				)
			SetAsRead();
			*/
	}
	
	public static void SaveUserInfo(Context ctx)
	{
		if (com.farzaneganco.sant.sant.logic.Variables.user == null || com.farzaneganco.sant.sant.logic.Variables.user.GetPersonelId() < 1)
			return;
		
		SharedPreferences sharedPref = ctx.getSharedPreferences("Sant", ctx.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = sharedPref.edit();
		prefEditor.putInt("PersonelId", com.farzaneganco.sant.sant.logic.Variables.user.GetPersonelId());
		prefEditor.putInt("MySazmanId", com.farzaneganco.sant.sant.logic.Variables.user.MySazmanId);
		prefEditor.putString("Username", com.farzaneganco.sant.sant.logic.Variables.user.GetUsername());
		prefEditor.putString("Password", com.farzaneganco.sant.sant.logic.Variables.user.GetPassword());
		prefEditor.putString("FName", com.farzaneganco.sant.sant.logic.Variables.user.FName);
		prefEditor.putString("LName", com.farzaneganco.sant.sant.logic.Variables.user.LName);
		prefEditor.putString("FullName", com.farzaneganco.sant.sant.logic.Variables.user.FullName);
		prefEditor.commit();
	}
	
	/**
	 * ذخیره آی دی آخرین کار دریافت شده در حافظه
	 * @param ctx
	 * @param erja
	 */
	public static void SaveLastErja(Context ctx, WFlow erja)
	{
		SharedPreferences sharedPref = ctx.getSharedPreferences("Sant", ctx.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = sharedPref.edit();
		prefEditor.putInt("LastErjaDarkhastId", erja.ErjaDarkhastId);
  		prefEditor.commit();
	}
	
	/**
	 * خواندن آی دی آخرین کار دریافت شده از حافظه
	 * @param ctx
	 * @return
	 */
	public static int GetLastErjaId(Context ctx)
	{
		return (GetInt(ctx, "LastErjaDarkhastId"));
	}
	
	
	public static Boolean GetUserInfo(Context ctx)
	{
		SharedPreferences prefs = ctx.getSharedPreferences("Sant", ctx.MODE_PRIVATE); 
		int PersonelId = prefs.getInt("PersonelId", 0);
		if (PersonelId < 1)
			return (false);
		
		String Username = prefs.getString("Username", "");
		String Password = prefs.getString("Password", "");
		String FName = prefs.getString("FName", "");
		String LName = prefs.getString("LName", "");
		String FullName = prefs.getString("FullName", "");
		int MySazmanId = prefs.getInt("MySazmanId", 0);
	
		if (com.farzaneganco.sant.sant.logic.Variables.user == null)
			com.farzaneganco.sant.sant.logic.Variables.user = new User(Username, Password);
		else
		{
			com.farzaneganco.sant.sant.logic.Variables.user.SetUsername(Username);
			com.farzaneganco.sant.sant.logic.Variables.user.SetPassword(Password);
		}
		com.farzaneganco.sant.sant.logic.Variables.user.SetPersonelId(PersonelId);
		com.farzaneganco.sant.sant.logic.Variables.user.MySazmanId = MySazmanId;
		com.farzaneganco.sant.sant.logic.Variables.user.FName = FName;
		com.farzaneganco.sant.sant.logic.Variables.user.LName = LName;
		com.farzaneganco.sant.sant.logic.Variables.user.FullName = FullName;
		
		return (true);
	}
	
	public static int GetInt(Context ctx, String Name)
	{
		SharedPreferences prefs = ctx.getSharedPreferences("Sant", ctx.MODE_PRIVATE); 
		int val = prefs.getInt(Name, 0);
		return (val);
	}
	
	public static int GetPersonelId(Context ctx)
	{
		return (GetInt(ctx, "PersonelId"));
	}
	public static int GetMySazmanId(Context ctx)
	{
		return (GetInt(ctx, "MySazmanId"));
	}
	
	public static void RemoveUserInfo(Context ctx)
	{
		SharedPreferences sharedPref = ctx.getSharedPreferences("Sant", ctx.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = sharedPref.edit();
		prefEditor.putInt("PersonelId", 0);
		prefEditor.putInt("MySazmanId", 0);		
		prefEditor.putString("Username", "");
		prefEditor.putString("Password", "");
		prefEditor.commit();
	}
}
