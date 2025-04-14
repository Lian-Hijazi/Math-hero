package com.example.mathhero;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("NotificationReceiver", "Notification received");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "game_channel")
                .setSmallIcon(R.drawable.star)
                .setContentTitle("We miss you!")
                .setContentText("You've been away for a minute—ready to be a MathHero again?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}

