package com.suicune.d2tools.fragments;

import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suicune.d2tools.R;
import com.suicune.d2tools.activities.CharacterDetailActivity;
import com.suicune.d2tools.activities.CharacterListActivity;
import com.suicune.d2tools.database.D2Contract;
import com.suicune.d2tools.D2Character;

/**
 * A fragment representing a single Character detail screen. This fragment is
 * either contained in a {@link CharacterListActivity} in two-pane mode (on
 * tablets) or a {@link CharacterDetailActivity} on handsets.
 */
public class CharacterDetailFragment extends Fragment implements
		LoaderCallbacks<Cursor> {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	private static final int LOADER_CHARACTER = 1;

	/**
	 * The character this fragment is presenting.
	 */
	private D2Character mItem;

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
			// arguments.
			getCharacter(getArguments().getLong(ARG_ITEM_ID));
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
					.setText(mItem.toString());
		}

		return rootView;
	}

	@Override
	public void onPause() {
		save();
		super.onPause();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_details, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_details_copy_from:
			copyFrom();
			return true;
		case R.id.menu_details_delete:
			delete();
			return true;
		case R.id.menu_details_save:
			save();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void getCharacter(long id) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, id);
		getLoaderManager().restartLoader(LOADER_CHARACTER, args, this);
	}

	private void copyFrom() {
		// TODO Auto-generated method stub

	}

	private void delete() {
		if (mItem != null) {
			String selection = D2Contract.Characters._ID + "=?";
			String[] selectionArgs = { "" + mItem.mId };

			getActivity().getContentResolver()
					.delete(D2Contract.Characters.CONTENT_URI, selection,
							selectionArgs);
			selection = D2Contract.Skills.CHARACTER + "=?";
			getActivity().getContentResolver().delete(
					D2Contract.Skills.CONTENT_URI, selection, selectionArgs);
		}
	}

	private void save() {
		if (mItem != null) {
			mItem.save(getActivity());
		}
	}

	private void showInfo() {
		// TODO Auto-generated method stub
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case LOADER_CHARACTER:
			String[] selectionArgs = new String[1];
			if (args.containsKey(ARG_ITEM_ID)) {
				selectionArgs[0] = Long.toString(args.getLong(ARG_ITEM_ID));
			} else {
				return null;
			}
			String selection = D2Contract.Characters._ID + "=?";
			return new CursorLoader(getActivity(),
					D2Contract.Characters.CONTENT_URI, null, selection,
					selectionArgs, null);
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor != null) {
			mItem = D2Character.createChar(cursor);
			showInfo();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}
