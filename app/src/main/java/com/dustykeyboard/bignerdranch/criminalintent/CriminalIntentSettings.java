package com.dustykeyboard.bignerdranch.criminalintent;

import android.content.Context;

/**
 * Created by jfredrickson on 3/16/2017.
 *
 * This works but not the point of the lesson (chapter 13) so it is not current being used.
 */

public class CriminalIntentSettings {
    private static CriminalIntentSettings sCriminalIntentSettings;

    private static Boolean mSubtitleVisible;

    public static CriminalIntentSettings get(Context context) {
        if(sCriminalIntentSettings == null) {
            sCriminalIntentSettings = new CriminalIntentSettings(context);
        }
        return sCriminalIntentSettings;
    }

    private CriminalIntentSettings(Context context) {
        mSubtitleVisible = false;
    }

    public void setSubtitleVisible(boolean isVisible) {
        mSubtitleVisible = isVisible;
    }

    public boolean isSubtitleVisible() {
        return mSubtitleVisible;
    }
}
