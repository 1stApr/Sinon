package com.crocussativus.wallpaper.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.RemoteViews;

import com.crocussativus.wallpaper.LunarCalender;
import com.crocussativus.wallpaper.R;

import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.BATTERY_SERVICE;

/**
 * Implementation of App Widget functionality.
 */
public class LunarDayWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lunar_day_widget);
        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        int mYear;
        int mDate;
        int mMonth;
        int dayOfWeek;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;
        mDate = calendar.get(Calendar.DATE);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String month = "00";
        if (mMonth < 10) {
            month = "0" + mMonth;
        } else {
            month = mMonth + "";
        }
        LunarCalender lunarCalender = new LunarCalender(mDate, mMonth, mYear);
        views.setTextViewText(R.id.tv_pin_level, "T." + dayOfWeek + ", " + mDate + " tháng " + month);
        views.setTextViewText(R.id.tv_lunar_day, lunarCalender.getmLunarDay() + "");
        views.setTextViewText(R.id.tv_lunar_month, "Tháng " + lunarCalender.getmLunarMonth());

        //Log.d("Date: ",mDate+":"+mMonth+":"+mYear);
        //Log.d("Date: Luner ","T."+dayOfWeek+", "+lunarCalender.getmLunarDay()+":"+lunarCalender.getmLunarMonth()+":"+lunarCalender.getmLunarYear());

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

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

}

