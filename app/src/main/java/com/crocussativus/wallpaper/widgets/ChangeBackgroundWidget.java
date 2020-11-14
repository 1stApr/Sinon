package com.crocussativus.wallpaper.widgets;

import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crocussativus.wallpaper.MainActivity;
import com.crocussativus.wallpaper.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * Implementation of App Widget functionality.
 */
public class ChangeBackgroundWidget extends AppWidgetProvider {


    private static final String SYNC_CLICKED2 = "automaticWidgetSyncButtonClick";

    public static ArrayList<String> imageArrayList;
    public int position = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.change_background_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        ComponentName watchWidget;
        watchWidget = new ComponentName(context, ChangeBackgroundWidget.class);
        views.setOnClickPendingIntent(R.id.btn_change, getPendingSelfIntent(context, SYNC_CLICKED2));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, ChangeBackgroundWidget.class);
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
        Toast.makeText(context, "Enabled BGWidget",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "Disabled BGWidget",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (SYNC_CLICKED2.equals(intent.getAction())) {
            Toast.makeText(context, "Please Wait",
                    Toast.LENGTH_SHORT).show();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.change_background_widget);
            watchWidget = new ComponentName(context, ChangeBackgroundWidget.class);
            //Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/bg2");
            int size = MainActivity.imageArrayList.size();
            Random r = new Random();
            position = r.nextInt(size - 1) + 1;
            final Uri uri = Uri.parse(MainActivity.imageArrayList.get(position));
            //Log.d("Data: Link Bg: ",uri+"");
            //Log.d("Data: size-position: ",size+" "+position);
            final WallpaperManager myWallpaperManager = WallpaperManager.getInstance(context);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        myWallpaperManager.setBitmap(Glide.
                                with(context).
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
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }


}

