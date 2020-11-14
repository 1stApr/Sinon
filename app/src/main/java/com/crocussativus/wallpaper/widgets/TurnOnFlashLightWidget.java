package com.crocussativus.wallpaper.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.crocussativus.wallpaper.R;


/**
 * Implementation of App Widget functionality.
 */
public class TurnOnFlashLightWidget extends AppWidgetProvider {


    private static final String SYNC_CLICKED = "automaticWidgetSyncButtonClick";
    public static int lightOn = 0;
    public static Camera cam = null;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.turn_on_flash_light_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        ComponentName watchWidget;
        watchWidget = new ComponentName(context, TurnOnFlashLightWidget.class);
        views.setOnClickPendingIntent(R.id.btn_on_flash_light, getPendingSelfIntent(context, SYNC_CLICKED));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, TurnOnFlashLightWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context, "Enabled FlashWidget",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "Disabled FlashWidget",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (SYNC_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.turn_on_flash_light_widget);
            watchWidget = new ComponentName(context, TurnOnFlashLightWidget.class);

            if (lightOn == 0) {
                try {
                    if (context.getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA_FLASH)) {
                        cam = Camera.open();
                        Camera.Parameters p = cam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        cam.setParameters(p);
                        cam.startPreview();
                        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_flash_light_yellow_64");
                        remoteViews.setImageViewUri(R.id.btn_on_flash_light, uri);
                        Toast.makeText(context, "Light On", Toast.LENGTH_SHORT).show();
                        lightOn = 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Exception 1\n" + e,
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                try {
                    if (context.getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA_FLASH)) {
                        cam.stopPreview();
                        cam.release();
                        cam = null;
                        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_flash_light_64");
                        remoteViews.setImageViewUri(R.id.btn_on_flash_light, uri);
                        Toast.makeText(context, "Light Off", Toast.LENGTH_SHORT).show();
                        lightOn = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Exception 2\n" + e,
                            Toast.LENGTH_SHORT).show();
                }
            }
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }

}

