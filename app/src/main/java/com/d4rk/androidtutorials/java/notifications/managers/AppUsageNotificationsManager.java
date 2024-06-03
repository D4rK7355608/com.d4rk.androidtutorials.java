package com.d4rk.androidtutorials.java.notifications.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.d4rk.androidtutorials.java.notifications.receivers.AppUsageNotificationReceiver;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for managing app usage notifications.
 * <p>
 * This class provides functionality to schedule periodic checks for app usage notifications
 * using {@link android.app.AlarmManager}.
 */
public class AppUsageNotificationsManager {

    private final AlarmManager alarmManager;
    private final PendingIntent notificationIntent;

    /**
     * Constructor for AppUsageNotificationsManager.
     *
     * @param context The application context used for scheduling app usage checks.
     */
    public AppUsageNotificationsManager(Context context) {
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.notificationIntent = PendingIntent.getBroadcast(
                context,
                0,
                new Intent(context, AppUsageNotificationReceiver.class),
                PendingIntent.FLAG_IMMUTABLE
        );
    }

    /**
     * Schedules a periodic check for app usage notifications.
     * <p>
     * This method schedules a repeating task using {@link android.app.AlarmManager} to perform app
     * usage checks every 3 days. It sets a repeating alarm that triggers an instance of the
     * {@link AppUsageNotificationReceiver} to handle the app usage check.
     */
    public void scheduleAppUsageCheck() {
        long triggerTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                TimeUnit.DAYS.toMillis(3),
                notificationIntent
        );
    }
}