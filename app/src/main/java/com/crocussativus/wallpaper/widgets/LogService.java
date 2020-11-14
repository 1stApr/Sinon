package com.crocussativus.wallpaper.widgets;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.crocussativus.wallpaper.MainActivity;
import com.crocussativus.wallpaper.R;
import com.crocussativus.wallpaper.Restarter;
import com.crocussativus.wallpaper.SettingsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class LogService extends Service {

    public static int counter = 0;
    public static ArrayList<String> imageArrayList;
    Handler mHandler = new Handler();
    String NOTIFICATION_CHANNEL_ID = "meow.permanence";
    private Timer timer;
    private TimerTask timerTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        imageArrayList = new ArrayList<String>();
        imageArrayList = MainActivity.imageArrayList;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("App is running")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("App is running in background to update widget and get data from server."))
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                    .setAutoCancel(false)
                    .build();
            startForeground(1, notification);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("App is running in background to update widget and get data from server."))
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSmallIcon(R.drawable.ic_noti)
                .setAutoCancel(true)
                .setShowWhen(false)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build();
        startForeground(2, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                //Log.i("Count", "=========  "+ (counter++));

                int timeDelay = SettingsActivity.SyncFragment.getTimeDelay();

                if (counter % 30 == 0 && counter != 0) {
                    Log.i("Count", "======" + counter + "=====  ");
                    /*
                    BatteryManager bm = (BatteryManager) getBaseContext().getSystemService(BATTERY_SERVICE);
                    int batLevel = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    } else {
                        //batLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

                        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                        Intent batteryStatus = getBaseContext().registerReceiver(null, iFilter);

                        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
                        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

                        float batteryPct = level / (float) scale;

                        batLevel = (int) (batteryPct * 100);
                    }
                    Log.i("Data Battery: ",batLevel+"");
                     */
                }


                if (counter % (timeDelay*60) == 0 && counter != 0) {
                    if (SettingsActivity.SyncFragment.isAutoChangeBG()) {
                        //Log.i("Count", "====="+timeDelay+"=====  ");
                        int size = MainActivity.imageArrayList.size();
                        Random r = new Random();
                        int position = r.nextInt(size - 1) + 1;

                        final Uri uri = Uri.parse(MainActivity.imageArrayList.get(position));
                        Log.d("Data: Link Bg: ",uri+"");
                        Log.d("Data: size-position: ",size+" "+position);
                        final WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getBaseContext());

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    myWallpaperManager.setBitmap(Glide.
                                            with(getBaseContext()).
                                            load(uri).
                                            asBitmap().
                                            into(-1, -1).
                                            get());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                    }

                }
                counter++;

            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
