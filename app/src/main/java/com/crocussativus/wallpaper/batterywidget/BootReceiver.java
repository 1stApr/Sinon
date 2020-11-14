package com.crocussativus.wallpaper.batterywidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BatteryWidget.getNumberOfWidgets(context) > 0) {
            // ensure service is running
            context.startService(new Intent(context, MonitorService.class));
        }
    }
}
