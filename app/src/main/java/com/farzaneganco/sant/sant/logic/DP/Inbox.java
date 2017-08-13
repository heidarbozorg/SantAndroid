package com.farzaneganco.sant.sant.logic.DP;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.farzaneganco.sant.sant.logic.*;

public class Inbox {
	
	public static ArrayList<WFlow> GetAll(Context context)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();
	    	
	    	
	    	Cursor cu = db.mydb.rawQuery("Select * from Inbox", null);    	
    		cu.moveToPosition(0);
    		
    		int n = cu.getCount();
    		if (n <= 0)
    			return (null);
    		
    		ArrayList<WFlow> rst = new ArrayList<WFlow>();
    		for (int i = 0; i < n; i++)
    		{
    			int ErjaDarkhastId = cu.getInt(0);
    			int DarkhastKarId = cu.getInt(1);
    			int TajhizId = cu.getInt(2);
    			int MySazmanId_Darkhast = cu.getInt(3);
    			int PersonelId_Darkhast = cu.getInt(4);
    			int PersonelId_Frestande = cu.getInt(5);
				String NameSazman_Darkhast = cu.getString(6);
				String FullName_Darkhast = cu.getString(7);
				String FullName_ErjaDahande = cu.getString(8);
				String TarikhDarkhast = cu.getString(9);
				String TarikhErja = cu.getString(10);
				String MohlatEghdam = cu.getString(11);
				String TarikhRoyat = cu.getString(12);
				String SharhDarkhast = cu.getString(13);
				String SharhErja = cu.getString(14);
				String ShomareDarkhast = cu.getString(15);
				String MozooDarkhastStr = cu.getString(16); 
				String vaziatDarkhastStr = cu.getString(17);
				int totalMohlatDaghighe = cu.getInt(18);
				int mohlatEghdamMandeTotlaDaghighe = cu.getInt(19);
				String TajhizName = cu.getString(20);
				String MahaleEsteghrar = cu.getString(21);
				
    			WFlow item = new WFlow(null, DarkhastKarId, ErjaDarkhastId, TajhizId,
    					MySazmanId_Darkhast, PersonelId_Darkhast, PersonelId_Frestande,
    					NameSazman_Darkhast, FullName_Darkhast, FullName_ErjaDahande,
    					TarikhDarkhast, TarikhErja, MohlatEghdam, TarikhRoyat,
    					SharhDarkhast, SharhErja, ShomareDarkhast, MozooDarkhastStr, 
    					vaziatDarkhastStr,
    					totalMohlatDaghighe, mohlatEghdamMandeTotlaDaghighe,
    					TajhizName, MahaleEsteghrar);
    					
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
			Log.i("Database.Inbox", "Error in GetAll: " + e.getMessage());
			return (null);
		}
	}
	
	public static int Insert(Context context, List<WFlow> items)
	{
		try
		{
			Database db = new Database(context);
	    	db.open();    		

    		//تمامی اطلاعات موجود در دیتابیس را حذف میکنیم
    		db.mydb.execSQL("Delete from Inbox");
    		
    		int n = items.size();
    		if (n <= 0)
    		{
    			db.close();
    			return (0);
    		}
    		int rst = 0;
    		
    		
    		
    		for (int i = 0; i < n; i++)
    		{
    			//به ازای هر آیتم در آرایه یک رکورد در دیتابیس ثبت میکنیم
    			ContentValues values = new ContentValues();
    			WFlow item = items.get(i);
    			
    			
    			values.put("ErjaDarkhastId", item.ErjaDarkhastId);
    			values.put("DarkhastKarId", item.DarkhastKarId);
    			values.put("TajhizId", item.TajhizId);
    			
    			values.put("MySazmanId_Darkhast", item.MySazmanId_Darkhast);
    			values.put("PersonelId_Darkhast", item.PersonelId_Darkhast);
    			values.put("PersonelId_Frestande", item.PersonelId_Frestande);
    			values.put("NameSazman_Darkhast", item.NameSazman_Darkhast);
    			
    			values.put("FullName_Darkhast", item.FullName_Darkhast);
    			values.put("FullName_ErjaDahande", item.FullName_ErjaDahande);
    			values.put("TarikhDarkhast", item.TarikhDarkhast);
    			
    			values.put("TarikhErja", item.TarikhErja);
    			values.put("MohlatEghdam", item.MohlatEghdam);
    			values.put("TarikhRoyat", item.TarikhRoyat);
    			values.put("SharhDarkhast", item.SharhDarkhast);
    			
    			values.put("SharhErja", item.SharhErja);
    			values.put("ShomareDarkhast", item.ShomareDarkhast);
    			values.put("MozooDarkhastStr", item.MozooDarkhastStr);
    			values.put("vaziatDarkhastStr", item.vaziatDarkhastStr);
    			
    			values.put("totalMohlatDaghighe", item.totalMohlatDaghighe);
    			values.put("mohlatEghdamMandeTotlaDaghighe", item.mohlatEghdamMandeTotlaDaghighe);

    			db.mydb.insert("Inbox", null, values);
    			
    			rst++;
    		}

    		db.close();
    		
    		return (rst);
		}
		catch (Exception e)
		{
			Log.i("Database.Inbox", "Error in Insert: " + e.getMessage());
			return (-1);
		}
	}
	
	public static int Update(Context context, WFlow item)
	{
		try
		{
    		if (item == null)
    			return (0);

			Database db = new Database(context);
	    	db.open();    		
	    	
	    	ContentValues values = new ContentValues();
			values.put("TajhizName", item.TajhizName);
			values.put("MahaleEsteghrar", item.MahaleEsteghrar);

			db.mydb.update("Inbox", values,
					"ErjaDarkhastId" + "=?",
					new String[] { String.valueOf(item.ErjaDarkhastId) });
			
    		db.close();
    		
    		return (1);
		}
		catch (Exception e)
		{
			Log.i("Database.Inbox", "Error in Insert: " + e.getMessage());
			return (-1);
		}
	}
}
