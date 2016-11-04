package pialkanti.com.libraryreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Pial on 10/28/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent parentIntent = new Intent(context,UserHomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,parentIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("KUET Library Reminder")
                .setContentText("You have a book to renew.")
                .setTicker("Hurry")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .addAction(R.mipmap.show_details,"Show details",pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.person))
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
}
