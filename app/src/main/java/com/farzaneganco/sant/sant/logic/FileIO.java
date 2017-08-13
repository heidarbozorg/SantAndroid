package com.farzaneganco.sant.sant.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

public class FileIO {

	public static boolean Delete(File fileForDelete) {
		try {
			if (fileForDelete.exists())
				fileForDelete.delete();
			else
				return (false);

			return (true);

		} catch (Exception e) {
			Log.i("logic.FileIO", "Error in Delete: " + e.getMessage());
			return (false);
		}
	}

	public static boolean Copy(Context context, String assetsFileName,
			String DestinationFolder, String DestinationFileName) {
		try {
			File folder = new File(DestinationFolder);
			if (!folder.isDirectory())
				folder.mkdir();

			OutputStream myOutPut = new FileOutputStream(DestinationFolder
					+ DestinationFileName);
			byte[] buffer = new byte[1024];
			int length;

			InputStream myInput = context.getAssets().open(assetsFileName);

			while ((length = myInput.read(buffer)) > 0) {
				myOutPut.write(buffer, 0, length);
			}
			myInput.close();
			myOutPut.flush();
			myOutPut.close();
			return (true);
		} catch (Exception e) {
			Log.i("logic.FileIO", "Error in Copy: " + e.getMessage());
			return (false);
		}
	}
}
