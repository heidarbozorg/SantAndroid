package com.farzaneganco.sant.sant.logic.DP;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * اعلام حضور در محل
 * @author SOSA
 *
 */
public class DarkhastGPS {	
	public int DarkhastKarId;
	public int ErjaDarkhastId;
	public int PersonelId;
	public double Latitude;
	public double Longitude;
	public String Tarikh;
	public String Saat;	
	
	public DarkhastGPS(int did, int eid, int pid, double la, double lo, String t, String s)
	{		
		this.DarkhastKarId = did;
		this.ErjaDarkhastId = eid;
		this.PersonelId = pid;
		this.Latitude = la;
		this.Longitude = lo;
		this.Tarikh = t;
		this.Saat = s;		
	}
	
	
	public static ArrayList<DarkhastGPS> GetAll(Context context)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();
	    	
	    	
	    	Cursor cu = db.mydb.rawQuery("Select * from DarkhastGPS", null);    	
    		cu.moveToPosition(0);
    		
    		int n = cu.getCount();
    		if (n <= 0)
    			return (null);
    		
    		ArrayList<DarkhastGPS> rst = new ArrayList<DarkhastGPS>();
    		for (int i = 0; i < n; i++)
    		{
    			int DarkhastKarId = cu.getInt(0);
    			int ErjaDarkhastId = cu.getInt(1);    			
    			int PersonelId = cu.getInt(2);
    			double Latitude = cu.getDouble(3);
    			double Longitude = cu.getDouble(4);
				String Tarikh = cu.getString(5);
				String Saat = cu.getString(6);
				
				DarkhastGPS item = new DarkhastGPS(
						DarkhastKarId, 
						ErjaDarkhastId,
						PersonelId,
						Latitude,
						Longitude,
						Tarikh,
						Saat
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
			Log.i("Database.DarkhastGPS", "Error in GetAll: " + e.getMessage());
			return (null);
		}
	}	
	
	
	public static int Insert(Context context, 
			int DarkhastKarId, 
			int ErjaDarkhastId, 
			int PersonelId,
			double Latitude, 
			double Longitude)
	{
		try
		{
			String Tarikh = com.farzaneganco.sant.sant.logic.Tarikh.GetTarikh();
			String Saat = com.farzaneganco.sant.sant.logic.Tarikh.GetSaat();
			
			Database db = new Database(context);
	    	db.open();    		

			//به ازای هر آیتم در آرایه یک رکورد در دیتابیس ثبت میکنیم
			ContentValues values = new ContentValues();			
			
			values.put("DarkhastKarId", DarkhastKarId);
			values.put("ErjaDarkhastId", ErjaDarkhastId);
			
			values.put("PersonelId", PersonelId);
			values.put("Latitude", Latitude);
			values.put("Longitude", Longitude);
			
			values.put("Tarikh", Tarikh);
			values.put("Saat", Saat);			

			db.mydb.insert("DarkhastGPS", null, values);

    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.DarkhastGPS", "Error in Insert: " + e.getMessage());
			return (-1);
		}
	}
	
	public static int Delete(Context context, int ErjaDarkhastId)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();    		
	    	
	    	db.mydb.execSQL("Delete from DarkhastGPS where ErjaDarkhastId = " + ErjaDarkhastId);

    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.Inbox", "Error in Delete: " + e.getMessage());
			return (-1);
		}
	}

}

