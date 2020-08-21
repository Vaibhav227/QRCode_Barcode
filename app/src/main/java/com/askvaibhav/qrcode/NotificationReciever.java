package com.askvaibhav.qrcode;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;


public class NotificationReciever extends BroadcastReceiver {

    @Override
        public void onReceive(Context context, Intent intent) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(context,SettingsActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //if we want ring on notification then uncomment below line//

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chan").
                    setSmallIcon(R.drawable.logoforapp_background).
                    setContentIntent(pendingIntent).
                    setContentText("Long time,No see.Try Scanning and generating Qr Code.").
                    setContentTitle("QR Code").
                setSound(alarmSound).
        setAutoCancel(true);
            notificationManager.notify(100,builder.build());

        }
    }

