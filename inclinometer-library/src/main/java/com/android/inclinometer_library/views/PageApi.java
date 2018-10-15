

package com.android.inclinometer_library.views;

import android.app.Activity;

import com.android.inclinometer_library.backgroundservice.HromatkaServiceApi;


/**
 * Interface that all pages are expected to implement
 */
public interface PageApi {
    /**
     * method to create the page
     * @param activity           Instance of the requesting Android activity
     * @param hromatkaServiceApi Instance of the HromatkaServiceApi (for interacting with the sensors)
     */
    void onCreate(Activity activity, HromatkaServiceApi hromatkaServiceApi);

    /**
     * method to destroy the page
     * @param activity           Instance of the requesting Android activity
     * @param hromatkaServiceApi Instance of the HromatkaServiceApi (for interacting with the sensors)
     */
    void onDestroy(Activity activity, HromatkaServiceApi hromatkaServiceApi);
}
