package com.suicune.d2tools.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.suicune.d2tools.R;

public class D2Provider extends ContentProvider {
	public static final String AUTHORITY = "com.suicune.d2tools.database.D2Provider";

	private static final int URI_CHARACTER = 1;
	private static final int URI_CHARACTER_ID = 2;
	private static final int URI_SKILL = 3;
	private static final int URI_SKILL_ID = 4;

	private D2DBOpenHelper mDbHelper;

	static UriMatcher sUriMatcher;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, D2Contract.Characters.TABLE_NAME,
				URI_CHARACTER);
		sUriMatcher.addURI(AUTHORITY, D2Contract.Characters.TABLE_NAME + "/#",
				URI_CHARACTER_ID);
		sUriMatcher.addURI(AUTHORITY, D2Contract.Skills.TABLE_NAME,
				URI_SKILL);
		sUriMatcher.addURI(AUTHORITY, D2Contract.Skills.TABLE_NAME + "/#",
				URI_SKILL_ID);
	}

	@Override
	public boolean onCreate() {
		mDbHelper = new D2DBOpenHelper(getContext());
		return mDbHelper != null;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case URI_CHARACTER:
			return ContentResolver.CURSOR_DIR_BASE_TYPE + AUTHORITY + "."
					+ D2Contract.Characters.TABLE_NAME;
		case URI_CHARACTER_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE + AUTHORITY + "."
					+ D2Contract.Characters.TABLE_NAME;
		case URI_SKILL:
			return ContentResolver.CURSOR_DIR_BASE_TYPE + AUTHORITY + "."
					+ D2Contract.Skills.TABLE_NAME;
		case URI_SKILL_ID:
			return ContentResolver.CURSOR_ITEM_BASE_TYPE + AUTHORITY + "."
					+ D2Contract.Skills.TABLE_NAME;
		default:
			throw new IllegalArgumentException(getContext().getString(
					R.string.illegal_uri)
					+ uri.toString());
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = mDbHelper.getReadableDatabase().query(getTable(uri),
				projection, selection, selectionArgs, null, null, null);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = mDbHelper.getWritableDatabase().insert(getTable(uri), null,
				values);

		Uri result = null;
		if (id != -1) {
			result = ContentUris.withAppendedId(uri, id);
			getContext().getContentResolver().notifyChange(uri, null);
			getContext().getContentResolver().notifyChange(result, null);
		}
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = mDbHelper.getWritableDatabase().update(getTable(uri),
				values, selection, selectionArgs);

		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = mDbHelper.getWritableDatabase().delete(getTable(uri),
				selection, selectionArgs);

		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	private String getTable(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case URI_CHARACTER_ID:
		case URI_CHARACTER:
			return D2Contract.Characters.TABLE_NAME;
		case URI_SKILL_ID:
		case URI_SKILL:
			return D2Contract.Skills.TABLE_NAME;
		default:
			throw new IllegalArgumentException(getContext().getString(
					R.string.illegal_uri)
					+ uri.toString());
		}
	}
}
