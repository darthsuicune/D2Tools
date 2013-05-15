package com.suicune.d2tools;

import java.util.ArrayList;

public class D2Skill {
	public String mName;
	public String mDescription;
	public ArrayList<D2Skill> mSinergies;
	public int mLevel;

	public D2Skill(String name, int level) {
		mName = name;
		mLevel = level;
	}
}
