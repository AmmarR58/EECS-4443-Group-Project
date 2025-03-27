package ca.yorku.eecs.mack.mygraffitilauncher;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;

public class WidgetDrawingActivity extends Activity implements OnGesturePerformedListener {

    private GestureLibrary gestureLibrary;
    private int appWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_drawing_activity);

        // Get the widget ID
        Intent intent = getIntent();
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        // Load gesture library
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLibrary.load()) {
            finish();
        }

        // Set up gesture overlay
        GestureOverlayView gestureOverlay = findViewById(R.id.gesture_overlay);
        gestureOverlay.addOnGesturePerformedListener(this);
        gestureOverlay.setGestureColor(Color.TRANSPARENT);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);

        // Check if gesture is recognized
        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String gestureName = predictions.get(0).name;

            // Return result to widget
            Intent result = new Intent();
            result.putExtra("GESTURE_NAME", gestureName);
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, result);
        } else {
            Toast.makeText(this, "Gesture not recognized", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
