package com.d4rk.androidtutorials.java.notifications;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import com.d4rk.androidtutorials.java.R;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
public class AppUpdateNotificationsManager {
    private final Context context;
    private final String updateChannelId = "update_channel";
    private final int updateNotificationId = 0;
    public AppUpdateNotificationsManager(Context context) {
        this.context = context;
    }
    public void checkAndSendUpdateNotification() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        AppUpdateManagerFactory.create(context).getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                NotificationChannel updateChannel = new NotificationChannel(updateChannelId, context.getString(R.string.update_notifications), NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(updateChannel);
                NotificationCompat.Builder updateBuilder = new NotificationCompat.Builder(context, updateChannelId)
                        .setSmallIcon(R.drawable.ic_notification_update)
                        .setContentTitle(context.getString(R.string.notification_update_title))
                        .setContentText(context.getString(R.string.summary_notification_update))
                        .setAutoCancel(true)
                        .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())), PendingIntent.FLAG_IMMUTABLE));
                notificationManager.notify(updateNotificationId, updateBuilder.build());
            }
        });
    }
}