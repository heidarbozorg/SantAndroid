package com.farzaneganco.sant.sant.logic;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.farzaneganco.sant.sant.R;

public class Menu {
	public static String[] navigationArray = { "خروج از حساب کاربری" };

	public static void SetMenuItem(final Activity ctx) {
		try {
			ImageView imgMenu = (ImageView) ctx.findViewById(R.id.imgMenu);
			final DrawerLayout drawerLayout = (DrawerLayout) ctx.findViewById(R.id.drawer_layout);
			imgMenu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
						drawerLayout.closeDrawers();
					} else {
						drawerLayout.openDrawer(Gravity.RIGHT);
					}
				}
			});
			
			TextView tvUserName = (TextView) ctx.findViewById(R.id.tvUserName);
			TextView tvEmail = (TextView) ctx.findViewById(R.id.tvEmail);
			
			tvUserName.setText(com.farzaneganco.sant.sant.logic.Variables.user.FullName);
			tvEmail.setText(com.farzaneganco.sant.sant.logic.Variables.user.GetUsername());

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,
					R.layout.category_menu, navigationArray);

			ListView listMenu = (ListView) ctx.findViewById(R.id.listMenu);
			listMenu.setAdapter(adapter);
			listMenu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					Intent menuIntent = null;

					switch (position) {

					case 0:
						Bind.RemoveUserInfo(ctx);
						Intent exit = new Intent(ctx, com.farzaneganco.sant.sant.LoginActivity.class);
						ctx.startActivity(exit);
						ctx.finish();
						break;
					}
				}
			});
		} catch (Exception e) {
		}
	}
}