package com.suicune.d2tools;

public abstract class Character {
	public static final int STATS_X_LEVEL = 5;
	public static final int ABILITIES_X_LEVEL = 1;
	public static final int MAX_ABILITIES = 115;

	public static final int INDEX_STR = 0;
	public static final int INDEX_DEX = 1;
	public static final int INDEX_VIT = 2;
	public static final int INDEX_ENE = 3;

	public String mName;
	public int mLevel;
	public int[] mBaseStats = new int[4];
	public int[] mAssignedStats = { 0, 0, 0, 0 };

	public class Necromancer extends Character {
		public Necromancer(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 15;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 15;
			mBaseStats[INDEX_ENE] = 25;
		}
	}

	public class Sorceress extends Character {
		public Sorceress(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 10;
			mBaseStats[INDEX_DEX] = 25;
			mBaseStats[INDEX_VIT] = 10;
			mBaseStats[INDEX_ENE] = 35;
		}
	}

	public class Amazon extends Character {
		public Amazon(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 0;
			mBaseStats[INDEX_DEX] = 0;
			mBaseStats[INDEX_VIT] = 0;
			mBaseStats[INDEX_ENE] = 0;
		}
	}

	public class Barbarian extends Character {
		public Barbarian(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 0;
			mBaseStats[INDEX_DEX] = 0;
			mBaseStats[INDEX_VIT] = 0;
			mBaseStats[INDEX_ENE] = 0;
		}
	}

	public class Paladin extends Character {
		public Paladin(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 0;
			mBaseStats[INDEX_DEX] = 0;
			mBaseStats[INDEX_VIT] = 0;
			mBaseStats[INDEX_ENE] = 0;
		}
	}

	public class Assassin extends Character {
		public Assassin(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 0;
			mBaseStats[INDEX_DEX] = 0;
			mBaseStats[INDEX_VIT] = 0;
			mBaseStats[INDEX_ENE] = 0;
		}
	}

	public class Druid extends Character {
		public Druid(String name, int level) {
			mName = name;
			mLevel = level;
			mBaseStats = new int[4];
			mBaseStats[INDEX_STR] = 0;
			mBaseStats[INDEX_DEX] = 0;
			mBaseStats[INDEX_VIT] = 0;
			mBaseStats[INDEX_ENE] = 0;
		}
	}
}
