package ca.yorku.eecs.mack.mygraffitilauncher;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class GraffitiWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Update all widgets
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Set up the remote views
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Set up the intent to handle drawing
        Intent intent = new Intent(context, WidgetDrawingActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // Set up the pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set up the click handler
        views.setOnClickPendingIntent(R.id.widget_drawing_area, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}