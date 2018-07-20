package com.techieaid.howler;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techieaid.howler.model.Alarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AlarmRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private ArrayList<String> alarms = new ArrayList<>();
    private PendingIntent mPendingIntent;
    private int REQUEST_CODE;
    private Realm realm;
    AlarmManager mAlarmManager;
    private RelativeLayout mRelativeLayout;
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
        alarms = new ArrayList<>();
        alarms.add("04:30");
        alarms.add("05:00");
        alarms.add("05:30");
        alarms.add("06:00");
        alarms.add("19:00");
        realm.executeTransaction(realm -> {
            results = realm.where(Alarm.class).findAll();
        });
        mRecyclerView = findViewById(R.id.alarm_item);
        mRelativeLayout = findViewById(R.id.main_alarms_layout);
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
        Log.i("Selected Hour Time", "Hour of Day ::" + hourOfDay);
        Log.i("System Minute Time", "Minute of Day ::" + minute);
        String setTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        long milliseconds = (hourOfDay * 60 + minute) * 60000;
        Log.i("Milliseconds", "Date in milli ::" + milliseconds);
        Log.i("System Time", "System time ::" + System.currentTimeMillis());
        setAlarm(hourOfDay, minute, setTime);
    }

    private void setAlarm(int hourOfDay, int minute, String setTime) {
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        int numberOfAlarms = (int) realm.where(Alarm.class).count();
        REQUEST_CODE = numberOfAlarms + 1;
        PRIMARY_KEY = numberOfAlarms + 100;
        mPendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQUEST_CODE, intent, 0);
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
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), mPendingIntent);
        Snackbar snackbar = Snackbar.make(mRelativeLayout, "Alarm has been set", Snackbar.LENGTH_LONG);
        TextView messageTextView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        messageTextView.setTextColor(getColor(R.color.colorAccent));
        snackbar.show();
    }
}
