package com.dustykeyboard.bignerdranch.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by jfredrickson on 10/22/2016.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
