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
import android.widget.TextView;

import com.techieaid.howler.model.Alarm;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AlarmRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private PendingIntent mPendingIntent;
    private int REQUEST_CODE;
    private Realm realm;
    AlarmManager mAlarmManager;
    private CoordinatorLayout mCoordinatorLayout;
    private RealmResults<Alarm> results;
    private int PRIMARY_KEY;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.custom_toolbar));
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        realm.executeTransaction(realm -> {
            results = realm.where(Alarm.class).findAll();
        });
        mRecyclerView = findViewById(R.id.alarm_item);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlarmRecyclerViewAdapter(this, results, realm);
        mRecyclerView.setAdapter(mAdapter);
        mFloatingActionButton = findViewById(R.id.add_alarm);
        mFloatingActionButton.setOnClickListener(v -> {
            DialogFragment timeFragment = new TimePickerFragment();
            timeFragment.show(getFragmentManager(), "time_picker");
        });
    }

    public void DisplayAlarmTime(int hourOfDay, int minute) {
        String setTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        setAlarm(hourOfDay, minute, setTime);
    }

    private void setAlarm(int hourOfDay, int minute, String setTime) {
        Intent intent = new Intent(getApplicationContext(), SnoozeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Alarm time", setTime);
        int numberOfAlarms = (int) realm.where(Alarm.class).count();
        REQUEST_CODE = numberOfAlarms + 1;
        PRIMARY_KEY = numberOfAlarms + 100;
        Log.i("Request Code", String.valueOf(REQUEST_CODE));
        mPendingIntent = PendingIntent.getActivity(getApplicationContext(), REQUEST_CODE, intent, 0);
        realm.executeTransaction((Realm realm) -> {
            alarm = realm.createObject(Alarm.class, PRIMARY_KEY);
            alarm.setAlarmTime(setTime);
            alarm.setRequestCode(REQUEST_CODE);
        });
        mAdapter.updateAdapter(alarm);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), mPendingIntent);
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, "Alarm has been set", Snackbar.LENGTH_LONG);
        TextView messageTextView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        messageTextView.setTextColor(getColor(R.color.colorAccent));
        snackbar.show();
    }
}
