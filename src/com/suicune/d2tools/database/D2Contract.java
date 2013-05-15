package com.suicune.d2tools.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class D2Contract {

	public static final class Characters implements BaseColumns {
		public static final String TABLE_NAME = "characters";

		public static final String CLASS = "class";
		public static final String NAME = "name";
		public static final String LEVEL = "level";
		public static final String STR = "str";
		public static final String DEX = "dex";
		public static final String VIT = "vit";
		public static final String ENE = "ene";

		public static final Uri CONTENT_URI = Uri.parse(D2Provider.AUTHORITY
				+ TABLE_NAME);
	}

	public static final class Skills implements BaseColumns {
		public static final String TABLE_NAME = "skills";

		public static final String CHARACTER = "character";
		public static final String SKILL_NAME = "skillname";
		public static final String SKILL_LEVEL = "skilllevel";

		public static final Uri CONTENT_URI = Uri.parse(D2Provider.AUTHORITY
				+ TABLE_NAME);
	}
}
