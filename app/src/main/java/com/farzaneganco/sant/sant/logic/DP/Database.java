package com.farzaneganco.sant.sant.logic.DP;

import java.io.File;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
	private int db2MinSize = 10;
	
	/**
	 * اندروید فایل های دیتابیس را فشرده میکند که در حجم های بالا باعث بروز خطا
	 * میشود لذا مجبور شدیم پسوند فایل دیتابیس را به عکس تغییر دهیم
	 */
	public static String DummydbName = "Sant31.db";
	public static String dbName3 = "Sant31.db";

	private final Context mycontext;
	public String DatabasePath;
	public SQLiteDatabase mydb;

	public Database(Context context) {
		super(context, dbName3, null, 1);
		mycontext = context;
		
		StringBuffer dbPath = new StringBuffer();
		dbPath.append("/data/data/");
		dbPath.append(mycontext.getPackageName());
		dbPath.append("/databases/");
		
		DatabasePath = dbPath.toString();
		
		CheckAndCreate();
	}

	public void onCreate(SQLiteDatabase db) {
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void open() {
		mydb = SQLiteDatabase.openDatabase(DatabasePath + dbName3, 
				null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	public void close() {
		mydb.close();
	}

	/**
	 * این تابع در اولین درخواستی که به سمت دیتابیس می آید فراخوانی میشود اولین
	 * درخواست یا از طریق صفحه نمایش مواد اولیه در جستجو هوشمند است و یا از طریق
	 * صفحات علاقه مندی و همه غذاها که هر دو به یک کلاس ارجاع داده میشوند و آن
	 * کلاس ShowSearchResult است
	 */
	public void CheckAndCreate() {
		try {
			File databaseFile = new File(DatabasePath, dbName3);			

			if (databaseFile.exists() && databaseFile.length() > db2MinSize)
				return;

			CreateV2();
			
		} catch (Exception e) {
			Log.i("Database.database", "Error in CheckAndCreate: " + e.getMessage());
		}
	}

	private void CreateV2()
	{
		try
		{
			Log.i("Database", "Begin CreateV2");
			File databaseFile2 = new File(DatabasePath, dbName3);
			if (!databaseFile2.exists() && databaseFile2.length() > db2MinSize)
				return;
			
			boolean rst = com.farzaneganco.sant.sant.logic.FileIO.Copy(mycontext, DummydbName, DatabasePath, dbName3);
			Log.i("Database", "end CreateV2, rst: " + rst);
		}
		catch(Exception e)
		{
			Log.i("Database", "Error in CreateV2: " + e.getMessage());
		}
	}
	
}
