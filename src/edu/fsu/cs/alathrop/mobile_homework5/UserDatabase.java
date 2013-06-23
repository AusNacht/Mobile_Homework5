package edu.fsu.cs.alathrop.mobile_homework5;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class UserDatabase extends ContentProvider {
	
	public final static String DBNAME = "UDatabase";
	public final static String COLUMN_FIRSTNAME = "FirstName";
	public final static String COLUMN_LASTNAME = "LastName";
	public final static String COLUMN_PHONE = "Phone";
	public final static String COLUMN_EMAIL = "Email";
	public final static String COLUMN_USERNAME = "Username";
	public final static String COLUMN_PASSWORD = "Password";
	public final static String COLUMN_GENDER = "Gender";
	public final static String COLUMN_COUNTRY = "Country";
	private static final String SQL_CREATE_MAIN = "CREATE TABLE Users ( " 
			+ "_ID INTEGER PRIMARY KEY," 
			+ COLUMN_FIRSTNAME + " TEXT," 
			+ COLUMN_LASTNAME + " TEXT,"
			+ COLUMN_PHONE + " TEXT,"
			+ COLUMN_EMAIL + " TEXT,"
			+ COLUMN_USERNAME + " TEXT,"
			+ COLUMN_PASSWORD + " TEXT,"
			+ COLUMN_GENDER + " TEXT,"
			+ COLUMN_COUNTRY + " TEXT )";	
	public static final Uri CONTENT_URI = Uri.parse("content://userdb.provider/Users");
	
	private MainDatabaseHelper mOpenHelper;

	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {

		return mOpenHelper.getWritableDatabase().delete("Users", whereClause, whereArgs);
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues values) {
		String fname = values.getAsString("FirstName").trim();
		String lname = values.getAsString("LastName").trim();
		String phone = values.getAsString("Phone").trim();
		String email = values.getAsString("Email").trim();
		String username = values.getAsString("Username").trim();
		String password = values.getAsString("Password").trim();
		String gender = values.getAsString("Gender").trim();
		String country = values.getAsString("Country").trim();
		
		if(fname.equals(""))
			return null;
		if(lname.equals(""))
			return null;
		if(phone.equals(""))
			return null;
		if(email.equals(""))
			return null;
		if(username.equals(""))
			return null;
		if(password.equals(""))
			return null;
		if(gender.equals(""))
			return null;
		if(country.equals(""))
			return null;
		
		long id = mOpenHelper.getWritableDatabase().insert("Users", null, values);
		
		return Uri.withAppendedPath(CONTENT_URI,  "" + id);
	}

	@Override
	public boolean onCreate() {
		mOpenHelper = new MainDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri table, String[] columns, String selection, String[] args,
			String orderBy) {
		return mOpenHelper.getReadableDatabase().query("Users", columns, selection, args, null, null, orderBy);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return mOpenHelper.getWritableDatabase().update("Users", values, selection, selectionArgs);
	}

	
	protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
		MainDatabaseHelper(Context context){
			super(context, DBNAME, null, 1);	
		}
		
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(SQL_CREATE_MAIN);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){	
		}
	}
	
}
