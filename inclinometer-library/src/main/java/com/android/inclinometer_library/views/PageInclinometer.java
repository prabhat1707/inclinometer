

package com.android.inclinometer_library.views;

import android.app.Activity;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.inclinometer_library.R;
import com.android.inclinometer_library.backgroundservice.HromatkaLog;
import com.android.inclinometer_library.backgroundservice.HromatkaServiceApi;
import com.android.inclinometer_library.backgroundservice.sensormanager.SensorApi;

import java.util.Locale;

public class PageInclinometer implements PageApi {
    private static final double DEG_TO_LEVEL = 10000.0f / 360.0f;
    private static final String CHAR_DEGREE = "\u00b0";
    private static TextView wPitchText = null;
    private static TextView wRollText = null;
    private static ImageView wPitchCompass = null;
    private static ImageView wRollCompass = null;
    private static PageInclinometer instance = null;
    private final String TAG = this.getClass().getSimpleName();
    private InclinometerListener inclinometerListener = new InclinometerListener();

    /**
     * Constructor - note this will force the class to be a singleton
     */
    protected PageInclinometer() {
    }

    /**
     * Public constructor.  Returns the instance of this singleton class.  This method will
     * create the instance if it doesn't exist.
     *
     * @return the instance of this class
     */
    public static PageInclinometer getInstance() {
        if (null == instance) {
            instance = new PageInclinometer();
        }

        return instance;
    }

    @Override
    public void onCreate(Activity activity, HromatkaServiceApi hromatkaServiceApi) {
        HromatkaLog.getInstance().enter(TAG);
        wPitchText = (TextView) activity.findViewById(R.id.wPitchText);
        wRollText = (TextView) activity.findViewById(R.id.wRollText);

        wPitchCompass = (ImageView) activity.findViewById(R.id.wPitchCompass);
        wRollCompass = (ImageView) activity.findViewById(R.id.wRollCompass);

        hromatkaServiceApi.registerInclinometerListener(inclinometerListener);
        HromatkaLog.getInstance().exit(TAG);
    }

    @Override
    public void onDestroy(Activity activity, HromatkaServiceApi hromatkaServiceApi) {
        HromatkaLog.getInstance().enter(TAG);
        hromatkaServiceApi.unregisterInclinometerListener(inclinometerListener);
        HromatkaLog.getInstance().exit(TAG);
    }

    private static class InclinometerListener implements SensorApi {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onDataReceived(long timestamp, float[] values) {
            HromatkaLog.getInstance().enter(TAG);
            wPitchText.setText(String.format(Locale.getDefault(), "%2.0f" + CHAR_DEGREE, values[0]));
            wRollText.setText(String.format(Locale.getDefault(), "%2.0f" + CHAR_DEGREE, Math.abs(values[1])));

            LayerDrawable lvPitch = (LayerDrawable) wPitchCompass.getDrawable();
            RotateDrawable pitchLayer = (RotateDrawable) lvPitch.getDrawable(1);
            pitchLayer.setLevel((int) (values[0] * DEG_TO_LEVEL));

            LayerDrawable lvRoll = (LayerDrawable) wRollCompass.getDrawable();
            RotateDrawable rollLayer = (RotateDrawable) lvRoll.getDrawable(1);
            rollLayer.setLevel((int) (values[1] * DEG_TO_LEVEL));

            HromatkaLog.getInstance().exit(TAG);
        }

        @Override
        public void onAccuracyChanged(int accuracy) {
            HromatkaLog.getInstance().enter(TAG);
            HromatkaLog.getInstance().exit(TAG);
        }
    }
}
