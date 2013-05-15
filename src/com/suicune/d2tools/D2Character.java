package com.suicune.d2tools;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.suicune.d2tools.database.D2Contract;

public class D2Character {
	public static final int NECROMANCER = 0;
	public static final int SORCERESS = 1;
	public static final int AMAZON = 2;
	public static final int BARBARIAN = 3;
	public static final int PALADIN = 4;
	public static final int ASSASSIN = 5;
	public static final int DRUID = 6;

	public static final int STATS_X_LEVEL = 5;
	public static final int ABILITIES_X_LEVEL = 1;
	public static final int MAX_ABILITIES = 115;

	public static final int INDEX_STR = 0;
	public static final int INDEX_DEX = 1;
	public static final int INDEX_VIT = 2;
	public static final int INDEX_ENE = 3;

	public long mId;
	public String mName;
	public int mLevel;
	public int[] mBaseStats = new int[4];
	public int[] mCurrentStats;
	public ArrayList<D2Skill> mSkillList;

	private D2Character(long id, String name, int level) {
		mId = id;
		mName = name;
		mLevel = level;
		mSkillList = new ArrayList<D2Skill>();
	}

	public static class Necromancer extends D2Character {
		public Necromancer(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 15;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 15;
			mBaseStats[INDEX_ENE] = 25;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Sorceress extends D2Character {
		public Sorceress(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 10;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 10;
			mBaseStats[INDEX_ENE] = 35;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Amazon extends D2Character {
		public Amazon(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 20;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 20;
			mBaseStats[INDEX_ENE] = 15;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Barbarian extends D2Character {
		public Barbarian(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 30;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 25;
			mBaseStats[INDEX_ENE] = 10;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Paladin extends D2Character {
		public Paladin(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 25;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 25;
			mBaseStats[INDEX_ENE] = 15;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Assassin extends D2Character {
		public Assassin(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 20;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 20;
			mBaseStats[INDEX_ENE] = 25;
			mCurrentStats = mBaseStats;
		}
	}

	public static class Druid extends D2Character {
		public Druid(long id, String name, int level) {
			super(id, name, level);
			mBaseStats[INDEX_STR] = 15;
			mBaseStats[INDEX_DEX] = 20;
			mBaseStats[INDEX_VIT] = 25;
			mBaseStats[INDEX_ENE] = 20;
			mCurrentStats = mBaseStats;
		}
	}

	public static D2Character createChar(Cursor c) {
		if (c.moveToFirst()) {
			long mId = c.getLong(c.getColumnIndex(D2Contract.Characters._ID));
			String characterClass = c.getString(c
					.getColumnIndex(D2Contract.Characters.CLASS));
			try {
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

				switch (Integer.parseInt(characterClass)) {
				case NECROMANCER:
					Necromancer necro = new Necromancer(mId, name, level);
					necro.mCurrentStats = stats;
					return necro;
				case SORCERESS:
					Sorceress sorc = new Sorceress(mId, name, level);
					sorc.mCurrentStats = stats;
					return sorc;
				case BARBARIAN:
					Barbarian barb = new Barbarian(mId, name, level);
					barb.mCurrentStats = stats;
					return barb;
				case AMAZON:
					Amazon amaz = new Amazon(mId, name, level);
					amaz.mCurrentStats = stats;
					return amaz;
				case PALADIN:
					Paladin pala = new Paladin(mId, name, level);
					pala.mCurrentStats = stats;
					return pala;
				case ASSASSIN:
					Assassin assa = new Assassin(mId, name, level);
					assa.mCurrentStats = stats;
					return assa;
				case DRUID:
					Druid druid = new Druid(mId, name, level);
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

	public void addSkill(String name, int level) {
		int actualValue = 0;
		for (int i = 0; i < mSkillList.size(); i++) {
			if (isInSkillList(name)) {
				actualValue = mSkillList.get(i).mLevel;
			}
		}
		D2Skill item = new D2Skill(name, actualValue);
		mSkillList.add(item);
	}

	private boolean isInSkillList(String name) {
		for (int i = 0; i < mSkillList.size(); i++) {
			if (mSkillList.get(i).mName.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void save(Context context) {
		ContentValues values = new ContentValues();
		values.put(D2Contract.Characters.NAME, mName);
		values.put(D2Contract.Characters.CLASS, getClass(this));
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

	private int getClass(D2Character d2Character) {
		if (d2Character instanceof Necromancer) {
			return NECROMANCER;
		} else if (d2Character instanceof Sorceress) {
			return SORCERESS;
		} else if (d2Character instanceof Paladin) {
			return PALADIN;
		} else if (d2Character instanceof Amazon) {
			return AMAZON;
		} else if (d2Character instanceof Assassin) {
			return ASSASSIN;
		} else if (d2Character instanceof Barbarian) {
			return BARBARIAN;
		} else if (d2Character instanceof Druid) {
			return DRUID;
		} else {
			return 0;
		}
	}

}
