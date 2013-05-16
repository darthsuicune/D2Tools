package com.suicune.d2tools;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.suicune.d2tools.database.D2Contract;

public class D2Character {
	public static final int STATS_X_LEVEL = 5;
	public static final int ABILITIES_X_LEVEL = 1;
	public static final int MAX_ABILITIES = 115;

	public static final int INDEX_STR = 0;
	public static final int INDEX_DEX = 1;
	public static final int INDEX_VIT = 2;
	public static final int INDEX_ENE = 3;

	public long mId;
	public String mName;
	public int mClass;
	public int mLevel;
	public int[] mBaseStats = new int[4];
	public int[] mCurrentStats;
	public ArrayList<D2Skill> mSkillList;

	private D2Character(long id, String name, int level, int charClass) {
		mId = id;
		mName = name;
		mLevel = level;
		mClass = charClass;
		mSkillList = new ArrayList<D2Skill>();
	}

	public static class Necromancer extends D2Character {
		public Necromancer(long id, String name, int level) {
			super(id, name, level, R.string.necromancer);
			mBaseStats[INDEX_STR] = 15;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 15;
			mBaseStats[INDEX_ENE] = 25;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Sorceress extends D2Character {
		public Sorceress(long id, String name, int level) {
			super(id, name, level, R.string.sorceress);
			mBaseStats[INDEX_STR] = 10;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 10;
			mBaseStats[INDEX_ENE] = 35;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Amazon extends D2Character {
		public Amazon(long id, String name, int level) {
			super(id, name, level, R.string.amazon);
			mBaseStats[INDEX_STR] = 20;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 20;
			mBaseStats[INDEX_ENE] = 15;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Barbarian extends D2Character {
		public Barbarian(long id, String name, int level) {
			super(id, name, level, R.string.barbarian);
			mBaseStats[INDEX_STR] = 30;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 25;
			mBaseStats[INDEX_ENE] = 10;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Paladin extends D2Character {
		public Paladin(long id, String name, int level) {
			super(id, name, level, R.string.paladin);
			mBaseStats[INDEX_STR] = 25;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 25;
			mBaseStats[INDEX_ENE] = 15;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Assassin extends D2Character {
		public Assassin(long id, String name, int level) {
			super(id, name, level, R.string.assassin);
			mBaseStats[INDEX_STR] = 20;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 20;
			mBaseStats[INDEX_ENE] = 25;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Druid extends D2Character {
		public Druid(long id, String name, int level) {
			super(id, name, level, R.string.druid);
			mBaseStats[INDEX_STR] = 15;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 25;
			mBaseStats[INDEX_ENE] = 20;
			mCurrentStats = mBaseStats;
		}
	}

	public static D2Character createChar(Cursor c) {
		if (c.moveToFirst()) {
			long id = c.getLong(c.getColumnIndex(D2Contract.Characters._ID));
			
			try {
				int characterClass = Integer.parseInt(c.getString(c
						.getColumnIndex(D2Contract.Characters.CLASS)));
				String name = c.getString(c
						.getColumnIndex(D2Contract.Characters.NAME));
				int level = Integer.parseInt(c.getString(c
						.getColumnIndex(D2Contract.Characters.LEVEL)));
				int[] stats = new int[4];
				stats[INDEX_STR] = Integer.parseInt(c.getString(c
						.getColumnIndex(D2Contract.Characters.STR)));
				stats[INDEX_DEX] = Integer.parseInt(c.getString(c
						.getColumnIndex(D2Contract.Characters.DEX)));
				stats[INDEX_VIT] = Integer.parseInt(c.getString(c
						.getColumnIndex(D2Contract.Characters.VIT)));
				stats[INDEX_ENE] = Integer.parseInt(c.getString(c
						.getColumnIndex(D2Contract.Characters.ENE)));

				switch (characterClass) {
				case R.string.necromancer:
					Necromancer necro = new Necromancer(id, name, level);
					necro.mCurrentStats = stats;
					return necro;
				case R.string.sorceress:
					Sorceress sorc = new Sorceress(id, name, level);
					sorc.mCurrentStats = stats;
					return sorc;
				case R.string.barbarian:
					Barbarian barb = new Barbarian(id, name, level);
					barb.mCurrentStats = stats;
					return barb;
				case R.string.amazon:
					Amazon amaz = new Amazon(id, name, level);
					amaz.mCurrentStats = stats;
					return amaz;
				case R.string.paladin:
					Paladin pala = new Paladin(id, name, level);
					pala.mCurrentStats = stats;
					return pala;
				case R.string.assassin:
					Assassin assa = new Assassin(id, name, level);
					assa.mCurrentStats = stats;
					return assa;
				case R.string.druid:
					Druid druid = new Druid(id, name, level);
					druid.mCurrentStats = stats;
					return druid;
				default:
					return null;
				}
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public static D2Character createChar(int id, String name, int level, int charClass,
			int[] stats) {
		switch (charClass) {
		case R.string.necromancer:
			Necromancer necro = new Necromancer(id, name, level);
			necro.mCurrentStats = stats;
			return necro;
		case R.string.sorceress:
			Sorceress sorc = new Sorceress(id, name, level);
			sorc.mCurrentStats = stats;
			return sorc;
		case R.string.barbarian:
			Barbarian barb = new Barbarian(id, name, level);
			barb.mCurrentStats = stats;
			return barb;
		case R.string.amazon:
			Amazon amaz = new Amazon(id, name, level);
			amaz.mCurrentStats = stats;
			return amaz;
		case R.string.paladin:
			Paladin pala = new Paladin(id, name, level);
			pala.mCurrentStats = stats;
			return pala;
		case R.string.assassin:
			Assassin assa = new Assassin(id, name, level);
			assa.mCurrentStats = stats;
			return assa;
		case R.string.druid:
			Druid druid = new Druid(id, name, level);
			druid.mCurrentStats = stats;
			return druid;
		default:
			return null;
		}
	}

	public void addSkill(String name, int level) {
		for (int i = 0; i < mSkillList.size(); i++) {
			if (mSkillList.get(i).mName.equals(name)) {
				mSkillList.get(i).mLevel = level;
				return;
			}
		}
		mSkillList.add(new D2Skill(name, level));
	}

	public void save(Context context) {
		ContentValues values = new ContentValues();
		values.put(D2Contract.Characters.NAME, mName);
		values.put(D2Contract.Characters.CLASS, mClass);
		values.put(D2Contract.Characters.LEVEL, mLevel);
		values.put(D2Contract.Characters.STR, mCurrentStats[INDEX_STR]);
		values.put(D2Contract.Characters.DEX, mCurrentStats[INDEX_DEX]);
		values.put(D2Contract.Characters.VIT, mCurrentStats[INDEX_VIT]);
		values.put(D2Contract.Characters.ENE, mCurrentStats[INDEX_ENE]);

		String where = D2Contract.Characters._ID + "=?";
		String[] selectionArgs = { "" + mId };
		context.getContentResolver().update(D2Contract.Characters.CONTENT_URI,
				values, where, selectionArgs);

		for (int i = 0; i < mSkillList.size(); i++) {
			D2Skill skill = mSkillList.get(i);
			values.clear();
			values.put(D2Contract.Skills.CHARACTER, mId);
			values.put(D2Contract.Skills.SKILL_LEVEL, skill.mLevel);
			values.put(D2Contract.Skills.SKILL_NAME, skill.mName);

			String selection = D2Contract.Skills.SKILL_NAME + "=? AND "
					+ D2Contract.Skills.CHARACTER + "=?";
			String[] args = { skill.mName, "" + mId };
			context.getContentResolver().update(D2Contract.Skills.CONTENT_URI,
					values, selection, args);
		}

	}

}
