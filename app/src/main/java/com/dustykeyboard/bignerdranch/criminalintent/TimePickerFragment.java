package com.dustykeyboard.bignerdranch.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jfredrickson on 2/27/2017.
 */

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_DATE_TIME = "time";
    public static final String EXTRA_DATE_TIME = "com.dustykeyboard.bignerdranch.criminalintent.time";

    private TimePicker mTimePicker;
    private Calendar mCalendar;

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return; //not sure why this is needed
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE_TIME, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, intent);
    }

    /** Construct a new DatePickerFragment with an initial Date
     *
     * @param date
     * @return
     */
    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE_TIME);

        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        mTimePicker.setHour(mCalendar.get(Calendar.HOUR_OF_DAY)); //time picker needs to be set using 24 hour value
        mTimePicker.setMinute(mCalendar.get(Calendar.MINUTE));

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hour = mTimePicker.getHour(); // 24 hour
                                int min = mTimePicker.getMinute();
                                Date date = new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), hour, min).getTime();

                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                .create();

    }
}
