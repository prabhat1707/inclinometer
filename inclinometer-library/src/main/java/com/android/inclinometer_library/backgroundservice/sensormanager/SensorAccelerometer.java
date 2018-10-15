

package com.android.inclinometer_library.backgroundservice.sensormanager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.android.inclinometer_library.backgroundservice.HromatkaLog;


public class SensorAccelerometer extends AbstractSensor implements SensorEventListener {
    private static SensorAccelerometer instance = null;
    private final String TAG = this.getClass().getSimpleName();

    /**
     * Constructor - note this will force the class to be a singleton
     */
    protected SensorAccelerometer() {
    }

    /**
     * Public constructor.  Returns the instance of this singleton class.  This method will
     * create the instance if it doesn't exist.
     *
     * @return the instance of this class
     */
    public static SensorAccelerometer getInstance() {
        if (null == instance) {
            instance = new SensorAccelerometer();
        }

        return instance;
    }

    /**
     * Enable the accelerometer sensor.
     *
     * @param sensorManager An instance of the Android SensorManager
     */
    @Override
    protected void enableSensor(SensorManager sensorManager) {
        HromatkaLog.getInstance().enter(TAG);

        Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * Disable the accelerometer sensor.
     *
     * @param sensorManager An instance of the Android SensorManager
     */
    @Override
    protected void disableSensor(SensorManager sensorManager) {
        HromatkaLog.getInstance().enter(TAG);
        sensorManager.unregisterListener(this);
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * Destroy the accelerometer sensor.
     *
     * @param sensorManager An instance of the Android SensorManager
     */
    @Override
    public void destroySensor(SensorManager sensorManager) {
        super.destroySensor(sensorManager);

        HromatkaLog.getInstance().enter(TAG);
        disableSensor(sensorManager);
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * This class's listener for new sensor data from the internal Android accelerometer
     * implementation.  Required via the SensorEventListener implementation.
     *
     * @param event SensorEvent data from Android
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        HromatkaLog.getInstance().enter(TAG);
        Log.v(TAG, "accel x,y,z = " + event.values[0] + ", " + event.values[1] + ", " + event.values[2]);

        /**
         * SensorAcclerometer generates the values[] array for onDataReceived() as follows:
         * 0 == x accelerometer measurement (m/s^2)
         * 1 == y accelerometer measurement (m/s^2)
         * 2 == z accelerometer measurement (m/s^2)
         */
        notifyListenersDataReceived(event.timestamp, event.values);
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * This class's listener for accuracy changes from the internal Android accelerometer
     * implementation.  Required via the SensorEventListener implementation.
     *
     * @param sensor   The sensor that has changed.  Some phones may have more than one physical sensor
     * @param accuracy The new accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        HromatkaLog.getInstance().enter(TAG);
        notifyListenersAccuracyChanged(accuracy);
        HromatkaLog.getInstance().exit(TAG);
    }
}
