package com.techieaid.howler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SnoozeActivity extends AppCompatActivity {
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView option4;
    private AlarmManager alarmManager;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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
        option1.setOnClickListener(v -> checkAnswer(option1));
        option2.setOnClickListener(v -> checkAnswer(option2));
        option3.setOnClickListener(v -> checkAnswer(option3));
        option4.setOnClickListener(v -> checkAnswer(option4));

    }

    private void checkAnswer(TextView correctOption) {
        Log.i("Selected Answer",correctOption.getText().toString());
        if (correctOption.getText().toString().equalsIgnoreCase(getString(R.string.option1))) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(getApplicationContext(), SnoozeActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 1, myIntent, 0);
            alarmManager.cancel(pendingIntent);
            mediaPlayer.stop();
        }
        else
            Toast.makeText(this,"That's an incorrect answer.",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Ohh I am gonna beat the shit out of you.!",Toast.LENGTH_LONG).show();
    }


}
