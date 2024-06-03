package com.d4rk.androidtutorials.java.notifications;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.core.app.NotificationCompat;
import com.d4rk.androidtutorials.java.R;
public class AppUsageNotificationsManager {
    private final Context context;
    public AppUsageNotificationsManager(Context context) {
        this.context = context;
    }
    public void checkAndSendAppUsageNotification() {
        SharedPreferences preferences = context.getSharedPreferences("app_usage", Context.MODE_PRIVATE);
        long lastUsedTimestamp = preferences.getLong("last_used", 0);
        long currentTimestamp = System.currentTimeMillis();
        long notificationThreshold = 3 * 24 * 60 * 60 * 1000;
        if (currentTimestamp - lastUsedTimestamp > notificationThreshold) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String appUsageChannelId = "app_usage_channel";
            NotificationChannel appUsageChannel = new NotificationChannel(appUsageChannelId, context.getString(R.string.app_usage_notifications), NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(appUsageChannel);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, appUsageChannelId)
                    .setSmallIcon(R.drawable.ic_notification_important)
                    .setContentTitle(context.getString(R.string.notification_last_time_used_title))
                    .setContentText(context.getString(R.string.summary_notification_last_time_used))
                    .setAutoCancel(true);
            int appUsageNotificationId = 0;
            notificationManager.notify(appUsageNotificationId, notificationBuilder.build());
        }
        preferences.edit().putLong("last_used", currentTimestamp).apply();
    }
}