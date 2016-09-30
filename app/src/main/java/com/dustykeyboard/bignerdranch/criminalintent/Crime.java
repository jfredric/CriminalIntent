package com.dustykeyboard.bignerdranch.criminalintent;

import java.util.UUID;

/**
 * Created by jfredrickson on 9/29/2016.
 */

public class Crime {
    private UUID mId;
    private String mTitle;

    public Crime() {
        //generate unique identifier
        mId = UUID.randomUUID();
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
}
