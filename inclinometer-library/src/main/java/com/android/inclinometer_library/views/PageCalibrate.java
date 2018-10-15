

package com.android.inclinometer_library.views;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.inclinometer_library.backgroundservice.HromatkaLog;
import com.android.inclinometer_library.backgroundservice.HromatkaServiceApi;
import com.android.inclinometer_library.backgroundservice.sensormanager.SensorApi;


public class PageCalibrate implements PageApi {
    private static PageCalibrate instance = null;
    private final String TAG = this.getClass().getSimpleName();
    private Button wSetOffsetsButton = null;
    private InclinometerListener inclinometerListener = new InclinometerListener();

    /**
     * Constructor - note this will force the class to be a singleton
     */
    protected PageCalibrate() {
    }

    /**
     * Public constructor.  Returns the instance of this singleton class.  This method will
     * create the instance if it doesn't exist.
     *
     * @return the instance of this class
     */
    public static PageCalibrate getInstance() {
        if (null == instance) {
            instance = new PageCalibrate();
        }

        return instance;
    }

    public void onCreate(final Activity activity, final HromatkaServiceApi hromatkaServiceApi) {
        HromatkaLog.getInstance().enter(TAG);
        wSetOffsetsButton = (Button) activity.findViewById(R.id.wSetOffsetsButton);
        wSetOffsetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HromatkaLog.getInstance().logVerbose(TAG, "Set offsets button pressed.");
                hromatkaServiceApi.updateInclinometerOffsets();

                Toast.makeText(
                        activity,
                        activity.getString(R.string.toast_calibration_complete),
                        Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });

        hromatkaServiceApi.registerInclinometerListener(inclinometerListener);
        HromatkaLog.getInstance().exit(TAG);
    }

    @Override
    public void onDestroy(Activity activity, HromatkaServiceApi hromatkaServiceApi) {
        HromatkaLog.getInstance().enter(TAG);
        hromatkaServiceApi.unregisterInclinometerListener(inclinometerListener);
        HromatkaLog.getInstance().exit(TAG);
    }

    /** We need to implement an inclinometer listener here so that the accelerometer
     * sensor is running.  This will allow us to compute the average acclerometer offset.
     */
    private static class InclinometerListener implements SensorApi {
        private final String TAG = this.getClass().getSimpleName();

        @Override
        public void onDataReceived(long timestamp, float[] values) {
            HromatkaLog.getInstance().enter(TAG);
            HromatkaLog.getInstance().exit(TAG);
        }

        @Override
        public void onAccuracyChanged(int accuracy) {
            HromatkaLog.getInstance().enter(TAG);
            HromatkaLog.getInstance().exit(TAG);
        }
    }
}
