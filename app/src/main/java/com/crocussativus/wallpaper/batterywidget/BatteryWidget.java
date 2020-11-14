package com.crocussativus.wallpaper.batterywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BatteryWidget extends AppWidgetProvider {

    /**
     * Get the number of widgets that have been enabled and placed on the home screen.
     *
     * @param context The application context.
     */
    public static int getNumberOfWidgets(final Context context) {
        ComponentName componentName = new ComponentName(context, BatteryWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] activeWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        if (activeWidgetIds != null) {
            return activeWidgetIds.length;
        } else {
            return 0;
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        context.startService(new Intent(context, MonitorService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager widgetManager, int[] widgetIds) {
        super.onUpdate(context, widgetManager, widgetIds);
        // ensure service is running
        context.startService(new Intent(context, MonitorService.class));
        // update the widgets
        Intent updateIntent = new Intent(context, UpdateService.class);
        updateIntent.setAction(UpdateService.ACTION_WIDGET_UPDATE);
        updateIntent.putExtra(UpdateService.EXTRA_WIDGET_IDS, widgetIds);
        context.startService(updateIntent);
    }

    @Override
    public void onDeleted(Context context, int[] widgetIds) {
        super.onDeleted(context, widgetIds);
        if (getNumberOfWidgets(context) == 0) {
            // stop monitoring if there are no more widgets on screen
            context.stopService(new Intent(context, MonitorService.class));
        }
    }

}
