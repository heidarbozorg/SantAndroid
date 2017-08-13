package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;
import java.util.List;

public class Variables {
	//public static String WebServiceAddress = "http://santws.farzaneganco.com/api/";
	public static String WebServiceAddress = "http://sanetws.ansarco.ir/api/";
	
	public static User user;
	
	/**
	 * حداکثر مدت زمانی که شی والی برای پاسخ منتظر میماند
	 * واحد اندازه گیری میلی ثانیه است
	 */
	public static int MaximumWatingTimeVolley = 10000;
	
	/**
	 * لیست کلیه رکوردهای مربوط به شرح اقدام
	 */
	public static ArrayList<SharhEghdam> SharhEghdamList;
}
