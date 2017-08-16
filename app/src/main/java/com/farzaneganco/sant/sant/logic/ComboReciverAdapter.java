package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.farzaneganco.sant.sant.R;

public class ComboReciverAdapter extends ArrayAdapter<com.farzaneganco.sant.sant.logic.User> {
	ArrayList<com.farzaneganco.sant.sant.logic.User> q;
	Context ctx;

	public ComboReciverAdapter(Context ctx, ArrayList<com.farzaneganco.sant.sant.logic.User> objects) {
		super(ctx, R.layout.custom_spinner, objects);
		this.q = objects;
		this.ctx = ctx;
	}


	@Override
	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		return getCustomView(position, cnvtView, prnt);
	}

	@Override
	public View getView(int pos, View cnvtView, ViewGroup prnt) {
		return getCustomView(pos, cnvtView, prnt);
	}

	public View getCustomView(final int position, View convertView, ViewGroup parent) {
		//LayoutInflater inflater = getLayoutInflater();
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE );

		View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);
		TextView main_text = (TextView) mySpinner.findViewById(R.id.txtSpinnerValue);
		main_text.setText(q.get(position).FullName);
		TextView subSpinner = (TextView) mySpinner.findViewById(R.id.txtSpinnerSubs);
		subSpinner.setText(q.get(position).Semat);
		ImageView left_icon = (ImageView) mySpinner.findViewById(R.id.imgSpinner);
		left_icon.setImageResource(R.drawable.drop);
		
		ToggleButton tbSelectReciver = (ToggleButton) mySpinner.findViewById(R.id.tbSelectReciver);
		tbSelectReciver.setChecked(q.get(position).Checked);
		
		tbSelectReciver.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				q.get(position).Checked = isChecked;
			}
		});

		return mySpinner;
	}

}