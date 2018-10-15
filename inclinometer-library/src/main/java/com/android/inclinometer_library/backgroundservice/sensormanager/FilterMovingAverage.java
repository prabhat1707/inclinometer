

package com.android.inclinometer_library.backgroundservice.sensormanager;



import com.android.inclinometer_library.backgroundservice.HromatkaLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterMovingAverage {
    public static final double SEC_TO_NANOSEC = 1e9;
    /* by default, expire samples after 1/2 of a second */
    public static final double DEFAULT_SAMPLE_EXPIRATION_NS = 0.5 * SEC_TO_NANOSEC;
    private final String TAG = this.getClass().getSimpleName();
    private List<TimestampAndData> timestampAndDataList = new ArrayList<>();
    private double samplesExpireAfterNanoseconds;
    public FilterMovingAverage(double samplesExpireAfterNanoseconds) {
        HromatkaLog.getInstance().enter(TAG);
        this.samplesExpireAfterNanoseconds = samplesExpireAfterNanoseconds;
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * Returns the current moving average of the data stored by this filter
     * @return moving average
     */
    public synchronized float[] getMovingAverage() {
        HromatkaLog.getInstance().enter(TAG);
        /* get one sample to determine the length of the float array */
        TimestampAndData oneSample = timestampAndDataList.get(0);
        float[] averages = new float[oneSample.getData().length];

        /*
         * 1) zero out the averages
         */
        for(int index = 0; index < averages.length; index++) {
            averages[index] = 0.0f;
        }

        /*
         * 2) sum up all of the samples
         */
        for (Iterator<TimestampAndData> iterator = timestampAndDataList.iterator(); iterator.hasNext();) {
            TimestampAndData thisSample = iterator.next();

            for(int index = 0; index < averages.length; index++) {
                averages[index] += thisSample.getData()[index];
            }
        }

        /*
         * 3) divide by the number of samples to get the average
         */
        for(int index = 0; index < averages.length; index++) {
            averages[index] = averages[index] / timestampAndDataList.size();
        }

        HromatkaLog.getInstance().exit(TAG);
        return averages;
    }

    /**
     * insert a sample and its timestamp into the moving average filter
     * @param timestamp timestamp of the data
     * @param data      float[] containing the sample data
     */
    public synchronized void add(long timestamp, float[] data) {
        HromatkaLog.getInstance().enter(TAG);
        TimestampAndData newSample = new TimestampAndData(timestamp, data);
        timestampAndDataList.add(newSample);

        HromatkaLog.getInstance().logVerbose(TAG, "timestampAndDataList size = " + timestampAndDataList.size());
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * clear the entire moving average filter
     */
    public synchronized void clear() {
        HromatkaLog.getInstance().enter(TAG);
        timestampAndDataList.clear();
        HromatkaLog.getInstance().exit(TAG);
    }

    /**
     * remove expired samples from the moving average filter.  The expiration duration was set
     * during construction of this class.
     */
    public synchronized void removeExpired() {
        HromatkaLog.getInstance().enter(TAG);

        /*
         * get the most recent sample's timestamp.  we will use this as the "current" time.  Not
         * perfect, but it's the only safe comparison for a sensor.  There's no guarantee that
         * the sensor's timestamp is comparable to System.nanoTime().
         */
        TimestampAndData mostCurrentSample = timestampAndDataList.get(timestampAndDataList.size() - 1);
        long currentTime = mostCurrentSample.getTimestamp();
        HromatkaLog.getInstance().logVerbose(TAG, "Current time: " + currentTime);

        for (Iterator<TimestampAndData> iterator = timestampAndDataList.iterator(); iterator.hasNext();) {
            TimestampAndData thisSample = iterator.next();

            if (thisSample.getTimestamp() < (currentTime - (long)samplesExpireAfterNanoseconds)) {
                /* this sample has expired.  remove it */
                HromatkaLog.getInstance().logVerbose(TAG, "Removing: " + thisSample.getTimestamp());
                iterator.remove();
            }
        }

        HromatkaLog.getInstance().logVerbose(TAG, "timestampAndDataList size = " + timestampAndDataList.size());
        HromatkaLog.getInstance().exit(TAG);
    }

    /** class that combines the timestamp and sensor data into one (convenient!) location */
    private static class TimestampAndData {
        private long timestamp;
        private float[] data;

        public TimestampAndData(long timestamp, float[] data) {
            this.timestamp = timestamp;
            this.data = data;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public float[] getData() {
            return this.data;
        }
    }
}
