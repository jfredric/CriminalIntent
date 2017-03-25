package com.dustykeyboard.bignerdranch.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;


/**
 * Created by jfredrickson on 12/6/2016.
 */

public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.dustykeyboard.bignerdranch.criminalintent.crime_id";
    private static final String EXTRA_SUBTITLE_VISIBLE = "com.dustykeyboard.bignerdranch.criminalintent.SUBTITLE_VISIBLE";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private Boolean mSubtitleVisible;

    public static Intent newIntent(Context packageContext, UUID crimeId, Boolean subtitleVisible) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        intent.putExtra(EXTRA_SUBTITLE_VISIBLE, subtitleVisible);
        return intent;
    }

    @Override
    public Intent getParentActivityIntent() {
        Intent parentIntent = CrimeListFragment.saveSubtitleVisible(super.getParentActivityIntent(), mSubtitleVisible);

        return parentIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        Intent intent = getIntent();
        UUID crimeId = (UUID) intent.getSerializableExtra(EXTRA_CRIME_ID);
        mSubtitleVisible = (Boolean) intent.getSerializableExtra(EXTRA_SUBTITLE_VISIBLE);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if(mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
