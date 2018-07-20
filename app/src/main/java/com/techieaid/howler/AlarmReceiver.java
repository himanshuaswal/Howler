package com.techieaid.howler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * @Override protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * setContentView(R.layout.activity_snooze);
     * }
     **/

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MediaPlayer mp = MediaPlayer.create(context, uri);
        mp.setLooping(true);
        mp.start();
        Intent startSnoozeActivity =  new Intent(context,SnoozeActivity.class);
        startSnoozeActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startSnoozeActivity);
    }

}
