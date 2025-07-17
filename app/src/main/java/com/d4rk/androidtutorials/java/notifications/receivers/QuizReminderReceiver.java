package com.d4rk.androidtutorials.java.notifications.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.d4rk.androidtutorials.java.notifications.workers.QuizReminderWorker;

/**
 * BroadcastReceiver that enqueues a {@link QuizReminderWorker}.
 */
public class QuizReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(
                QuizReminderWorker.class)
                .build();
        WorkManager.getInstance(context).enqueue(request);
    }
}
