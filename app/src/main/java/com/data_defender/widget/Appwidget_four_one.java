package com.data_defender.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.data_defender.Activity.Main;
import com.data_defender.data_defender.R;

/**
 * Implementation of App Widget functionality.
 */
public class Appwidget_four_one extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_four_one);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
        /************************
         * 위젯 클릭시 앱실행
         *************************/
        Intent intent =new Intent(context, Main.class);
        PendingIntent pe = PendingIntent.getActivities(context,0, new Intent[]{intent},0);
        views.setOnClickPendingIntent(R.id.imgbtn_widget,pe);
        /************************
         * 위젯 클릭시 앱실행 여기까지
         *************************/
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
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

