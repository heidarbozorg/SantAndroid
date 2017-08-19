package com.farzaneganco.sant.sant;

import java.util.ArrayList;

import com.farzaneganco.sant.sant.logic.ComboReciverAdapter;
import com.farzaneganco.sant.sant.logic.User;
import com.farzaneganco.sant.sant.logic.Variables;

import org.json.JSONArray;
import com.farzaneganco.sant.sant.webservice.Common.ArrayResult;
import com.farzaneganco.sant.sant.calendar.DatePickerDailog;
import com.farzaneganco.sant.sant.calendar.JalaliCalendar;

import com.android.volley.VolleyError;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Forward extends Activity {

	ImageView imgBack;
	// imgForwardAnjamShod;
	TextView TopTitles, tvResivers, tvDateMohlat, tvOlaviat, 
	tvSaateEghdam_Forward, tvAzSaatEghdam, tvTaSaatEghdam, 
	tvSpiner1, tvSpiner2, tvSpiner3,tvSpiner4,
	tvDateEghdam_Eghdam, tvDateMohlatEghdam_Forward;
	Button BtnForwardSabt;
	ProgressDialog pDialog;
	EditText edtSharheEghdamForward;
	Spinner spinnerOlaviat, Spinner1, Spinner2, Spinner3, Spinner4;
	ArrayList<com.farzaneganco.sant.sant.logic.Sabet> Olaviat;
	Boolean AnjamShod;
	JalaliCalendar dateandtime;
	private int hour, minute, timePickerKind;

	public static ComboReciverAdapter cmbAdapter;

	private void Save() {
		int SharhEghdam1 = -1, SharhEghdam2 = -1, SharhEghdam3 = -1, SharhEghdam4 = -1;

		if (Spinner1.getSelectedItem() != null
				&& ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner1.getSelectedItem()).sharhTamirId > 0) {

			SharhEghdam1 = ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner1.getSelectedItem()).sharhTamirId;
			// در صورتی که شرح اقدام اول انتخاب شده بود، تمامی اطلاعات مربوط به
			// شرح اقدام باید وارد شود

			if (Spinner2.getSelectedItem() == null
					|| ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner2.getSelectedItem()).sharhTamirId <= 0) {
				Toast.makeText(getApplicationContext(),
						R.string.SelectSharheEghdam, Toast.LENGTH_LONG).show();
				return;
			}
			SharhEghdam2 = ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner2.getSelectedItem()).sharhTamirId;

			if (Spinner3.getSelectedItem() == null
					|| ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner3.getSelectedItem()).sharhTamirId <= 0) {
				Toast.makeText(getApplicationContext(),
						R.string.SelectSharheEghdam, Toast.LENGTH_LONG).show();
				return;
			}
			SharhEghdam3 = ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner3.getSelectedItem()).sharhTamirId;

			if (Spinner4.getSelectedItem() == null
					|| ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner4.getSelectedItem()).sharhTamirId <= 0) {
				Toast.makeText(getApplicationContext(),
						R.string.SelectSharheEghdam, Toast.LENGTH_LONG).show();
				return;
			}
			SharhEghdam4 = ((com.farzaneganco.sant.sant.logic.SharhEghdam) Spinner4.getSelectedItem()).sharhTamirId;
		}

		if (cmbAdapter == null || cmbAdapter.getCount() == 0) {
			Toast.makeText(getApplicationContext(), R.string.SelectReciver,
					Toast.LENGTH_LONG).show();
			return;
		}

		ArrayList<User> SelectedUser = new ArrayList<User>();
		for (int i = 0; i < cmbAdapter.getCount(); i++) {
			if (cmbAdapter.getItem(i).Checked)
				SelectedUser.add(cmbAdapter.getItem(i));
		}

		EditText edtHamesh = (EditText) findViewById(R.id.edtHamesh);
		if (SelectedUser.size() > 0
				&& (edtHamesh.getText() == null || edtHamesh.getText()
						.toString().matches(""))) {
			Toast.makeText(getApplicationContext(), R.string.EnterHamesh,
					Toast.LENGTH_LONG).show();
			return;
		}

		if (SelectedUser.size() == 0)
			// در صورتی که اطلاعات ارجاع وارد شده بود، کار را پس از ارجاع موفقیت
			// آمیز از کارتابل خارج میکنیم
			AnjamShod = false;

		com.farzaneganco.sant.sant.logic.Sabet olaviat = Olaviat.get(spinnerOlaviat
				.getSelectedItemPosition());

		pDialog.setMessage("Loading...");
		pDialog.show();
		com.farzaneganco.sant.sant.webservice.WFlow.EghdamAndForward(com.farzaneganco.sant.sant.logic.Variables.user,
				Inbox.SelectedItem, SharhEghdam1, SharhEghdam2, SharhEghdam3,
				SharhEghdam4, edtSharheEghdamForward.getText().toString(),
				tvDateEghdam_Eghdam.getText().toString(), tvAzSaatEghdam
						.getText().toString(), tvTaSaatEghdam.getText()
						.toString(),

				SelectedUser, tvDateMohlatEghdam_Forward.getText().toString(),
				olaviat.SabetId, AnjamShod, edtHamesh.getText().toString(),
				new com.farzaneganco.sant.sant.webservice.Common.StringResult() {

					@Override
					public void onSuccessVolleyFinished(String response,
							User user) {
						Log.i("Response", response.toString());
						// در صورت موفقیت آمیز بودن عملیات ارجاع
						if (AnjamShod) {
							Inbox.adapter.remove(Inbox.SelectedItem);
							Inbox.adapter.notifyDataSetChanged();
							Inbox.SelectedItem = null;
						}

						pDialog.dismiss();
						finish();
					}

					@Override
					public void OnErrorVolleyFinished(VolleyError error,
							User user) {
						// TODO Auto-generated method stub
						Log.i("Response",
								"Error in Response.SaveFinalResponse.OnResponse: "
										+ error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_LONG).show();
						pDialog.dismiss();
					}
				});
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			StringBuilder text = new StringBuilder()
					.append(com.farzaneganco.sant.sant.logic.ChangeData.pad(selectedHour)).append(":")
					.append(com.farzaneganco.sant.sant.logic.ChangeData.pad(selectedMinute));

			switch (timePickerKind) {
			case 1:
				tvAzSaatEghdam.setText(text);
				break;

			case 2:
				tvTaSaatEghdam.setText(text);
				break;

			default:
				tvSaateEghdam_Forward.setText(text);
				break;
			}
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		//switch (id) {
		if (id == 1){
		//case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					true);
		}
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forward);

		com.farzaneganco.sant.sant.logic.Menu.SetMenuItem(this); // نمایش منوهای برنامه
		com.farzaneganco.sant.sant.logic.Bind.ShowDarkhastInfo(this, "ارجاع");

		final Spinner spinnerResivers = (Spinner) findViewById(R.id.spinnerResiver);

		ImageView imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
		imgRefresh.setVisibility(View.GONE);

		imgBack = (ImageView) findViewById(R.id.imgBack);
		// imgForwardAnjamShod = (ImageView)
		// findViewById(R.id.imgForwardAnjamShod);
		AnjamShod = true;
		/*
		 * imgForwardAnjamShod.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if (!AnjamShod) {
		 * imgForwardAnjamShod.setImageResource(R.drawable.fill); AnjamShod =
		 * true; } else {
		 * imgForwardAnjamShod.setImageResource(R.drawable.empty); AnjamShod =
		 * false; } } });
		 */
		edtSharheEghdamForward = (EditText) findViewById(R.id.edtSharheEghdamForward);

		tvResivers = (TextView) findViewById(R.id.tvResivers);
		tvResivers.setGravity(Gravity.CENTER | Gravity.RIGHT);
		tvDateMohlat = (TextView) findViewById(R.id.tvDateMohlatEghdam);
		tvDateMohlat.setGravity(Gravity.CENTER | Gravity.RIGHT);
		tvOlaviat = (TextView) findViewById(R.id.tvOlaviat);
		tvOlaviat.setGravity(Gravity.CENTER | Gravity.RIGHT);

		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tvDateMohlatEghdam_Forward = (TextView) findViewById(R.id.tvDateMohlatEghdam_Forward);
		tvDateMohlatEghdam_Forward.setText(com.farzaneganco.sant.sant.logic.Tarikh.GetTarikh());
		tvDateMohlatEghdam_Forward.setHintTextColor(getResources().getColor(
				R.color.Black));

		tvDateEghdam_Eghdam = (TextView) findViewById(R.id.tvDateEghdam_Eghdam);
		tvDateEghdam_Eghdam.setText(com.farzaneganco.sant.sant.logic.Tarikh.GetTarikh());
		tvDateEghdam_Eghdam.setHintTextColor(getResources().getColor(
				R.color.Black));

		tvDateMohlatEghdam_Forward
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DatePickerDailog dp = new DatePickerDailog(
								Forward.this, dateandtime,
								new DatePickerDailog.DatePickerListner() {

									@SuppressLint("SimpleDateFormat")
									@Override
									public void OnDoneButton(Dialog datedialog, String c) {
										datedialog.dismiss();
										tvDateMohlatEghdam_Forward.setText(c.toString());
									}

									@Override
									public void OnCancelButton(Dialog datedialog) {
										// TODO Auto-generated method stub
										datedialog.dismiss();
									}
								});
						dp.show();
					}
				});

		tvDateEghdam_Eghdam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDailog dp = new DatePickerDailog(Forward.this,
						dateandtime, new DatePickerDailog.DatePickerListner() {

							@SuppressLint("SimpleDateFormat")
							@Override
							public void OnDoneButton(Dialog datedialog, String c) {
								datedialog.dismiss();
								tvDateEghdam_Eghdam.setText(c.toString());
							}

							@Override
							public void OnCancelButton(Dialog datedialog) {
								// TODO Auto-generated method stub
								datedialog.dismiss();
							}
						});
				dp.show();

			}
		});

		/*
		 * tvedtDateEghdam.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { DatePickerDailog dp = new
		 * DatePickerDailog(Forward.this, dateandtime, new
		 * DatePickerDailog.DatePickerListner() {
		 * 
		 * @SuppressLint("SimpleDateFormat")
		 * 
		 * @Override public void OnDoneButton(Dialog datedialog, String c) {
		 * datedialog.dismiss(); tvedtDateEghdam.setText(c.toString()); }
		 * 
		 * @Override public void OnCancelButton(Dialog datedialog) { // TODO
		 * Auto-generated method stub datedialog.dismiss(); } }); dp.show(); }
		 * });
		 */
		tvSaateEghdam_Forward = (TextView) findViewById(R.id.tvSaateEghdam_Forward);
		tvAzSaatEghdam = (TextView) findViewById(R.id.tvAzSaatEghdam);
		tvTaSaatEghdam = (TextView) findViewById(R.id.tvTaSaatEghdam);

		// set current time into textview
		
		tvSaateEghdam_Forward.setText(com.farzaneganco.sant.sant.logic.Tarikh.GetSaat());
		tvSaateEghdam_Forward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				timePickerKind = 3;
				//showDialog(Action.TIME_DIALOG_ID);
			}
		});

		tvAzSaatEghdam.setText(com.farzaneganco.sant.sant.logic.Tarikh.GetSaat());
		tvAzSaatEghdam.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timePickerKind = 1;
				//showDialog(Action.TIME_DIALOG_ID);
			}
		});

		tvTaSaatEghdam.setText(com.farzaneganco.sant.sant.logic.Tarikh.GetSaat());
		tvTaSaatEghdam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				timePickerKind = 2;
				//showDialog(Action.TIME_DIALOG_ID);

			}
		});

		Spinner1 = (Spinner) findViewById(R.id.spinner1);
		Spinner2 = (Spinner) findViewById(R.id.spinner2);
		Spinner3 = (Spinner) findViewById(R.id.spinner3);
		Spinner4 = (Spinner) findViewById(R.id.spinner4);

		tvSpiner1 = (TextView) findViewById(R.id.tvSpiner1);
		tvSpiner2 = (TextView) findViewById(R.id.tvSpiner2);
		tvSpiner3 = (TextView) findViewById(R.id.tvSpiner3);
		tvSpiner4 = (TextView) findViewById(R.id.tvSpiner4);
		
		tvSpiner1.setGravity(Gravity.CENTER | Gravity.RIGHT);
		tvSpiner2.setGravity(Gravity.CENTER | Gravity.RIGHT);
		tvSpiner3.setGravity(Gravity.CENTER | Gravity.RIGHT);
		tvSpiner4.setGravity(Gravity.CENTER | Gravity.RIGHT);

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.show();
		com.farzaneganco.sant.sant.webservice.User.GetPersonForForward(Inbox.SelectedItem.ErjaDarkhastId,
				new ArrayResult() {
					@Override
					public void onSuccessVolleyFinished(JSONArray response,
							User user) {
						ArrayList<com.farzaneganco.sant.sant.logic.User> q = com.farzaneganco.sant.sant.logic.User.Convert(response);
						if (q != null) {
							cmbAdapter = new ComboReciverAdapter(
									getApplicationContext(), q);
							spinnerResivers.setAdapter(cmbAdapter);
						} else
							cmbAdapter = null;

						if (Variables.SharhEghdamList == null
								|| Variables.SharhEghdamList.size() == 0) {
							// اطلاعات شرح اقدام را دریافت کرده و در کومبوباکس
							// شرح اقدام نمایش میدهیم
							com.farzaneganco.sant.sant.webservice.Paye.GetSharhEghdam(new ArrayResult() {

								@Override
								public void onSuccessVolleyFinished(
										JSONArray response, User user) {
									// TODO Auto-generated method stub
									Variables.SharhEghdamList = com.farzaneganco.sant.sant.logic.SharhEghdam
											.Convert(response);

									ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam> adapter = new ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam>(
											getApplicationContext(),
											android.R.layout.simple_spinner_dropdown_item,
										com.farzaneganco.sant.sant.logic.SharhEghdam.Get(-1, -1, true,
													"انتخاب کنید"));
									Spinner1.setAdapter(adapter);

									pDialog.dismiss();
								}

								@Override
								public void OnErrorVolleyFinished(
										VolleyError error, User user) {
									// TODO Auto-generated method stub
									pDialog.dismiss();
									Log.i("Forward",
											"Error in getSharhEghdam: "
													+ error.getMessage());
									Toast.makeText(getApplicationContext(),
											error.getMessage(),
											Toast.LENGTH_LONG).show();
								}
							});
						} else {
							ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam> adapter = new ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam>(
									getApplicationContext(),
									android.R.layout.simple_spinner_dropdown_item,
								com.farzaneganco.sant.sant.logic.SharhEghdam.Get(-1, -1, true,
											"انتخاب کنید"));
							Spinner1.setAdapter(adapter);
							pDialog.dismiss();
						}
					}

					@Override
					public void OnErrorVolleyFinished(VolleyError error,
							User user) {
						pDialog.dismiss();
						Log.i("Forward", "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_LONG).show();
					}
				});

		BtnForwardSabt = (Button) findViewById(R.id.BtnForwardSabt);
		BtnForwardSabt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Save();
			}
		});

		Olaviat = com.farzaneganco.sant.sant.logic.Sabet.GetOlaviat();
		spinnerOlaviat = (Spinner) findViewById(R.id.spinnerOlaviat);

		ArrayAdapter<com.farzaneganco.sant.sant.logic.Sabet> adapter = new ArrayAdapter<com.farzaneganco.sant.sant.logic.Sabet>(this,
				android.R.layout.simple_spinner_dropdown_item, Olaviat);
		spinnerOlaviat.setAdapter(adapter);

		spinnerOlaviat.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(R.color.Black);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		Spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(R.color.Black);
				com.farzaneganco.sant.sant.logic.SharhEghdam selectedItem = (com.farzaneganco.sant.sant.logic.SharhEghdam) parent
						.getSelectedItem();

				ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam> adapter = new ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam>(
						getApplicationContext(),
						android.R.layout.simple_spinner_dropdown_item,
					com.farzaneganco.sant.sant.logic.SharhEghdam.Get(selectedItem.sharhTamirId, -1,
								true, "انتخاب کنید"));
				Spinner2.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		Spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(R.color.Black);
				com.farzaneganco.sant.sant.logic.SharhEghdam selectedItem = (com.farzaneganco.sant.sant.logic.SharhEghdam) parent
						.getSelectedItem();
				ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam> adapter = new ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam>(
						getApplicationContext(),
						android.R.layout.simple_spinner_dropdown_item,
					com.farzaneganco.sant.sant.logic.SharhEghdam.Get(selectedItem.sharhTamirId, -1,
								true, "انتخاب کنید"));
				Spinner3.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		Spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(R.color.Black);
				com.farzaneganco.sant.sant.logic.SharhEghdam selectedItem = (com.farzaneganco.sant.sant.logic.SharhEghdam) parent
						.getSelectedItem();

				ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam> adapter = new ArrayAdapter<com.farzaneganco.sant.sant.logic.SharhEghdam>(
						getApplicationContext(),
						android.R.layout.simple_spinner_dropdown_item,
					com.farzaneganco.sant.sant.logic.SharhEghdam.Get(selectedItem.sharhTamirId, -1,
								true, "انتخاب کنید"));
				Spinner4.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		Spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextColor(R.color.Black);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
}