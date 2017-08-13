package com.farzaneganco.sant.sant.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import com.farzaneganco.sant.sant.wheel.ArrayWheelAdapter;
import com.farzaneganco.sant.sant.wheel.OnWheelChangedListener;
import com.farzaneganco.sant.sant.wheel.WheelView;


public class DatePickerDailog extends Dialog {

	private Context Mcontex;

	private int NoOfYear = 64;
	private JalaliCalendar Jalali;
	private String months[] = new String[] { "فروردین", "اردیبهشت", "خرداد",
			"تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن",
			"اسفند" };

	private int curMonth;
	private String[] year_array;
	private String[] day_array;

	public DatePickerDailog(final Context context, Calendar calendar,
			final DatePickerListner dtp) {

		super(context);
		Mcontex = context;
		LinearLayout lytmain = new LinearLayout(Mcontex);
		lytmain.setOrientation(LinearLayout.VERTICAL);
		LinearLayout lytdate = new LinearLayout(Mcontex);
		LinearLayout lytbutton = new LinearLayout(Mcontex);

		Button btnset = new Button(Mcontex);
		Button btncancel = new Button(Mcontex);

		btnset.setText("انتخاب");
		btncancel.setText("انصراف");

		final WheelView month = new WheelView(Mcontex);
		final WheelView year = new WheelView(Mcontex);
		final WheelView day = new WheelView(Mcontex);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		lytdate.addView(year, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

		lytdate.addView(month, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f));

		lytdate.addView(day, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1.2f));

		lytbutton.addView(btnset, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

		lytbutton.addView(btncancel, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

		lytbutton.setPadding(5, 5, 5, 5);
		lytmain.addView(lytdate);
		lytmain.addView(lytbutton);

		setContentView(lytmain);

		getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		OnWheelChangedListener listener = new OnWheelChangedListener() {

			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};

		Jalali = new JalaliCalendar();

		// month
		curMonth = Jalali.get(Calendar.MONTH);

		month.setViewAdapter(new ArrayWheelAdapter<String>(context, months));

		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		// year
		int curYear = Jalali.get(Calendar.YEAR);
		int Year = Jalali.get(Calendar.YEAR);

		ArrayList<String> years = new ArrayList<String>();

		int Yearstart = Year - NoOfYear;
		int Yearend = Year + 1;

		while (Yearstart < Yearend) {
			//years.add(String.valueOf(number2farsi(Yearstart)));
			years.add(String.valueOf(Yearstart));
			Yearstart++;
		}

		year_array = new String[years.size()];
		years.toArray(year_array);

		year.setViewAdapter(new ArrayWheelAdapter<String>(context, year_array));

		year.setCurrentItem(curYear - (Year - NoOfYear));
		year.addChangingListener(listener);

		// day
		updateDays(year, month, day);
		day.setCurrentItem(Jalali.get(Calendar.DAY_OF_MONTH) - 1);

		btnset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dtp.OnDoneButton(
						DatePickerDailog.this,
						year_array[year.getCurrentItem()] + "/" +
						//day_array[day.getCurrentItem()] + "/"
								//+ months[month.getCurrentItem()] + "/"
						(month.getCurrentItem() < 9 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)) + "/" +
						(day.getCurrentItem() < 9 ? "0" + (day.getCurrentItem() + 1) : (day.getCurrentItem() + 1))
				);
			}
		});
		btncancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dtp.OnCancelButton(DatePickerDailog.this);
			}
		});
	}

	public void updateDays(WheelView year, WheelView month, WheelView day) {
		Jalali.set(Calendar.YEAR,
				Jalali.get(Calendar.YEAR) + (year.getCurrentItem() - NoOfYear));
		Jalali.set(Calendar.MONTH, month.getCurrentItem());
		curMonth = Jalali.get(Calendar.MONTH);
		int maxDays = JalaliCalendar.jalaliDaysInMonth[curMonth];

		boolean isLeapYear = ((Integer
				.valueOf(year_array[year.getCurrentItem()]) % 4 == 3)
				&& (Integer.valueOf(year_array[year.getCurrentItem()]) % 100 != 3) || (Integer
				.valueOf(year_array[year.getCurrentItem()]) % 400 == 3));

		ArrayList<String> days = new ArrayList<String>();

		int firstday = 1;
		int lastday = maxDays;

		if (isLeapYear) {
			if (months[month.getCurrentItem()] == "اسفند") {
				lastday++;
			}
		}

		while (firstday <= lastday) {
			days.add(String.valueOf(number2farsi(firstday)));
			firstday++;
		}

		day_array = new String[days.size()];
		days.toArray(day_array);

		day.setViewAdapter(new ArrayWheelAdapter<String>(Mcontex, day_array));

		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	public interface DatePickerListner {
		public void OnDoneButton(Dialog datedialog, String c);

		public void OnCancelButton(Dialog datedialog);
	}

	public String number2farsi(int i) {
		String b = Integer.toString(i);

		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("0","۰");	  
	    replaceMap.put("1","۱");
	    replaceMap.put("2","۲");
	    replaceMap.put("3","۳");
	    replaceMap.put("4","۴"); 
	    replaceMap.put("5","۵");
	    replaceMap.put("6","۶");
	    replaceMap.put("7","۷");
	    replaceMap.put("8","۸");
	    replaceMap.put("9","۹");

		for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
			b = b.replaceAll(entry.getKey(), entry.getValue());
		}

		return b;
	}

}
