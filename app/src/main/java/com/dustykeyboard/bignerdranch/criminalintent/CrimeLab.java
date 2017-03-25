package com.dustykeyboard.bignerdranch.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by jfredrickson on 10/17/2016.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        /** for(int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(crime);
        }**/
    }



    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    /**public void deleteCrime(UUID id) {
        Iterator<Crime> iterator = mCrimes.listIterator();
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                mCrimes.remove(crime);
            }
        }
    }**/

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
