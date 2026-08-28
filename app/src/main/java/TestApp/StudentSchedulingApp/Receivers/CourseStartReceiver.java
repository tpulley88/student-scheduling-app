package TestApp.StudentSchedulingApp.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import TestApp.StudentSchedulingApp.R;

public class CourseStartReceiver extends BroadcastReceiver {

    String channel_id="start_course";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"You have a course starting today.",Toast.LENGTH_LONG).show();
        createNotificationChannel(context,channel_id);
        Notification n=new NotificationCompat.Builder(context,channel_id)
                .setSmallIcon(R.mipmap.ic_launcher_icon_round)
                .setContentText((intent.getStringExtra("Course Title") + " starts today."))
                .setContentTitle("Starting Course").build();
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}