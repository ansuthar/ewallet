/*
 * Author: ittran 90
 * Create Date: 31/03/2012
 * Description: Helper for connect and access with database SQLite
 */
package com.share.code.ultility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static String DB_Path = "/data/data/EwalletHutech/databases/";
	public static String DB_Name = "ewallet";
	private SQLiteDatabase myDatabase;
	private final Context myContext;
	
	public DatabaseHelper(Context context) {		
		// TODO Auto-generated constructor stub
		super(context, DB_Name + ".sqlite", null, 3);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		if(myDatabase!=null)
			myDatabase.close();
		super.close();
	}
	
	private boolean checkDatabase()
	{
		File f = new File(DB_Path + DB_Name + ".sqlite");
		return f.exists();	
	}
	
	/**
	 * 
	 */
	private void copyDatabase()
	{
		try
		{
			String openFileName = DB_Path+DB_Name+".sqlite";
			File f = new File(openFileName);
			FileHelper.chmod(f, 0777);
			
			FileOutputStream myOutput = new FileOutputStream(openFileName);
			byte[] buffer = new byte[1024];
			int length;
			int files_num = 2;// number of files
			for (int i = 1; i < files_num; i++) {
				InputStream myInput = myContext.getAssets().open(
						DB_Name + i + ".sqlite");
				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}
				myInput.close();
			}
			myOutput.flush();
			myOutput.close();
		}
		catch( Exception ex)
		{
			
		}
	}
	
	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_Path+ DB_Name + ".sqlite";
		myDatabase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}
	
	public void createDatabase() throws IOException
	{
		boolean dbExist = checkDatabase();

		if (dbExist) {
			// copyDataBase();
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();
			// set write permission

			try {
				copyDatabase();
				this.close();

			} catch (Exception e) {
				throw new Error("Error copying database");
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
