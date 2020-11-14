package com.crocussativus.wallpaper.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.crocussativus.wallpaper.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_widget);

        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/ic_settings_red_24dp");
        views.setImageViewUri(R.id.wid_image_setting, uri);
        Uri uri2 = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/bg2");
        views.setImageViewUri(R.id.image_widget, uri2);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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
        Toast.makeText(context, "Enabled ImageWidget",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "Disabled ImageWidget",
                Toast.LENGTH_SHORT).show();
    }
}

