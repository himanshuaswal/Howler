package com.techieaid.howler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.techieaid.howler.model.Alarm;

import io.realm.Realm;
import io.realm.RealmResults;

public class SnoozeActivity extends AppCompatActivity {
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView option4;
    private AlarmManager alarmManager;
    private Realm realm;
    private int requestCode;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        realm = Realm.getDefaultInstance();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
    }

    private void checkAnswer(TextView correctOption) {
        if (correctOption.getText().toString().equalsIgnoreCase(getString(R.string.option1))) {
            Log.i("Selected Answer", correctOption.getText().toString());
            String setTime = getIntent().getStringExtra("Alarm time");
            Log.i("Retrieved time", setTime);
            realm.executeTransaction(realm -> {
                RealmResults<Alarm> results = realm.where(Alarm.class).equalTo("alarmTime", setTime).findAll();
                requestCode = results.first().getRequestCode();
                results.deleteAllFromRealm();
            });
            Log.i("Request Code", String.valueOf(requestCode));
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(getApplicationContext(), SnoozeActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), requestCode, myIntent, 0);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
            mediaPlayer.stop();
            Intent startAwakeActivity = new Intent(this, FinishActivity.class);
            //To navigate back to the main activity.
            TaskStackBuilder.create(this).addNextIntentWithParentStack(startAwakeActivity).startActivities();
        } else
            Toast.makeText(this, "You still aren't awake.", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Ohh I am gonna make sure that you wake up.!", Toast.LENGTH_LONG).show();
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox1:
                if (checked)
                    checkAnswer(option1);
                break;
            case R.id.checkbox2:
                if (checked)
                    checkAnswer(option2);
                break;
            case R.id.checkbox3:
                if (checked)
                    checkAnswer(option3);
                break;
            default:
                checkAnswer(option4);

        }
    }
}
