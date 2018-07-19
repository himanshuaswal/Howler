package com.techieaid.howler;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AlarmRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private ArrayList<String> alarms = new ArrayList<>();
    private PendingIntent mPendingIntent;
    private static final int REQUEST_CODE = 99;
    AlarmManager mAlarmManager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.custom_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        alarms = new ArrayList<>();
        alarms.add("04:30");
        alarms.add("05:00");
        alarms.add("05:30");
        alarms.add("06:00");
        alarms.add("19:00");
        mRecyclerView = findViewById(R.id.alarm_item);
        mRelativeLayout = findViewById(R.id.main_alarms_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlarmRecyclerViewAdapter(this, alarms);
        mRecyclerView.setAdapter(mAdapter);
        mFloatingActionButton = findViewById(R.id.add_alarm);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getFragmentManager(), "time_picker");
            }
        });
    }

    public void DisplayAlarmTime(int hourOfDay, int minute) {
        String setTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        mAdapter.setAlarmTime(setTime);
        long milliseconds = ( hourOfDay * 60 + minute) * 60000;
        Log.i("Milliseconds",String.valueOf(milliseconds));
        setAlarm(milliseconds);
    }

    private void setAlarm(long milliseconds) {
        Intent intent = new Intent(this, SnoozeActivity.class);
        mPendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,milliseconds,mPendingIntent);
        Snackbar snackbar = Snackbar.make(mRelativeLayout,"Alarm has been set",Snackbar.LENGTH_LONG);
        TextView messageTextView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        messageTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();
    }
}
