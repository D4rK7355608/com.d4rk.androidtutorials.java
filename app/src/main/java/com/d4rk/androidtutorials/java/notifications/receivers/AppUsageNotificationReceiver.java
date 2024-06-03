package com.d4rk.androidtutorials.java.notifications.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.d4rk.androidtutorials.java.notifications.workers.AppUsageNotificationWorker;

/**
 * A {@link BroadcastReceiver} that enqueues a {@link AppUsageNotificationWorker}
 * when it receives a broadcast.
 */
public class AppUsageNotificationReceiver extends BroadcastReceiver {

    /**
     * Receives the broadcast and enqueues the {@link AppUsageNotificationWorker}.
     *
     * @param context The context of the application.
     * @param intent  The intent that triggered the receiver.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(
                AppUsageNotificationWorker.class)
                .build();
        WorkManager.getInstance(context).enqueue(workRequest);
    }
}