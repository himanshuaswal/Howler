package com.techieaid.howler;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
     private View mView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String timeset = String.format("%02d",hourOfDay)+":"+String.format("%02d",minute);
        ((MainActivity)getActivity()).SetAlarmData(timeset);

    }
}
