package com.suicune.d2tools.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.d2tools.R;
import com.suicune.d2tools.activities.CharacterDetailActivity;
import com.suicune.d2tools.activities.CharacterListActivity;

/**
 * A fragment representing a single Character detail screen. This fragment is
 * either contained in a {@link CharacterListActivity} in two-pane mode (on
 * tablets) or a {@link CharacterDetailActivity} on handsets.
 */
public class CharacterDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The character this fragment is presenting.
	 */
	private Character mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CharacterDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the character specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			// TODO: Add actual item
			mItem = getCharacter(getArguments().getLong(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_character_detail,
				container, false);
		
		// TODO: add information
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.character_detail))
					.setText(mItem);
		}

		return rootView;
	}

	public Character getCharacter(long id) {
		return null;
	}
}
