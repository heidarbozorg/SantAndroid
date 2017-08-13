package com.farzaneganco.sant.sant.logic;

import java.util.Calendar;

import com.farzaneganco.sant.sant.calendar.JalaliCalendar;

public class Tarikh {
	public static String GetSaat()
	{
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		String rst = ChangeData.pad(hour)+":"+ ChangeData.pad(minute);
		return (rst);
	}
	
	public static String GetTarikh()
	{
		JalaliCalendar c = new JalaliCalendar();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;
		int curDay = c.get(Calendar.DAY_OF_MONTH);

		String rst = ChangeData.pad(curYear, 4)+"/"+ ChangeData.pad(curMonth, 2)+"/"+ ChangeData.pad(curDay, 2);
		return (rst);
	}
	
}
