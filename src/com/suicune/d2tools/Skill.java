package com.suicune.d2tools;

import java.util.ArrayList;

public class Skill {
	public String mName;
	public String mDescription;
	public ArrayList<Skill> mSinergies;

	public Skill(String name, String description, ArrayList<Skill> sinergies) {
		mName = name;
		mDescription = description;
		mSinergies = sinergies;
	}
	
}
