package com.suicune.d2tools.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.*;
import android.widget.*;
import com.suicune.d2tools.D2Character;
import com.suicune.d2tools.D2Skill;
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
        LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final int LOADER_CHARACTER = 1;
    private static final int LOADER_SKILLS = 2;
    EditText mNameView;
    Spinner mClassView;
    EditText mLevelView;
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
        } else {
            //If no argument was passed, a new Character should be created.
            createNewChar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character_detail,
                container, false);

        mNameView = (EditText) rootView.findViewById(R.id.char_detail_name);
        mLevelView = (EditText) rootView.findViewById(R.id.char_detail_level);
        mClassView = (Spinner) rootView.findViewById(R.id.char_detail_class);

        prepareClassAdapter();

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
        getLoaderManager().restartLoader(LOADER_CHARACTER, getArguments(), this);
    }

    private void createNewChar() {
        mItem = D2Character.createChar("", 1, R.string.sorceress);
        showCharacterInfo();
    }

    private void prepareClassAdapter(){
        mClassView.setAdapter(null);
        mClassView.setOnItemSelectedListener(this);
    }

    private void copyFrom() {
        // TODO Auto-generated method stub

    }

    private void delete() {
        if (mItem != null) {
            String selection = D2Contract.Characters._ID + "=?";
            String[] selectionArgs = {"" + mItem.mId};

            getActivity().getContentResolver().delete(D2Contract.Characters.CONTENT_URI, selection,
                    selectionArgs);
            selection = D2Contract.Skills.CHARACTER + "=?";
            getActivity().getContentResolver().delete(D2Contract.Skills.CONTENT_URI, selection,
                    selectionArgs);
        }
    }

    private void save() {
        if (mItem != null) {
            mItem.mName = mNameView.getText().toString();
            try {
                mItem.mLevel = Integer.parseInt(mLevelView.getText().toString());
                mItem.mClass = getCharacterClass(mClassView.getSelectedItemPosition());
            } catch (NumberFormatException e) {
            }
            mItem.save(getActivity());
        }
    }

    private void showCharacterInfo() {
        if (mItem != null) {
            mNameView.setText(mItem.mName);
            mClassView.setSelection(getPosition());
            mLevelView.setText("" + mItem.mLevel);
        }
        // TODO Auto-generated method stub
    }

    private int getPosition() {
        switch (mItem.mClass) {
            case R.string.sorceress:
                return 0;
            case R.string.necromancer:
                return 1;
            case R.string.amazon:
                return 2;
            case R.string.paladin:
                return 3;
            case R.string.barbarian:
                return 4;
            case R.string.assassin:
                return 5;
            case R.string.druid:
                return 6;
            default:
                return 0;
        }
    }

    private int getCharacterClass(int position) {
        switch (position) {
            case 0:
                return R.string.sorceress;
            case 1:
                return R.string.necromancer;
            case 2:
                return R.string.amazon;
            case 3:
                return R.string.paladin;
            case 4:
                return R.string.barbarian;
            case 5:
                return R.string.assassin;
            case 6:
                return R.string.druid;
            default:
                return 0;
        }
    }

    private void loadSkills() {
        getLoaderManager().restartLoader(LOADER_SKILLS, getArguments(), this);
    }

    private void showSkills(Cursor c) {
        if (c.moveToFirst()) {
            do {
                mItem.addSkill(c.getString(c.getColumnIndex(D2Contract.Skills.SKILL_NAME)),
                        Integer.parseInt(c.getString(c.getColumnIndex(
                                D2Contract.Skills.SKILL_LEVEL))));
            } while (c.moveToNext());
        }
        ListView skillListView = (ListView) getActivity().findViewById(R.id.char_detail_skills);
        skillListView.setAdapter(new SkillAdapter(getActivity()));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (!args.containsKey(ARG_ITEM_ID)) {
            Toast.makeText(getActivity(), "NORL", Toast.LENGTH_LONG).show();
            return null;
        }

        String selection;
        String[] selectionArgs = {args.getString(ARG_ITEM_ID)};

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
                    }
                    break;
                case LOADER_SKILLS:
                    if (cursor.getCount() > 0) {
                        showSkills(cursor);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if(view.getId() == R.id.char_detail_class){
            mItem.mClass = getCharacterClass(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class SkillAdapter extends ArrayAdapter<D2Skill> {
        public SkillAdapter(Context context) {
            super(context, R.id.skill_list_item_name);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.skill_list_item, null);
            }

            TextView skillName = (TextView) view.findViewById(R.id.skill_list_item_name);
            TextView skillLevel = (TextView) view.findViewById(R.id.skill_list_item_level);
            skillName.setText(mItem.mSkillList.get(position).mName);
            skillLevel.setText("" + mItem.mSkillList.get(position).mLevel);

            return view;
        }
    }
}
