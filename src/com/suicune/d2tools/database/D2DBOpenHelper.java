package com.suicune.d2tools.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class D2DBOpenHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "diablotools";
	private static final int DB_VERSION = 1;

	private static final String key = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
	private static final String text = " TEXT, ";
	private static final String textEnd = " TEXT)";

	public D2DBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (db.isReadOnly() || !db.isOpen()) {
			db = getWritableDatabase();
		}

		db.execSQL("CREATE TABLE " + D2Contract.Characters.TABLE_NAME + " ("
				+ D2Contract.Characters._ID + key 
				+ D2Contract.Characters.CLASS + text 
				+ D2Contract.Characters.NAME + text
				+ D2Contract.Characters.LEVEL + text
				+ D2Contract.Characters.STR + text 
				+ D2Contract.Characters.DEX + text 
				+ D2Contract.Characters.VIT + text
				+ D2Contract.Characters.ENE + textEnd);
		
		db.execSQL("CREATE TABLE " + D2Contract.Skills.TABLE_NAME + " ("
				+ D2Contract.Skills._ID + key + text
				+ D2Contract.Skills.CHARACTER + text
				+ D2Contract.Skills.SKILL_LEVEL + text
				+ D2Contract.Skills.SKILL_NAME + textEnd
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
		// Nothing to do here yet
	}
}
