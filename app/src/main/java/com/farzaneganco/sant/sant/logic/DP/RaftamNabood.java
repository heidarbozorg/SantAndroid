package com.farzaneganco.sant.sant.logic.DP;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class RaftamNabood {
	public int ErjaDarkhastId;
	public int DarkhastKarId;
	public int PersonelId;
	public String Tarikh;
	public String Saat;	
	public String Sharh;
	
	public RaftamNabood(int did, int eid, int pid, 
			String t, String s, String sharh)
	{		
		this.DarkhastKarId = did;
		this.ErjaDarkhastId = eid;
		this.PersonelId = pid;
		this.Tarikh = t;
		this.Saat = s;
		this.Sharh = sharh;
	}
	
	
	public static ArrayList<RaftamNabood> GetAll(Context context)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();
	    	
	    	
	    	Cursor cu = db.mydb.rawQuery("Select * from RaftamNabood", null);    	
    		cu.moveToPosition(0);
    		
    		int n = cu.getCount();
    		if (n <= 0)
    			return (null);
    		
    		ArrayList<RaftamNabood> rst = new ArrayList<RaftamNabood>();
    		for (int i = 0; i < n; i++)
    		{
    			int DarkhastKarId = cu.getInt(0);
    			int ErjaDarkhastId = cu.getInt(1);    			
    			int PersonelId = cu.getInt(2);
				String Tarikh = cu.getString(3);
				String Saat = cu.getString(4);
				String Sharh = cu.getString(5);
				
				RaftamNabood item = new RaftamNabood(
						DarkhastKarId, 
						ErjaDarkhastId,
						PersonelId,
						Tarikh,
						Saat,
						Sharh
						);
    					
    			rst.add(item);
    			if (!cu.moveToNext())
    				break;
    		}

    		cu.close();
    		db.close();
    		
    		return (rst);
		}
		catch (Exception e)
		{
			Log.i("Database.RaftamNabood", "Error in GetAll: " + e.getMessage());
			Toast.makeText(context, "Error in RaftamNabood.GetAll: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			
			return (null);
		}
	}	
	
	
	public static int Insert(Context context, int DarkhastKarId, int ErjaDarkhastId, int PersonelId,
			String Tarikh, String Saat, String Sharh)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();    		

			//به ازای هر آیتم در آرایه یک رکورد در دیتابیس ثبت میکنیم
			ContentValues values = new ContentValues();			
			
			values.put("DarkhastKarId", DarkhastKarId);
			values.put("ErjaDarkhastId", ErjaDarkhastId);
			values.put("PersonelId", PersonelId);
			values.put("Tarikh", Tarikh);
			values.put("Saat", Saat);		
			values.put("Sharh", Sharh);

			db.mydb.insert("RaftamNabood", null, values);

    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.RaftamNabood", "Error in Insert: " + e.getMessage());
			Toast.makeText(context, "Error in Insert into RaftamNabood: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			
			return (-1);
		}
	}
	
	public static int Delete(Context context, int ErjaDarkhastId)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();
	    	
	    	db.mydb.execSQL("Delete from RaftamNabood where ErjaDarkhastId = " + ErjaDarkhastId);
			
    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.RaftamNabood", "Error in Delete: " + e.getMessage());
			Toast.makeText(context, "Error in Delete from RaftamNabood: " + e.getMessage(),
					Toast.LENGTH_LONG).show();

			return (-1);
		}
	}
}

