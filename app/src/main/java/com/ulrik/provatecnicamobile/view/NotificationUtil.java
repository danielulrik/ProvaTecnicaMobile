package com.ulrik.provatecnicamobile.view;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.ulrik.provatecnicamobile.R;

public final class NotificationUtil {

    private NotificationUtil() {
    }

    public static void makeFinishedNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "main");
        mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle("Concluído")
                .setContentText("Sincronismo realizado com sucesso.")
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.stat_sys_download_done)).build();
        assert notificationManager != null;
        notificationManager.notify(notificationId, mBuilder.build());
    }

    public static void makeProgressNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "main");
        mBuilder.setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setContentTitle(context.getString(R.string.text_aguarde))
                .setContentText(context.getString(R.string.text_sincronizando))
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.stat_sys_download)).build();
        mBuilder.setProgress(0, 0, true);
        assert notificationManager != null;
        notificationManager.notify(notificationId, mBuilder.build());
    }

    public static void cancelNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancel(notificationId);
    }

}
