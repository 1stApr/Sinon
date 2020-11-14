package com.crocussativus.wallpaper.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.crocussativus.wallpaper.R;


/**
 * Implementation of App Widget functionality.
 */


public class DecreaseVolumeWidget extends AppWidgetProvider {

    private static final String SYNC_CLICKED_DOWN_VOLUME = "automaticWidgetSyncButtonDownVolumeClick";
    private static final String SYNC_CLICKED_UP_VOLUME = "automaticWidgetSyncButtonUPVolumeClick";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.decrease_volume_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget

        ComponentName watchWidget;
        watchWidget = new ComponentName(context, DecreaseVolumeWidget.class);
        views.setOnClickPendingIntent(R.id.btn_down_volume, getPendingSelfIntent(context, SYNC_CLICKED_DOWN_VOLUME));
        views.setOnClickPendingIntent(R.id.btn_up_volume, getPendingSelfIntent(context, SYNC_CLICKED_UP_VOLUME));
        appWidgetManager.updateAppWidget(watchWidget, views);
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, DecreaseVolumeWidget.class);
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
        Toast.makeText(context, "Enabled DecreaseVolumeWidget",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context, "Disabled DecreaseVolumeWidget",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (SYNC_CLICKED_DOWN_VOLUME.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews;
            ComponentName watchWidget;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.decrease_volume_widget);
            watchWidget = new ComponentName(context, DecreaseVolumeWidget.class);
            AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            //To decrease media player volume

            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_MUSIC);

            int volume_level = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = (float) volume_level / maxVolume;
            Toast.makeText(context, "Volume: " + (int) (volume * 100) + "%", Toast.LENGTH_SHORT).show();

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
        if (SYNC_CLICKED_UP_VOLUME.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remoteViews;
            ComponentName watchWidget;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.decrease_volume_widget);
            watchWidget = new ComponentName(context, DecreaseVolumeWidget.class);
            AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            //To decrease media player volume

            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

            int volume_level = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = (float) volume_level / maxVolume;
            Toast.makeText(context, "Volume: " + (int) (volume * 100) + "%", Toast.LENGTH_SHORT).show();

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }


}

