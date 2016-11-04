package com.dustykeyboard.bignerdranch.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.UUID;

import static android.R.attr.id;

/**
 * Created by jfredrickson on 10/22/2016.
 */

public class CrimeListFragment extends Fragment {
    private static final String KEY_LAST_CRIME_VIEWED = "last_crime_viewed";
    private static final int REQUEST_CODE_CRIME = 1;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int mLastViewed;

    @Override
    public void onResume() {
        super.onResume();
        //updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if( savedInstanceState != null) {
            mLastViewed = savedInstanceState.getInt(KEY_LAST_CRIME_VIEWED, 0); //default set to 0. Should this be -1?
        }

        updateUI();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_CODE_CRIME) {
                if(CrimeFragment.wasCrimeUpdated(data)) {
                    //updateUI
                    mAdapter.notifyItemChanged(mLastViewed);
                }
            }
        }
    }

    private void updateUI() {
        /** Why is this just not implemented within onCreateView?
         *  I do not see any code reuse.
         *   */
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            //mAdapter.notifyItemChanged(mLastViewed);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime; //why are were keeping a copy of crime? Seems like an extra variable.
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            UUID crimeId;

            crimeId = mCrime.getId();
            mLastViewed = mAdapter.getIndex(crimeId);
            Intent intent = CrimeActivity.newIntent(getActivity(),crimeId);
            startActivityForResult(intent, REQUEST_CODE_CRIME);
        }


    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public int getIndex(UUID crimeId) {
            int index = 0;
            for (Crime crime : mCrimes) {
                if (crime.getId().equals(crimeId)) {
                    return index;
                } else
                    index++;
            }
            return index;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
