package com.techieaid.howler;

import android.app.DialogFragment;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AlarmRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private ArrayList<String> alarms = new ArrayList<>();

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlarmRecyclerViewAdapter(this,alarms);
        mRecyclerView.setAdapter(mAdapter);
        mFloatingActionButton=findViewById(R.id.add_alarm);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getFragmentManager(),"time_picker");
            }
        });


    }

    public void DisplayAlarmTime(String time){
        mAdapter.setAlarmTime(time);
        Log.i("Selected Alarm",time);
    }
}
