package example.supervoo.notifymi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Member Variables
    private Button button_notify;
    private Button button_update;
    private Button button_cancel;
    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();

    // Constants
    private static final String PRIMARY_CHANNEL_ID = "primary_notifications_channel";
    private static final int NOTIFICATION_ID = 0;
    private static final String ACTION_UPDATE_NOTIFICATION =
            "example.supervoo.notifymi.ACTION_UPDATE_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        button_notify = findViewById(R.id.notifyMe);
        button_update = findViewById(R.id.updateMe);
        button_cancel = findViewById(R.id.cancelMe);
        setNotificationButtonState(true,false,false);

        // Create OnClick Listeners
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check sendNotification Method
                sendNotification();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check updateNotification Method
                updateNotification();
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check cancelNotification Method
                cancelNotification();
            }
        });
        // Intent Receiver
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
        createNotificationChannel();
    }

    @Override
    protected  void onDestroy(){
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled){
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    public void sendNotification() {
        // Set pending intent to update the notification
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        // Building the Notification
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Deliver notification
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // Setup button sequence eg. Enable or Disable
        setNotificationButtonState(false,true,true);

    }

    public void updateNotification() {
        // Set Image for Notification - Load Drawable Resource as Bitmap
        Bitmap androidImage =
                BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        // Build the Notification
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Update Notification to Big Picture Style
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage)
                .setBigContentTitle("Notification: Update")
                .setSummaryText("Check out this picture"));

        // Deliver the notification
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // Set Button Sequence
        setNotificationButtonState(false,false,true);
    }

    public void cancelNotification() {
        // Cancel Notification
        mNotifyManager.cancel(NOTIFICATION_ID);
        // Set Button Sequence
        setNotificationButtonState(true,false,false);
    }

    // Create Notification Channel for Android Oreo 8.0+ API 26+ and Above
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Check system Android API level
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Initialize Notification Channel
            NotificationChannel notificationChannel =
                    new NotificationChannel(PRIMARY_CHANNEL_ID,
                            "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            // Notification Channel Configurations
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            // Create the Notification Channel
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    // Create Notification for Android API lower then 26 + etc
    private NotificationCompat.Builder getNotificationBuilder() {
        // Setting explicit Intent
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent =
                PendingIntent.getActivity(this, NOTIFICATION_ID,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build Notification
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("Your Notification Title")
                        .setContentText("This is your notification text")
                        .setSmallIcon(R.drawable.ic_android)
                        // Notification Priority Configs
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        // Intent Configuration
                        .setContentIntent(notificationPendingIntent)
                        .setAutoCancel(true);
        return notifyBuilder;
    }

    public class NotificationReceiver extends BroadcastReceiver{
        public NotificationReceiver(){

        }

        @Override
        public void onReceive(Context c, Intent i){
            // Update the Notification
            updateNotification();
        }
    }

    public void notifyMe(View view) {
        // Do extra stuff or Remove Method
    }

    public void updateMe(View view) {
        // Do extra stuff or Remove Method
    }

    public void cancelMe(View view) {
        // Do extra stuff or Remove Method
    }
}
