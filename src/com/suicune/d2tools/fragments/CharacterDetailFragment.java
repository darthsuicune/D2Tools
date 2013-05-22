package com.suicune.d2tools.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.*;
import com.suicune.d2tools.D2Character;
import com.suicune.d2tools.R;
import com.suicune.d2tools.activities.CharacterDetailActivity;
import com.suicune.d2tools.activities.CharacterListActivity;
import com.suicune.d2tools.database.D2Contract;

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
    private static final int LOADER_SKILLS = 2;

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
            getCharacter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_detail,
                container, false);

        if (mItem != null) {
            showCharacterInfo();
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

    public void getCharacter() {
        getLoaderManager()
                .restartLoader(LOADER_CHARACTER, getArguments(), this);
    }

    private void copyFrom() {
        // TODO Auto-generated method stub

    }

    private void delete() {
        if (mItem != null) {
            String selection = D2Contract.Characters._ID + "=?";
            String[] selectionArgs = {"" + mItem.mId};

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

    private void showCharacterInfo() {
        // TODO Auto-generated method stub
    }

    private void loadSkills() {
        getLoaderManager().restartLoader(LOADER_SKILLS, getArguments(), this);
    }

    private void showSkills(Cursor c) {
        // TODO Auto-generated method stub
    }

    private void createNewChar() {
        // TODO Auto-generated method stub
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (!args.containsKey(ARG_ITEM_ID)) {
            return null;
        }

        String selection;
        String[] selectionArgs = {Long.toString(args.getLong(ARG_ITEM_ID))};

        switch (id) {
            case LOADER_CHARACTER:
                selection = D2Contract.Characters._ID + "=?";
                return new CursorLoader(getActivity(),
                        D2Contract.Characters.CONTENT_URI, null, selection,
                        selectionArgs, null);
            case LOADER_SKILLS:
                selection = D2Contract.Skills.CHARACTER + "=?";
                return new CursorLoader(getActivity(),
                        D2Contract.Skills.CONTENT_URI, null, selection,
                        selectionArgs, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            switch (loader.getId()) {
                case LOADER_CHARACTER:
                    if (cursor.getCount() > 0) {
                        mItem = D2Character.createChar(cursor);
                        loadSkills();
                        showCharacterInfo();
                    } else {
                        createNewChar();
                    }
                    break;
                case LOADER_SKILLS:
                    showSkills(cursor);
                    break;
                default:
                    break;
            }
        } else {
            createNewChar();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
