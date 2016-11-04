package com.dustykeyboard.bignerdranch.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CheckBox;

import java.util.UUID;

/**
 * Created by jfredrickson on 9/29/2016.
 */

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String EXTRA_WAS_UPDATED = "com.dustykeyboard.bignerdranch.criminalintent.was_updated";
    private static final String KEY_UPDATE_STATUS = "udpate_status";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private boolean mWasUpdated;

    public static CrimeFragment newInstance(UUID crimeID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeID = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeID);


        if (savedInstanceState != null) {
            mWasUpdated = savedInstanceState.getBoolean(KEY_UPDATE_STATUS, false); //recover udpate state
            setUpdateStatus(mWasUpdated); //set update state
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_UPDATE_STATUS, mWasUpdated); //save update state
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                setUpdateStatus(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false); //temporary state

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set the crimes Solved status
                mCrime.setSolved(isChecked);
                setUpdateStatus(true);
            }
        });

        return v;
    }

    private void setUpdateStatus(boolean wasUpdated) {
        Intent data = new Intent();
        data.putExtra(EXTRA_WAS_UPDATED, wasUpdated);
        getActivity().setResult(Activity.RESULT_OK, data);
        mWasUpdated = wasUpdated;
    }

    public static boolean wasCrimeUpdated(Intent result) {
        //return update status
        return result.getBooleanExtra(EXTRA_WAS_UPDATED, false);
    }
}
