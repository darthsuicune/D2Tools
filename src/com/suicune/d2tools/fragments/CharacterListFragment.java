package com.suicune.d2tools.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.suicune.d2tools.D2Character;
import com.suicune.d2tools.R;
import com.suicune.d2tools.database.D2Contract;

import java.util.ArrayList;

/**
 * A list fragment representing a list of Characters. This fragment also
 * supports tablet devices by allowing list items to be given an 'activated'
 * state upon selection. This helps indicate which item is currently being
 * viewed in a {@link CharacterDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class CharacterListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_CHARACTERS = 1;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sCharacterCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }

        @Override
        public void createNewChar(){
        }

    };
    ArrayList<D2Character> mCharacterList;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sCharacterCallbacks;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CharacterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCharacterList();
    }

    private void getCharacterList() {
        getLoaderManager().restartLoader(LOADER_CHARACTERS, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sCharacterCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_char_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_char_list_add:
                mCallbacks.createNewChar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private void loadCharacters(Cursor cursor) {
        if (mCharacterList == null) {
            mCharacterList = new ArrayList<D2Character>();
        } else {
            mCharacterList.clear();
        }
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                try {
                    int id = Integer.parseInt(cursor.getString(cursor
                            .getColumnIndex(D2Contract.Characters._ID)));
                    String name = cursor.getString(cursor
                            .getColumnIndex(D2Contract.Characters.NAME));
                    int level = Integer.parseInt(cursor.getString(cursor
                            .getColumnIndex(D2Contract.Characters.LEVEL)));
                    int charClass = Integer.parseInt(cursor.getString(cursor
                            .getColumnIndex(D2Contract.Characters.CLASS)));
                    int[] stats = new int[4];
                    stats[D2Character.INDEX_STR] = Integer
                            .parseInt(cursor.getString(cursor
                                    .getColumnIndex(D2Contract.Characters.STR)));
                    stats[D2Character.INDEX_DEX] = Integer
                            .parseInt(cursor.getString(cursor
                                    .getColumnIndex(D2Contract.Characters.DEX)));
                    stats[D2Character.INDEX_VIT] = Integer
                            .parseInt(cursor.getString(cursor
                                    .getColumnIndex(D2Contract.Characters.VIT)));
                    stats[D2Character.INDEX_ENE] = Integer
                            .parseInt(cursor.getString(cursor
                                    .getColumnIndex(D2Contract.Characters.ENE)));

                    mCharacterList.add(D2Character.createChar(id, name, level, charClass, stats));
                } catch (NumberFormatException e) {
                    return;
                }
            } while (cursor.moveToNext());
        }
        setListAdapter(new CharacterAdapter(getActivity()));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_CHARACTERS:
                return new CursorLoader(getActivity(),
                        D2Contract.Characters.CONTENT_URI, null, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        loadCharacters(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(long id);

        public void createNewChar();
    }

    public class CharacterAdapter extends ArrayAdapter<D2Character> {
        public CharacterAdapter(Context context) {
            super(context, R.layout.character_list_item, R.id.character_list_item_name, mCharacterList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (convertView == null) {
                v = LayoutInflater.from(getContext()).inflate(
                        R.layout.character_list_item, null);
            }

            ImageView icon = (ImageView) v.findViewById(R.id.character_list_item_char_icon);
            TextView name = (TextView) v
                    .findViewById(R.id.character_list_item_name);
            TextView charClass = (TextView) v
                    .findViewById(R.id.character_list_item_class);
            TextView level = (TextView) v
                    .findViewById(R.id.character_list_item_level);

            icon.setImageResource(R.drawable.ic_launcher);
            name.setText(mCharacterList.get(position).mName);
            charClass.setText(mCharacterList.get(position).mClass);
            level.setText("" + mCharacterList.get(position).mLevel);

            return v;
        }
    }
}
