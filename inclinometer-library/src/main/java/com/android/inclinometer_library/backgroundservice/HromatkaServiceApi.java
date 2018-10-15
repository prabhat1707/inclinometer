

package com.android.inclinometer_library.backgroundservice;


import com.android.inclinometer_library.backgroundservice.sensormanager.SensorApi;

/**
 * Interface that HromatkaService implements.  Android activities can call these
 * methods to request the service to perform specific operations.
 */
public interface HromatkaServiceApi {
    /**
     * Method for Android activities to register an accelerometer listener
     *
     * @param callback The listener's callback class
     */
    void registerAccelerometerListener(SensorApi callback);

    /**
     * Method for Android activities to unregister an accelerometer listener
     *
     * @param callback The listener's callback class
     */
    void unregisterAccelerometerListener(SensorApi callback);

    /**
     * Method for Android activities to register an oriented accelerometer listener
     *
     * @param callback The listener's callback class
     */
    void registerOrientedAccelerometerListener(SensorApi callback);

    /**
     * Method for Android activities to unregister an oriented accelerometer listener
     *
     * @param callback The listener's callback class
     */
    void unregisterOrientedAccelerometerListener(SensorApi callback);

    /**
     * Method for Android activities to register an inclinometer listener
     *
     * @param callback The listener's callback class
     */
    void registerInclinometerListener(SensorApi callback);

    /**
     * Method for Android activities to unregister an inclinometer listener
     *
     * @param callback The listener's callback class
     */
    void unregisterInclinometerListener(SensorApi callback);

    /**
     * Method for an Android activity to request the inclinometer offsets to be updated.  This will
     * cause the inclinometer sensor to save the current oriented accelerometer values and subtract
     * them from future inclinometer measurements; thus allowing the device to be mounted at an
     * arbitrary angle in the vehicle.
     */
    void updateInclinometerOffsets();
}
