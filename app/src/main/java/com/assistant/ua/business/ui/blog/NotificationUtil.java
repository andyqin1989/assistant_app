package com.assistant.ua.business.ui.blog;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.SparseArray;
import androidx.core.app.NotificationCompat;
import com.assistant.ua.ASApp;
import com.assistant.ua.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by qinbaoyuan on 2018/11/19
 */
public class NotificationUtil {
    private static class Holder {
        private static NotificationUtil holder = new NotificationUtil();
    }

    public static NotificationUtil getInstance() {
        return Holder.holder;
    }

    private NotificationUtil() {
        manager = (NotificationManager) ASApp.instance.getSystemService(NOTIFICATION_SERVICE);
    }

    private NotificationManager manager;

    private SparseArray<NotificationCompat.Builder> notificationMap = new SparseArray<>();

    private NotificationCompat.Builder initNotification(Context context, String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("assistant", "小秘书", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
        }
        return new NotificationCompat.Builder(context, "assistant")
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.icon_new_version)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_portrait_default));
    }

    public void createApkDownLoadNotification(Context context, String title, String content, int notifyId) {
        NotificationCompat.Builder builder = initNotification(context, title, content);
        manager.notify(notifyId, builder.build());
        notificationMap.put(notifyId, builder);
    }

    public void updateNotification(int notifyId, float progress) {
        NotificationCompat.Builder builder = notificationMap.get(notifyId);
        builder.setProgress(100, (int) progress, false);
        builder.setContentText(progress + "%");
        manager.notify(notifyId, builder.build());
    }

    public void cancelNotification(int notifyId) {
        manager.cancel(notifyId);
        notificationMap.remove(notifyId);
    }
}
