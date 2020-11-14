package com.crocussativus.wallpaper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.crocussativus.wallpaper.widgets.LogService;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Log.i("Service", "Service tried to stop");
        Toast.makeText(context, "Service Restarted!", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, LogService.class));
        } else {
            context.startService(new Intent(context, LogService.class));
        }
    }
}
