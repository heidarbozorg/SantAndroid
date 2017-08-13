package com.farzaneganco.sant.sant.logic.DP;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class PersonTrace {
	public int PersonelId;
	public double Latitude;
	public double Longitude;
	public String Tarikh;
	public String Saat;	
	
	public PersonTrace(int pid, String t, String s, double la, double lo)
	{
		this.PersonelId = pid;
		this.Latitude = la;
		this.Longitude = lo;
		this.Tarikh = t;
		this.Saat = s;		
	}	
	
	public static ArrayList<PersonTrace> GetAll(Context context)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();	    	
	    	
	    	Cursor cu = db.mydb.rawQuery("Select * from PersonTrace", null);    	
    		cu.moveToPosition(0);
    		
    		int n = cu.getCount();
    		if (n <= 0)
    			return (null);
    		
    		ArrayList<PersonTrace> rst = new ArrayList<PersonTrace>();
    		for (int i = 0; i < n; i++)
    		{    			
    			int PersonelId = cu.getInt(0);
				String Tarikh = cu.getString(1);
				String Saat = cu.getString(2);
    			double Latitude = cu.getDouble(3);
    			double Longitude = cu.getDouble(4);
				
				PersonTrace item = new PersonTrace(						
						PersonelId,
						Tarikh,
						Saat,
						Latitude,
						Longitude
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
			Log.i("Database.PersonTrace", "Error in GetAll: " + e.getMessage());
			Toast.makeText(context, "Error in PersonTrace.GetAll: " + e.getMessage(), Toast.LENGTH_LONG).show();
			return (null);
		}
	}	
	
	
	public static int Insert(Context context, 
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
			
			values.put("PersonelId", PersonelId);
			values.put("Tarikh", Tarikh);
			values.put("Saat", Saat);			
			values.put("Latitude", Latitude);
			values.put("Longitude", Longitude);			

			db.mydb.insert("PersonTrace", null, values);

    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.PersonTrace", "Error in Insert: " + e.getMessage());
			Toast.makeText(context, "Error in Insert into PersonTrace: " + e.getMessage(), Toast.LENGTH_LONG).show();
			return (-1);
		}
	}
	
	public static int Delete(Context context, 
			int PersonelId, String Tarikh, String Saat)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();    		
	    	
	    	db.mydb.execSQL("Delete from PersonTrace where PersonelId = " + PersonelId + " and Tarikh = '" + Tarikh + "' and Saat = '" + Saat + "'");

    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.PersonTrace", "Error in Delete: " + e.getMessage());
			Toast.makeText(context, "Error in PersonTrace.Delete: " + e.getMessage(), Toast.LENGTH_LONG).show();
			return (-1);
		}
	}

}

