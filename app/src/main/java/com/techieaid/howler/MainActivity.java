package com.techieaid.howler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AlarmRecyclerViewAdapter mAdapter;
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
        alarms.add("08:30");
        alarms.add("22:00");
        alarms.add("00:00");
        alarms.add("11:40");
        alarms.add("01:00");
        mRecyclerView = findViewById(R.id.alarm_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlarmRecyclerViewAdapter(this,alarms);
        mRecyclerView.setAdapter(mAdapter);


    }
}
