

package com.android.inclinometer_library.backgroundservice.sensormanager;

/**
 * Listeners (Visitors) that are interested in listening to a particular sensor will need to
 * implement this interface.  The sensor will then call the methods below upon an event (new
 * data, accuracy changed, etc.).
 */
public interface SensorApi {
    /**
     * The sensor will notify its listeners of new data via this method.  The length and
     * meaning of the values[] array is sensor-dependent.  See that sensor's Javadoc for the
     * meaning of each index in the array.
     * i
     * @param timestamp time at which this measurement occurred
     * @param values    measured sensor values
     */
    void onDataReceived(
            long timestamp,
            float[] values);

    /**
     * The sensor will notify its listeners of accuracy changes via this method
     *
     * @param accuracy new accuracy of this sensor
     */
    void onAccuracyChanged(int accuracy);
}
