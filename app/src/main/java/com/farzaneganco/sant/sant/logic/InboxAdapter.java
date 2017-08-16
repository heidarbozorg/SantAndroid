package com.farzaneganco.sant.sant.logic;

import java.util.ArrayList;

import com.farzaneganco.sant.sant.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InboxAdapter extends ArrayAdapter<com.farzaneganco.sant.sant.logic.WFlow>  {
	private final ArrayList<com.farzaneganco.sant.sant.logic.WFlow> list;
	private final Activity context;

	public InboxAdapter(Activity context, ArrayList<com.farzaneganco.sant.sant.logic.WFlow> list) {
		//super(context, R.layout.note_item);
		super(context, R.layout.inbox_item, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView tvInboxTarikheErja;
		protected TextView tvInboxMohlatEghdam;
		protected TextView tvInboxMozoo;
		protected TextView tvInboxShomarePeigiry;
		protected TextView tvInboxVaziatDarkhast;
		protected TextView tvInboxErjadahande;
		protected TextView tvDescInboxItem;
		protected TextView tvInboxTajhizName;
		protected TextView tvInboxMahalEsteghrar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.inbox_item, null);
			viewHolder = new ViewHolder();
			
			viewHolder.tvInboxTarikheErja = (TextView) convertView.findViewById(R.id.tvInboxTarikheErja);
			viewHolder.tvInboxMohlatEghdam = (TextView) convertView.findViewById(R.id.tvInboxMohlatEghdam);
			viewHolder.tvInboxMozoo = (TextView) convertView.findViewById(R.id.tvInboxMozoo);
			viewHolder.tvInboxShomarePeigiry = (TextView) convertView.findViewById(R.id.tvInboxShomarePeigiry);
			viewHolder.tvInboxVaziatDarkhast = (TextView) convertView.findViewById(R.id.tvInboxVaziatDarkhast);
			viewHolder.tvInboxErjadahande = (TextView) convertView.findViewById(R.id.tvInboxErjadahande);
			
			viewHolder.tvDescInboxItem = (TextView) convertView.findViewById(R.id.tvDescInboxItem);
			viewHolder.tvInboxTajhizName = (TextView) convertView.findViewById(R.id.tvInboxTajhizName);
			viewHolder.tvInboxMahalEsteghrar = (TextView) convertView.findViewById(R.id.tvInboxMahalEsteghrar);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvInboxTarikheErja.setText(list.get(position).TarikhErja);
		if (list.get(position).MohlatEghdam != null)
			viewHolder.tvInboxMohlatEghdam.setText(list.get(position).MohlatEghdam);
		
		viewHolder.tvInboxMozoo.setText(list.get(position).MozooDarkhastStr);
		viewHolder.tvInboxShomarePeigiry.setText(list.get(position).ShomareDarkhast);
		viewHolder.tvInboxVaziatDarkhast.setText(list.get(position).vaziatDarkhastStr);
		viewHolder.tvInboxErjadahande.setText(list.get(position).FullName_ErjaDahande);
		viewHolder.tvDescInboxItem.setText(list.get(position).SharhErja);
		
		if (list.get(position).TajhizId > 0)
		{
			viewHolder.tvInboxMahalEsteghrar.setVisibility(View.VISIBLE);
			viewHolder.tvInboxTajhizName.setVisibility(View.VISIBLE);
			
			viewHolder.tvInboxMahalEsteghrar.setText("محل استقرار: " + list.get(position).MahaleEsteghrar);
			viewHolder.tvInboxTajhizName.setText("نام تجهیز: " + list.get(position).TajhizName);
		}
		else
		{
			viewHolder.tvInboxMahalEsteghrar.setVisibility(View.GONE);
			viewHolder.tvInboxTajhizName.setVisibility(View.GONE);
		}
		
		
		if (list.get(position).TarikhRoyat == null 
			|| list.get(position).TarikhRoyat == ""
			|| (list.get(position).TarikhRoyat != "1" && list.get(position).TarikhRoyat.length() < 10)
				)
		{
			viewHolder.tvDescInboxItem.setTextColor(this.context.getResources().getColor(R.color.Black));
			viewHolder.tvInboxMozoo.setTextColor(this.context.getResources().getColor(R.color.Black));
			viewHolder.tvInboxMahalEsteghrar.setTextColor(this.context.getResources().getColor(R.color.Black));
			viewHolder.tvInboxTajhizName.setTextColor(this.context.getResources().getColor(R.color.Black));
		}
		else
		{
			viewHolder.tvDescInboxItem.setTextColor(this.context.getResources().getColor(R.color.Gray3));
			viewHolder.tvInboxMozoo.setTextColor(this.context.getResources().getColor(R.color.Gray3));
			viewHolder.tvInboxMahalEsteghrar.setTextColor(this.context.getResources().getColor(R.color.Gray3));
			viewHolder.tvInboxTajhizName.setTextColor(this.context.getResources().getColor(R.color.Gray3));
		}
		return convertView;
	}
}
