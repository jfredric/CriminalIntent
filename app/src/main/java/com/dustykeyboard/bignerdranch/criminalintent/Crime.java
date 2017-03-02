package com.dustykeyboard.bignerdranch.criminalintent;

import java.util.UUID;
import java.util.Date;

/**
 * Created by jfredrickson on 9/29/2016.
 */

public class Crime {
    private UUID mId;
    private String mTitle;

    private Date mDate;
    private boolean mSolved;

    public Crime() {
        //generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date(); //default contructor used for current date. Used to autofill
       //should this not have... mSolved = false;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDate() {

        return mDate;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean isSolved() {
        return mSolved;
    }
}
